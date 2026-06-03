package com.ust.pos.api.home;

import com.ust.pos.node.service.NodeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/home")

public class HomeApiController {

    @Autowired

    private NodeService nodeService;

    @GetMapping

    public Object home() {

        try {

            return nodeService.getNodesForRoles();

        } catch (RuntimeException ex) {

            if ("USER_DELETED".equals(ex.getMessage())) {

                throw new RuntimeException("USER_DELETED");

            }

            throw new RuntimeException("REDIRECT_LOGIN");

        }

    }

}
