package com.ust.pos.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NodeRepository extends JpaRepository<Node, Long> {
    Node findByIdentifier(String identifier);

    List<Node> findByRoles(List<String> roles);

    void deleteByIdentifier(String identifier);

    List<Node> findDistinctByRolesIn(List<String> roles);

}
