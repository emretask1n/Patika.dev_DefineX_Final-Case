package com.emretaskin.definexFinalCase.service.checker.impl;

import com.emretaskin.definexFinalCase.entity.User;
import com.emretaskin.definexFinalCase.exception.IdNumberAlreadyInUseException;
import com.emretaskin.definexFinalCase.repository.UserRepository;
import com.emretaskin.definexFinalCase.service.checker.IsIdNumberAlreadyInUse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class IsIdNumberAlreadyInUseImpl implements IsIdNumberAlreadyInUse {

    private final UserRepository userRepository;

    @Override
    public void check(String idNumber) {
        Optional<User> checkUser = userRepository.findByIdNumber(idNumber);
        if (checkUser.isPresent()) {
            log.error("This ID Number already in use: {}", idNumber);
            throw new IdNumberAlreadyInUseException("This ID Number already in use!");
        }
    }
}
