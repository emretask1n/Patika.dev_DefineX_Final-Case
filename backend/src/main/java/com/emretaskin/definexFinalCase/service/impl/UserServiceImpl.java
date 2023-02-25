package com.emretaskin.definexFinalCase.service.impl;

import com.emretaskin.definexFinalCase.dto.request.UserInputDTO;
import com.emretaskin.definexFinalCase.dto.response.UserResponse;
import com.emretaskin.definexFinalCase.entity.User;
import com.emretaskin.definexFinalCase.exception.UserNotFoundException;
import com.emretaskin.definexFinalCase.repository.UserRepository;
import com.emretaskin.definexFinalCase.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final CreditScoreServiceImpl creditScoreService;

    @Override
    public UserResponse updateUser(Long userId, UserInputDTO userInputDTO) {
        log.info("Updating user with id {}", userId);
        Optional<User> checkUser = userRepository.findById(userId);
        if (checkUser.isPresent()){
            User foundUser = checkUser.get();
            foundUser.setNameSurname(userInputDTO.getNameSurname());
            foundUser.setPassword(passwordEncoder.encode(userInputDTO.getPassword()));
            userRepository.save(foundUser);

            log.info("User with id {} has been updated successfully", userId);
            return UserResponse.builder().response("User is updated").build();
        } else {
            log.error("User with id {} not found", userId);
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
    }

    @Override
    public UserResponse deleteUser(Long userId) {
        log.info("Deleting user with id {}", userId);
        Optional<User> checkUser = userRepository.findById(userId);
        if (checkUser.isPresent()){
            User foundUser = checkUser.get();
            userRepository.deleteById(foundUser.getId());

            log.info("User with id {} has been deleted successfully", userId);
            return UserResponse.builder().response("User is deleted").build();
        }else {
            log.info("User with id {} not found", userId);
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
    }

    @Override
    public int getCreditScore(String idNumber) {
        log.info("Getting credit score of user with id number {}", idNumber);
        Optional<User> optionalUser = userRepository.findByIdNumber(idNumber);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found"));
        log.info("Credit score of user with id number {} is {}", idNumber, user.getCreditScore().getCreditScore());
        return user.getCreditScore().getCreditScore();
    }

    @Override
    public User getUserByIdNumber(String idNumber){
        log.info("Getting user with id number {}", idNumber);
        Optional<User> optionalUser = userRepository.findByIdNumber(idNumber);
        return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public int generateCreditScore() {
        return creditScoreService.generateCreditScore();
    }

}

