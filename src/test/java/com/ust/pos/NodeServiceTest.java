package com.ust.pos;

import com.ust.pos.dto.NodeDto;
import com.ust.pos.model.Node;
import com.ust.pos.model.NodeRepository;
import com.ust.pos.model.User;
import com.ust.pos.model.UserRepository;
import com.ust.pos.node.service.impl.NodeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Type;
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

    // ================= SAVE =================
    @Test
    void save_success() {

        NodeDto dto = new NodeDto();
        dto.setIdentifier("N1");

        Node node = new Node();

        Mockito.when(nodeRepository.findByIdentifier("N1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(dto, Node.class))
                .thenReturn(node);

        NodeDto response = nodeService.save(dto);

        Mockito.verify(nodeRepository).save(node);

        Assertions.assertEquals("N1", dto.getIdentifier());
    }

    @Test
    void save_failure_existing() {

        NodeDto dto = new NodeDto();
        dto.setIdentifier("N1");

        Mockito.when(nodeRepository.findByIdentifier("N1"))
                .thenReturn(new Node());

        NodeDto response = nodeService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertTrue(response.getMessage().contains("already exists"));
    }

    // ================= FIND BY ID =================
    @Test
    void findByIdentifier_success() {

        Node node = new Node();
        NodeDto dto = new NodeDto();

        Mockito.when(nodeRepository.findByIdentifier("N1"))
                .thenReturn(node);

        Mockito.when(modelMapper.map(node, NodeDto.class))
                .thenReturn(dto);

        NodeDto response = nodeService.findByIdentifier("N1");

        Assertions.assertNotNull(response);
    }

    @Test
    void findByIdentifier_null() {

        Mockito.when(nodeRepository.findByIdentifier("N1"))
                .thenReturn(null);

        NodeDto response = nodeService.findByIdentifier("N1");

        Assertions.assertNull(response);
    }

    // ================= UPDATE =================
    @Test
    void update_success() {

        NodeDto dto = new NodeDto();
        dto.setIdentifier("N1");

        Node existing = new Node();

        Mockito.when(nodeRepository.findByIdentifier("N1"))
                .thenReturn(existing);

        Mockito.when(nodeRepository.save(existing))
                .thenReturn(existing);

        NodeDto response = nodeService.update(dto);

        Mockito.verify(nodeRepository).save(existing);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void update_failure() {

        NodeDto dto = new NodeDto();
        dto.setIdentifier("N1");

        Mockito.when(nodeRepository.findByIdentifier("N1"))
                .thenReturn(null);

        NodeDto response = nodeService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertTrue(response.getMessage().contains("not found"));
    }

    // ================= DELETE =================
    @Test
    void delete_test() {

        Mockito.doNothing()
                .when(nodeRepository)
                .deleteByIdentifier("N1");

        nodeService.delete("N1");

        Mockito.verify(nodeRepository).deleteByIdentifier("N1");
    }

    // ================= FIND ALL =================
    @Test
    void findAll_test() {

        Node node = new Node();
        NodeDto dto = new NodeDto();

        Mockito.when(nodeRepository.findAll())
                .thenReturn(List.of(node));

        Type type = new TypeToken<List<NodeDto>>() {}.getType();

        Mockito.when(modelMapper.map(Mockito.anyList(), Mockito.eq(type)))
                .thenReturn(List.of(dto));

        List<NodeDto> response = nodeService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    // ================= getNodesForRoles =================
    @Test
    void getNodesForRoles_emptyAuth() {

        SecurityContextHolder.clearContext();

        List<NodeDto> response = nodeService.getNodesForRoles();

        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getNodesForRoles_success() {

        var springUser = new org.springframework.security.core.userdetails.User(
                "john", "pass", List.of()
        );

        var auth = new UsernamePasswordAuthenticationToken(springUser, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = new User();
        user.setRoles(List.of("ADMIN"));

        Node node = new Node();
        node.setIdentifier("N1");
        node.setRoles(List.of("ADMIN"));

        Mockito.when(userRepository.findByUsername("john"))
                .thenReturn(user);

        Mockito.when(nodeRepository.findAll())
                .thenReturn(List.of(node));

        Mockito.when(nodeRepository.findByIdentifier("N1"))
                .thenReturn(node);

        Mockito.when(modelMapper.map(node, NodeDto.class))
                .thenReturn(new NodeDto());

        List<NodeDto> response = nodeService.getNodesForRoles();

        Assertions.assertEquals(1, response.size());
    }
}