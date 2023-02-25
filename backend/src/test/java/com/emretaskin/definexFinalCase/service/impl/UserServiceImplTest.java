package com.emretaskin.definexFinalCase.service.impl;

import com.emretaskin.definexFinalCase.dto.request.UserInputDTO;
import com.emretaskin.definexFinalCase.dto.response.UserResponse;
import com.emretaskin.definexFinalCase.entity.CreditScore;
import com.emretaskin.definexFinalCase.entity.User;
import com.emretaskin.definexFinalCase.exception.UserNotFoundException;
import com.emretaskin.definexFinalCase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreditScoreServiceImpl creditScoreService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;


    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).idNumber("123456").nameSurname("Test User").password("password")
                .creditScore(new CreditScore(1000)).build();
        CreditScore creditScore = CreditScore.builder()
                .creditScore(1200)
                .build();
        testUser.setCreditScore(creditScore);
    }

    @Test
    void deleteUser_whenUserFound_shouldReturnUserDeletedMessage() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        UserResponse response = userService.deleteUser(1L);

        assertEquals("User is deleted", response.getResponse());
    }

    @Test
    void deleteUser_whenUserNotFound_shouldThrowUserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    public void getCreditScore_whenUserExists_returnCreditScore() {
        when(userRepository.findByIdNumber(anyString())).thenReturn(Optional.of(testUser));

        int result = userService.getCreditScore("123456789");

        assertEquals(1200, result);
    }

    @Test
    public void getUserByIdNumber_whenUserExists_returnUser() {
        when(userRepository.findByIdNumber(anyString())).thenReturn(Optional.of(testUser));

        User result = userService.getUserByIdNumber("123456789");

        assertEquals(testUser, result);
    }


    @Test
    void updateUser_whenUserFound_shouldReturnUserUpdatedMessage() {
        // Arrange
        Long userId = 1L;
        UserInputDTO userInputDTO = UserInputDTO.builder().nameSurname("Test User 2").password("newPassword").build();
        User foundUser = User.builder().id(userId).idNumber("123456").nameSurname("Test User").password("password")
                .creditScore(new CreditScore(1000)).build();
        CreditScore creditScore = CreditScore.builder()
                .creditScore(1200)
                .build();
        foundUser.setCreditScore(creditScore);
        when(userRepository.findById(userId)).thenReturn(Optional.of(foundUser));

        // Act
        UserResponse userResponse = userService.updateUser(userId, userInputDTO);

        // Assert
        assertNotNull(userResponse);
        assertEquals("User is updated", userResponse.getResponse());
        verify(userRepository, times(1)).save(foundUser);
    }

    @Test
    void updateUser_whenUserNotFound_shouldThrowUserNotFoundException() {
        // Arrange
        Long userId = 1L;
        UserInputDTO userInputDTO = UserInputDTO.builder().nameSurname("Test User 2").password("newPassword").build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, userInputDTO));
    }

    @Test
    void updateUser_whenNullInputDTO_shouldThrowNullPointerException() {
        // Arrange
        Long userId = 1L;

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, null));
    }

    @Test
    void findUserById_whenUserExists_shouldReturnUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
    }

    @Test
    void findUserById_whenUserDoesNotExist_shouldReturnEmptyOptional() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void generateCreditScore_shouldReturnGeneratedCreditScore() {
        int generatedCreditScore = 1200;
        when(creditScoreService.generateCreditScore()).thenReturn(generatedCreditScore);

        int result = userService.generateCreditScore();

        assertEquals(generatedCreditScore, result);
    }

}

