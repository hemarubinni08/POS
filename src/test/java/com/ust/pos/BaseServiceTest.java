package com.ust.pos;

import com.ust.pos.base.service.BaseService;
import com.ust.pos.modell.CommonFields;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BaseServiceTest {

    private final TestBaseService service = new TestBaseService();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void setCreatedDetails_WithAuthenticatedUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("user");
        when(authentication.getName()).thenReturn("testUser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);

        CommonFields entity = mock(CommonFields.class);

        service.callSetCreatedDetails(entity);

        verify(entity).setCreatedBy("testUser");
        verify(entity).setModifiedBy("testUser");
        verify(entity).setCreatedOn(any(LocalDateTime.class));
        verify(entity).setModifiedOn(any(LocalDateTime.class));
    }

    @Test
    void setCreatedDetails_NullEntity() {
        service.callSetCreatedDetails(null);
    }

    @Test
    void setModifiedDetails_NullEntity() {
        service.callSetModifiedDetails(null);
    }

    @Test
    void setCreatedDetails_WithUnauthenticatedUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);

        CommonFields entity = mock(CommonFields.class);

        service.callSetCreatedDetails(entity);

        verify(entity).setCreatedBy("SYSTEM");
        verify(entity).setModifiedBy("SYSTEM");
    }

    @Test
    void setCreatedDetails_WithAnonymousUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);

        CommonFields entity = mock(CommonFields.class);

        service.callSetCreatedDetails(entity);

        verify(entity).setCreatedBy("SYSTEM");
        verify(entity).setModifiedBy("SYSTEM");
        verify(entity).setCreatedOn(any(LocalDateTime.class));
        verify(entity).setModifiedOn(any(LocalDateTime.class));
    }

    @Test
    void setCreatedDetails_WithNullAuthentication() {
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(null);

        SecurityContextHolder.setContext(context);

        CommonFields entity = mock(CommonFields.class);

        service.callSetCreatedDetails(entity);

        verify(entity).setCreatedBy("SYSTEM");
        verify(entity).setModifiedBy("SYSTEM");
        verify(entity).setCreatedOn(any(LocalDateTime.class));
        verify(entity).setModifiedOn(any(LocalDateTime.class));
    }

    @Test
    void setCreatedDetails_WhenAuthenticationThrowsException() {
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenThrow(new RuntimeException("error"));

        SecurityContextHolder.setContext(context);

        CommonFields entity = mock(CommonFields.class);

        service.callSetCreatedDetails(entity);

        verify(entity).setCreatedBy("SYSTEM");
        verify(entity).setModifiedBy("SYSTEM");
        verify(entity).setCreatedOn(any(LocalDateTime.class));
        verify(entity).setModifiedOn(any(LocalDateTime.class));
    }

    @Test
    void setCreatedDetails_WithNullEntity() {
        service.callSetCreatedDetails(null);
    }

    @Test
    void setModifiedDetails_WithAuthenticatedUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("user");
        when(authentication.getName()).thenReturn("testUser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);

        CommonFields entity = mock(CommonFields.class);

        service.callSetModifiedDetails(entity);

        verify(entity).setModifiedBy("testUser");
        verify(entity).setModifiedOn(any(LocalDateTime.class));
    }

    @Test
    void setModifiedDetails_WithNullEntity() {
        service.callSetModifiedDetails(null);
    }

    @Test
    void softDelete_ShouldMarkEntityDeleted() {
        CommonFields entity = mock(CommonFields.class);

        service.callSoftDelete(entity);

        verify(entity).setDeleted(true);
    }

    @Test
    void staticFields_ShouldBeInitialized() {
        assertNotNull(BaseService.CONTEXT);
    }

    static class TestBaseService extends BaseService {
        void callSetCreatedDetails(CommonFields entity) {
            setCreatedDetails(entity);
        }

        void callSetModifiedDetails(CommonFields entity) {
            setModifiedDetails(entity);
        }

        void callSoftDelete(CommonFields entity) {
            softDelete(entity);
        }
    }
}