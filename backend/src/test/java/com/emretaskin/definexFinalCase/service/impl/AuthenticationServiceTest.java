package com.emretaskin.definexFinalCase.service.impl;

import com.emretaskin.definexFinalCase.dto.request.AuthenticationRequest;
import com.emretaskin.definexFinalCase.dto.request.RegisterRequest;
import com.emretaskin.definexFinalCase.dto.response.AuthenticationResponse;
import com.emretaskin.definexFinalCase.entity.CreditScore;
import com.emretaskin.definexFinalCase.entity.User;
import com.emretaskin.definexFinalCase.enums.Role;
import com.emretaskin.definexFinalCase.exception.IdNumberAlreadyInUseException;
import com.emretaskin.definexFinalCase.exception.UserIdOrPasswordIncorrectException;
import com.emretaskin.definexFinalCase.repository.UserRepository;
import com.emretaskin.definexFinalCase.service.checker.impl.IsIdNumberAlreadyInUseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IsIdNumberAlreadyInUseImpl isIdNumberAlreadyInUse;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(userRepository, userService, passwordEncoder, isIdNumberAlreadyInUse, jwtService, authenticationManager);
    }




    @Test
    void givenRegisterRequest_whenRegister_thenCreateNewUser() {
        RegisterRequest request = new RegisterRequest("123456789101", "John Doe", "password");
        User newUser = User.builder()
                .idNumber(request.getIdNumber())
                .nameSurname(request.getNameSurname())
                .password(passwordEncoder.encode(request.getPassword()))
                .creditScore(CreditScore.builder().creditScore(500).build())
                .role(Role.USER)
                .build();
        when(userService.generateCreditScore()).thenReturn(500);
        doNothing().when(isIdNumberAlreadyInUse).check(request.getIdNumber());
        when(userRepository.save(newUser)).thenReturn(newUser);
        when(jwtService.generateToken(newUser)).thenReturn("token");
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .token("token")
                .idNumber(request.getIdNumber())
                .userId(newUser.getId())
                .build();

        AuthenticationResponse response = authenticationService.register(request);

        verify(isIdNumberAlreadyInUse, times(1)).check(request.getIdNumber());
        verify(userService, times(1)).generateCreditScore();
        verify(userRepository, times(1)).save(newUser);
        verify(jwtService, times(1)).generateToken(newUser);
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenLoginRequest_whenLogin_thenAuthenticateUser() {
        AuthenticationRequest request = new AuthenticationRequest("12345678910", "password");
        User user = User.builder()
                .id(1L)
                .idNumber(request.getIdNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        when(userRepository.findByIdNumber(request.getIdNumber())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("token");
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .token("token")
                .idNumber(request.getIdNumber())
                .userId(user.getId())
                .build();

        AuthenticationResponse response = authenticationService.login(request);

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByIdNumber(request.getIdNumber());
        verify(jwtService, times(1)).generateToken(user);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testCheck_IdNumberAlreadyInUse() {
        isIdNumberAlreadyInUse = new IsIdNumberAlreadyInUseImpl(userRepository);

        // Given
        String idNumber = "12345678910";
        User user = User.builder()
                .id(1L)
                .idNumber(idNumber)
                .nameSurname("John Doe")
                .password("password")
                .creditScore(null)
                .loanForms(null)
                .role(Role.USER)
                .build();
        given(userRepository.findByIdNumber(idNumber)).willReturn(Optional.of(user));

        // When-Then
        assertThrows(IdNumberAlreadyInUseException.class, () -> isIdNumberAlreadyInUse.check(idNumber));
    }

    @Test
    void givenInvalidIdNumberOrPassword_whenLogin_thenThrowUserIdOrPasswordIncorrectException() {
        AuthenticationRequest request = new AuthenticationRequest("12345678910", "password");
        when(userRepository.findByIdNumber(request.getIdNumber())).thenReturn(Optional.empty());
        assertThrows(UserIdOrPasswordIncorrectException.class, () -> authenticationService.login(request));
    }



}
