package com.rev.user_service.service.impl;

import com.rev.user_service.dto.request.CreateUserRequest;
import com.rev.user_service.dto.request.UpdateUserRequest;
import com.rev.user_service.dto.response.UserResponse;
import com.rev.user_service.entity.Role;
import com.rev.user_service.service.UserAccountService;
import com.rev.user_service.service.UserDirectoryService;
import com.rev.user_service.service.UserRelationshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserManagementServiceImplTest {

    @Mock
    private UserAccountService accountService;

    @Mock
    private UserRelationshipService relationshipService;

    @Mock
    private UserDirectoryService directoryService;

    @InjectMocks
    private UserManagementServiceImpl userManagementService;

    private UserResponse userResponse;
    private CreateUserRequest createRequest;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("John Doe");
        userResponse.setEmail("test@rev.com");
        userResponse.setRole(Role.EMPLOYEE);

        createRequest = new CreateUserRequest();
        createRequest.setEmail("test@rev.com");
        createRequest.setFirstName("John");
        createRequest.setLastName("Doe");
    }

    @Test
    void createUser_Success() {
        when(accountService.createUser(any())).thenReturn(userResponse);
        UserResponse result = userManagementService.createUser(createRequest);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(accountService).createUser(createRequest);
    }

    @Test
    void getUserById_Success() {
        when(accountService.getUserById(1L)).thenReturn(userResponse);
        UserResponse result = userManagementService.getUserById(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(accountService).getUserById(1L);
    }

    @Test
    void updateUser_Success() {
        UpdateUserRequest updateRequest = new UpdateUserRequest();
        when(accountService.updateUser(eq(1L), any())).thenReturn(userResponse);
        UserResponse result = userManagementService.updateUser(1L, updateRequest);
        assertNotNull(result);
        verify(accountService).updateUser(1L, updateRequest);
    }

    @Test
    void deactivateUser_Success() {
        userManagementService.deactivateUser(1L);
        verify(accountService).deactivateUser(1L);
    }

    @Test
    void changePassword_Success() {
        userManagementService.changePassword(1L, "old", "new");
        verify(accountService).changePassword(1L, "old", "new");
    }

    @Test
    void getEmployeeDirectory_Success() {
        when(directoryService.getEmployeeDirectory()).thenReturn(Collections.emptyList());
        List<?> result = userManagementService.getEmployeeDirectory();
        assertNotNull(result);
        verify(directoryService).getEmployeeDirectory();
    }
}
