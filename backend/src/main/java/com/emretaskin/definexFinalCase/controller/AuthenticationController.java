package com.emretaskin.definexFinalCase.controller;

import com.emretaskin.definexFinalCase.dto.request.AuthenticationRequest;
import com.emretaskin.definexFinalCase.dto.request.RegisterRequest;
import com.emretaskin.definexFinalCase.dto.response.AuthenticationResponse;
import com.emretaskin.definexFinalCase.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        log.info("Registration request received for ID Number: {}", request.getIdNumber());
        AuthenticationResponse response = authenticationService.register(request);
        log.info("User with ID Number {} registered successfully", response.getIdNumber());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        log.info("Login request received for ID Number: {}", request.getIdNumber());
        AuthenticationResponse response = authenticationService.login(request);
        log.info("User with ID Number {} logged in successfully", response.getIdNumber());
        return ResponseEntity.ok(response);
    }
}
