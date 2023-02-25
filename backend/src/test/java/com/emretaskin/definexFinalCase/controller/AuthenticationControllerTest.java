package com.emretaskin.definexFinalCase.controller;

import com.emretaskin.definexFinalCase.dto.request.AuthenticationRequest;
import com.emretaskin.definexFinalCase.dto.request.RegisterRequest;
import com.emretaskin.definexFinalCase.dto.response.AuthenticationResponse;
import com.emretaskin.definexFinalCase.service.impl.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Authentication Controller Unit Test")
class AuthenticationControllerTest {
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test register method of Authentication Controller")
    void registerTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .idNumber("12345678910")
                .nameSurname("John Doe")
                .password("password")
                .build();
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("token")
                .idNumber("12345678910")
                .userId(1L)
                .build();

        when(authenticationService.register(any())).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(registerRequest);

        verify(authenticationService).register(registerRequest);
        assert responseEntity.getStatusCode() == HttpStatusCode.valueOf(200);
    }

    @Test
    @DisplayName("Test login method of Authentication Controller")
    void loginTest() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .idNumber("12345678910")
                .password("password")
                .build();
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("token")
                .idNumber("12345678910")
                .userId(1L)
                .build();

        when(authenticationService.login(any())).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.login(authenticationRequest);

        verify(authenticationService).login(authenticationRequest);
        assert responseEntity.getStatusCode() == HttpStatusCode.valueOf(200);
    }
}