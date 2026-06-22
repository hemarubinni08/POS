package com.ust.pos.base.service;

import com.ust.pos.model.CommonFields;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BaseService {

    private String getLoggedInUser() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "SYSTEM";
        }
    }

    public void setCreatedDetails(CommonFields entity) {
        entity.setCreatedBy(getLoggedInUser());
        entity.setCreatedOn(LocalDateTime.now());
        entity.setModifiedBy(getLoggedInUser());
        entity.setModifiedOn(LocalDateTime.now());
    }

    public void setModifiedDetails(CommonFields entity) {
        entity.setModifiedBy(getLoggedInUser());
        entity.setModifiedOn(LocalDateTime.now());
    }

    public void softDelete(CommonFields entity) {
        entity.setDeleted(true);
    }
}
