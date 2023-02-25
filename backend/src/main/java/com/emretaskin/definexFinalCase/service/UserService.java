package com.emretaskin.definexFinalCase.service;

import com.emretaskin.definexFinalCase.dto.request.UserInputDTO;
import com.emretaskin.definexFinalCase.dto.response.UserResponse;
import com.emretaskin.definexFinalCase.entity.User;

import java.util.Optional;

public interface UserService {

    UserResponse updateUser(Long userId, UserInputDTO userInputDTO);

    UserResponse deleteUser(Long userId);

    int getCreditScore(String idNumber);

    User getUserByIdNumber(String idNumber);

    Optional<User> findUserById(Long userId);
}
