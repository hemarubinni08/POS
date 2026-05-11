package com.ust.pos;

import com.ust.pos.dto.NodeDto;
import com.ust.pos.modell.Node;
import com.ust.pos.modell.NodeRepository;
import com.ust.pos.modell.UserRepository;
import com.ust.pos.node.service.impl.NodeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
 class NodeServiceTest {

    @InjectMocks
    private NodeServiceImpl nodeService;

    @Mock
    private NodeRepository nodeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {
        NodeDto nodeDto = new NodeDto();
        nodeDto.setIdentifier("NODE1");

        Mockito.when(nodeRepository.findByIdentifier("NODE1"))
                .thenReturn(null);

        Node node = new Node();
        node.setIdentifier("NODE1");

        Mockito.when(modelMapper.map(nodeDto, Node.class))
                .thenReturn(node);
        Mockito.when(nodeRepository.save(node))
                .thenReturn(node);

        NodeDto response = nodeService.save(nodeDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void saveTestFailure() {
        NodeDto nodeDto = new NodeDto();
        nodeDto.setIdentifier("NODE1");

        Node existingNode = new Node();
        existingNode.setIdentifier("NODE1");

        Mockito.when(nodeRepository.findByIdentifier("NODE1"))
                .thenReturn(existingNode);

        NodeDto response = nodeService.save(nodeDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateTest() {
        NodeDto nodeDto = new NodeDto();
        nodeDto.setIdentifier("NODE1");

        Node existingNode = new Node();
        existingNode.setIdentifier("NODE1");

        Mockito.when(nodeRepository.findByIdentifier("NODE1"))
                .thenReturn(existingNode);
        Mockito.when(nodeRepository.save(existingNode))
                .thenReturn(existingNode);

        NodeDto response = nodeService.update(nodeDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure() {
        NodeDto nodeDto = new NodeDto();
        nodeDto.setIdentifier("NODE1");

        Mockito.when(nodeRepository.findByIdentifier("NODE1"))
                .thenReturn(null);

        NodeDto response = nodeService.update(nodeDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(nodeRepository)
                .deleteByIdentifier("NODE1");

        nodeService.delete("NODE1");

        Mockito.verify(nodeRepository).deleteByIdentifier("NODE1");
    }

    @Test
    void findAllTest() {
        Node node = new Node();
        node.setIdentifier("NODE1");

        NodeDto nodeDto = new NodeDto();
        nodeDto.setIdentifier("NODE1");

        List<Node> nodes = List.of(node);
        List<NodeDto> nodeDtos = List.of(nodeDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Node> nodePage =
                new PageImpl<>(nodes, pageable, nodes.size());

        Mockito.when(nodeRepository.findAll(pageable))
                .thenReturn(nodePage);

        Mockito.when(modelMapper.map(
                Mockito.eq(nodes),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(nodeDtos);

        List<NodeDto> response = nodeService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void getNodesForRolesTest() {

        org.springframework.security.core.userdetails.User principal =
                new org.springframework.security.core.userdetails.User(
                        "admin",
                        "pwd",
                        List.of(() -> "ROLE_ADMIN")
                );

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        com.ust.pos.modell.User appUser = new com.ust.pos.modell.User();
        appUser.setUsername("admin");
        appUser.setRoles(List.of("ROLE_ADMIN"));

        Mockito.when(userRepository.findByUsername("admin"))
                .thenReturn(appUser);

        Node node = new Node();
        node.setIdentifier("NODE1");
        node.setRoles(List.of("ROLE_ADMIN"));

        NodeDto nodeDto = new NodeDto();
        nodeDto.setIdentifier("NODE1");

        Mockito.when(nodeRepository.findAll())
                .thenReturn(List.of(node));
        Mockito.when(nodeRepository.findByIdentifier("NODE1"))
                .thenReturn(node);
        Mockito.when(modelMapper.map(node, NodeDto.class))
                .thenReturn(nodeDto);

        List<NodeDto> response = nodeService.getNodesForRoles();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("NODE1", response.get(0).getIdentifier());
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getNodesForRolesWithoutAuthenticationTest() {

        SecurityContextHolder.clearContext();

        List<NodeDto> response = nodeService.getNodesForRoles();

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void findByIdentifierTest() {

        Node node = new Node();
        node.setIdentifier("NODE1");

        NodeDto nodeDto = new NodeDto();
        nodeDto.setIdentifier("NODE1");

        Mockito.when(nodeRepository.findByIdentifier("NODE1"))
                .thenReturn(node);
        Mockito.when(modelMapper.map(node, NodeDto.class))
                .thenReturn(nodeDto);

        NodeDto response = nodeService.findByIdentifier("NODE1");

        Assertions.assertEquals("NODE1", response.getIdentifier());
    }

    @Test
    void findAllEmptyTest() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));

        Page<Node> emptyPage =
                new PageImpl<>(List.of(), pageable, 0);

        Mockito.when(nodeRepository.findAll(pageable))
                .thenReturn(emptyPage);

        Mockito.when(modelMapper.map(
                        Mockito.eq(List.of()),
                        Mockito.any(java.lang.reflect.Type.class)))
                .thenReturn(List.of());

        List<NodeDto> response = nodeService.findAll(pageable);

        Assertions.assertTrue(response.isEmpty());
    }
}