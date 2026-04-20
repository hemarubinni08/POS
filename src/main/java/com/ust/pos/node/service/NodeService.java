package com.ust.pos.node.service;

import com.ust.pos.dto.NodeDto;

import java.util.List;

public interface NodeService {
    NodeDto save(NodeDto userDto);

    NodeDto update(NodeDto userDto);

    boolean delete(String username);

    List<NodeDto> findAll();

    NodeDto findByIdentifier(String identifier);

    List<NodeDto> getNodesForRoles();
}
