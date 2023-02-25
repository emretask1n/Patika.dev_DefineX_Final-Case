package com.emretaskin.definexFinalCase.controller;

import com.emretaskin.definexFinalCase.dto.request.UserInputDTO;
import com.emretaskin.definexFinalCase.dto.response.UserResponse;
import com.emretaskin.definexFinalCase.entity.User;
import com.emretaskin.definexFinalCase.exception.UserNotFoundException;
import com.emretaskin.definexFinalCase.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController = new UserController(userService);

    @BeforeEach
    public void setUp() {
        userService = mock(UserServiceImpl.class);
        userController = new UserController(userService);
    }


    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setResponse(("Your user has been deleted."));

        when(userService.deleteUser(userId)).thenReturn(expectedResponse);

        ResponseEntity<UserResponse> actualResponse = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setNameSurname("Test User");
        userInputDTO.setPassword("password");

        UserResponse expectedResponse = UserResponse.builder().response("User is updated").build();

        Optional<User> optionalUser = Optional.of(User.builder().id(userId).nameSurname("Old Name").password("oldpassword").build());

        when(userService.updateUser(userId, userInputDTO)).thenReturn(expectedResponse);
        when(userService.findUserById(userId)).thenReturn(optionalUser);

        ResponseEntity<UserResponse> actualResponse = userController.updateUser(userId, userInputDTO);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void testUpdateUser_UserNotFoundException() {
        Long userId = 1L;
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setNameSurname("Test User");
        userInputDTO.setPassword("password");

        when(userService.findUserById(userId)).thenReturn(Optional.empty());

        try {
            userController.updateUser(userId, userInputDTO);
        } catch (UserNotFoundException ex) {
            assertEquals("User with id " + userId + " not found", ex.getMessage());
        }
    }

}