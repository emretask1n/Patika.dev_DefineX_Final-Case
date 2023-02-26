package com.emretaskin.definexFinalCase.controller;

import com.emretaskin.definexFinalCase.dto.request.UserInputDTO;
import com.emretaskin.definexFinalCase.dto.response.UserResponse;
import com.emretaskin.definexFinalCase.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "Update name surname or password by id")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UserInputDTO userInputDTO){
        log.info("Received a request to update user with id: {}", userId);
        UserResponse response = userService.updateUser(userId, userInputDTO);
        log.info("Successfully updated user with id: {}", userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete user by user id")
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId){
        log.info("Received a request to delete user with id: {}", userId);
        UserResponse response = userService.deleteUser(userId);
        log.info("Successfully deleted user with id: {}", userId);
        return ResponseEntity.ok(response);
    }

}
