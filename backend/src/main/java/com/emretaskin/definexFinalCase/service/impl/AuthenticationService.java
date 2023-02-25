package com.emretaskin.definexFinalCase.service.impl;


import com.emretaskin.definexFinalCase.dto.request.AuthenticationRequest;
import com.emretaskin.definexFinalCase.dto.request.RegisterRequest;
import com.emretaskin.definexFinalCase.dto.response.AuthenticationResponse;
import com.emretaskin.definexFinalCase.entity.CreditScore;
import com.emretaskin.definexFinalCase.entity.User;
import com.emretaskin.definexFinalCase.enums.Role;
import com.emretaskin.definexFinalCase.exception.UserIdOrPasswordIncorrectException;
import com.emretaskin.definexFinalCase.repository.UserRepository;
import com.emretaskin.definexFinalCase.service.checker.impl.IsIdNumberAlreadyInUseImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final IsIdNumberAlreadyInUseImpl isIdNumberAlreadyInUse;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        log.info("Registering user with id number {}", request.getIdNumber());
        isIdNumberAlreadyInUse.check(request.getIdNumber());
        User newUser = createUserRegisterRequest(request);
        CreditScore creditScore = new CreditScore(userService.generateCreditScore());
        newUser.setCreditScore(creditScore);
        userRepository.save(newUser);

        log.info("New user registered successfully: {}", request.getIdNumber());
        String jwtToken = jwtService.generateToken(newUser);
        return createAuthenticationResponse(newUser, jwtToken);
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getIdNumber(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByIdNumber(request.getIdNumber()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            log.info("User logged in successfully: {}", request.getIdNumber());
            return createAuthenticationResponse(user,jwtToken);
        } catch (AuthenticationException ex) {
            log.error("Authentication failed for ID Number: {}", request.getIdNumber(), ex);
            throw new UserIdOrPasswordIncorrectException("Invalid id number or password", ex);
        } catch (Exception ex) {
            log.error("An error occurred while logging in for ID Number: {}", request.getIdNumber(), ex);
            throw new UserIdOrPasswordIncorrectException("Invalid id number or password", ex);
        }
    }

    private User createUserRegisterRequest(RegisterRequest request) {
        return User.builder()
                .idNumber(request.getIdNumber())
                .nameSurname(request.getNameSurname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }

    private AuthenticationResponse createAuthenticationResponse(User user, String jwtToken) {
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .idNumber(user.getIdNumber())
                .userId(user.getId())
                .build();
    }
}
