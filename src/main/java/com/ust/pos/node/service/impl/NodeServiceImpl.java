package com.ust.pos.node.service.impl;

import com.ust.pos.dto.NodeDto;
import com.ust.pos.dto.NodeDto;
import com.ust.pos.model.*;
import com.ust.pos.node.service.NodeService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
@Transactional
@Service
public class NodeServiceImpl implements NodeService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<NodeDto> getNodesForRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User currentUser = userRepository.findByUsername(principalObject.getUsername());
        List<Node> nodes = nodeRepository.findByRoles(currentUser.getRoles());
        Type listType = new TypeToken<List<NodeDto>>() {
        }.getType();
        return modelMapper.map(nodes, listType);
    }

    @Override
    public NodeDto save(NodeDto nodeDto) {
        String identifier = nodeDto.getIdentifier();
        Node existingRole = nodeRepository.findByIdentifier(identifier);
        if (existingRole != null) {
            nodeDto.setMessage("Node with identifier - " + identifier + " already exists");
            nodeDto.setSuccess(false);
            return nodeDto;
        }
        Node node = modelMapper.map(nodeDto, Node.class);
        nodeRepository.save(node);
        return nodeDto;
    }

    @Override
    public NodeDto update(NodeDto nodeDto) {
        String identifier = nodeDto.getIdentifier();
        Node existingRole = nodeRepository.findByIdentifier(identifier);
        if (existingRole == null) {
            nodeDto.setMessage("Role with identifier - " + identifier + " not found");
            nodeDto.setSuccess(false);
            return nodeDto;
        }
        modelMapper.map(nodeDto, existingRole);
        nodeRepository.save(existingRole);
        return nodeDto;
    }

    @Override
    public boolean delete(String identifier) {
        return nodeRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<NodeDto> findAll() {
        Type listType = new TypeToken<List<NodeDto>>() {
        }.getType();
        return modelMapper.map(nodeRepository.findAll(), listType);
    
    }

    @Override
    public NodeDto findByIdentifier(String identifier) {
        return modelMapper.map(nodeRepository.findByIdentifier(identifier), NodeDto.class);
    }
}
