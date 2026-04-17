package com.ust.pos.node.service;

import com.ust.pos.dto.NodeDto;

import java.util.List;

public interface NodeService {
    List<NodeDto> getNodesForRoles();
}
