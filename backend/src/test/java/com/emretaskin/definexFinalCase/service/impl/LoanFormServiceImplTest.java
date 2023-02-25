package com.emretaskin.definexFinalCase.service.impl;

import com.emretaskin.definexFinalCase.dto.request.LoanFormInputDTO;
import com.emretaskin.definexFinalCase.dto.request.LoanFormInquiryDTO;
import com.emretaskin.definexFinalCase.dto.response.LoanFormInquiryResponse;
import com.emretaskin.definexFinalCase.dto.response.LoanResultResponse;
import com.emretaskin.definexFinalCase.entity.LoanForm;
import com.emretaskin.definexFinalCase.entity.User;

import com.emretaskin.definexFinalCase.exception.CreditScoreTooLowException;
import com.emretaskin.definexFinalCase.exception.InvalidLoanFormInquiryException;
import com.emretaskin.definexFinalCase.exception.UserNotFoundException;
import com.emretaskin.definexFinalCase.repository.LoanFormRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.Date;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanFormServiceImplTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private LoanFormRepository loanFormRepository;

    @InjectMocks
    private LoanFormServiceImpl loanFormService;

    @Test
    void applyForLoan_whenUserNotFound_shouldThrowUserNotFoundException() {
        LoanFormInputDTO loanFormInput = new LoanFormInputDTO();
        loanFormInput.setIdNumber("12345678901");


        when(userService.getUserByIdNumber(anyString())).thenReturn(null);

        // Act and assert
        assertThrows(UserNotFoundException.class, () -> loanFormService.applyForLoan(loanFormInput));
    }

    @Test
    void applyForLoan_whenCreditScoreTooLow_shouldThrowCreditScoreTooLowException() {
        // Arrange
        LoanFormInputDTO loanFormInput = LoanFormInputDTO.builder()
                .idNumber("12345678901")
                .monthlyIncome(10000)
                .deposit(10000)
                .build();

        when(userService.getUserByIdNumber(anyString())).thenReturn(new User());
        when(userService.getCreditScore(anyString())).thenReturn(400);

        // Act and assert
        assertThrows(CreditScoreTooLowException.class, () -> loanFormService.applyForLoan(loanFormInput));
    }

    @Test
    void applyForLoan_whenCreditScoreQualifiedAndDepositNotEnough_shouldReturnLoanResultResponse() {
        // Arrange
        LoanFormInputDTO loanFormInput = LoanFormInputDTO.builder()
                .idNumber("12345678901")
                .monthlyIncome(10000)
                .deposit(0)
                .birthDate(Date.valueOf("1998-10-10"))
                .phoneNumber("5555555555")
                .build();

        when(userService.getUserByIdNumber(anyString())).thenReturn(new User());
        when(userService.getCreditScore(anyString())).thenReturn(800);

        // Act
        LoanResultResponse result = loanFormService.applyForLoan(loanFormInput);

        // Assert
        assertEquals("Approved", result.getResult());
        assertEquals(20000, result.getLimit());
    }

    @Test
    void applyForLoan_whenCreditScoreQualifiedAndDepositEnough_shouldReturnLoanResultResponse() {
        // Arrange
        LoanFormInputDTO loanFormInput = LoanFormInputDTO.builder()
                .idNumber("12345678901")
                .monthlyIncome(10000)
                .deposit(5000)
                .birthDate(Date.valueOf("1998-10-10"))
                .phoneNumber("5555555555")
                .build();

        when(userService.getUserByIdNumber(anyString())).thenReturn(new User());
        when(userService.getCreditScore(anyString())).thenReturn(800);

        // Act
        LoanResultResponse result = loanFormService.applyForLoan(loanFormInput);

        // Assert
        assertEquals("Approved", result.getResult());
        assertEquals(21250, result.getLimit());
    }

    @Test
    void applyForLoan_whenCreditScoreAbove1K_shouldReturnLoanResultResponse() {
        // Arrange
        LoanFormInputDTO loanFormInput = LoanFormInputDTO.builder()
                .idNumber("12345678901")
                .monthlyIncome(10000)
                .deposit(200)
                .birthDate(Date.valueOf("1998-10-10"))
                .phoneNumber("5555555555")
                .build();

        when(userService.getUserByIdNumber(anyString())).thenReturn(new User());
        when(userService.getCreditScore(anyString())).thenReturn(1100);

        // Act
        LoanResultResponse result = loanFormService.applyForLoan(loanFormInput);

        // Assert
        assertEquals("Approved", result.getResult());
        assertEquals(40100, result.getLimit());
    }

    @Test
    void applyForLoan_MonthlyIncomeBelow5000_whenCreditScoreQualifiedAndDepositEnough_shouldReturnLoanResultResponse() {
        // Arrange
        LoanFormInputDTO loanFormInput = LoanFormInputDTO.builder()
                .idNumber("12345678901")
                .monthlyIncome(4999)
                .deposit(500)
                .birthDate(Date.valueOf("1998-10-10"))
                .phoneNumber("5555555555")
                .build();

        when(userService.getUserByIdNumber(anyString())).thenReturn(new User());
        when(userService.getCreditScore(anyString())).thenReturn(800);

        // Act
        LoanResultResponse result = loanFormService.applyForLoan(loanFormInput);

        // Assert
        assertEquals("Approved", result.getResult());
        assertEquals(10050, result.getLimit());
    }

    @Test
    void applyForLoan_MonthlyIncomeBelow10000Above5000_whenCreditScoreQualifiedAndDepositEnough_shouldReturnLoanResultResponse() {
        // Arrange
        LoanFormInputDTO loanFormInput = LoanFormInputDTO.builder()
                .idNumber("12345678901")
                .monthlyIncome(7999)
                .deposit(500)
                .birthDate(Date.valueOf("1998-10-10"))
                .phoneNumber("5555555555")
                .build();

        when(userService.getUserByIdNumber(anyString())).thenReturn(new User());
        when(userService.getCreditScore(anyString())).thenReturn(800);

        // Act
        LoanResultResponse result = loanFormService.applyForLoan(loanFormInput);

        // Assert
        assertEquals("Approved", result.getResult());
        assertEquals(20100, result.getLimit());
    }

    @Test
    void getLoanFormApplicationResults_shouldReturnLoanFormInquiryResponses() {
        // Given
        String idNumber = "10101010101";
        Date birthDate = Date.valueOf("1998-01-01");
        LoanFormInquiryDTO loanFormInquiryDTO = new LoanFormInquiryDTO(idNumber, birthDate);

        User user = new User();
        user.setIdNumber(idNumber);

        LoanForm loanForm1 = new LoanForm();
        loanForm1.setUser(user);
        loanForm1.setBirthDate(birthDate);
        loanForm1.setResult("Approved");
        loanForm1.setLoanLimit("10000");

        LoanForm loanForm2 = new LoanForm();
        loanForm2.setUser(user);
        loanForm2.setBirthDate(birthDate);
        loanForm2.setResult("Approved");
        loanForm2.setLoanLimit("10000");

        List<LoanForm> loanForms = new ArrayList<>();
        loanForms.add(loanForm1);
        loanForms.add(loanForm2);

        when(loanFormRepository.findAllByIdNumberAndBirthDate(idNumber, birthDate)).thenReturn(loanForms);

        // When
        List<LoanFormInquiryResponse> actualResponses = loanFormService.getLoanFormApplicationResults(loanFormInquiryDTO);

        // Then
        assertEquals(2, actualResponses.size());
        assertEquals(idNumber, actualResponses.get(0).getIdNumber());
        assertEquals(birthDate, actualResponses.get(0).getBirthDate());
        assertEquals("Approved", actualResponses.get(0).getLoanResult());
        assertEquals("10000", actualResponses.get(0).getLoanLimit());

        assertEquals(idNumber, actualResponses.get(1).getIdNumber());
        assertEquals(birthDate, actualResponses.get(1).getBirthDate());
        assertEquals("Approved", actualResponses.get(1).getLoanResult());
        assertEquals("10000", actualResponses.get(1).getLoanLimit());
    }

    @Test
    void getLoanFormApplicationResults_whenInputInvalid_shouldThrowException() {
        LoanFormInquiryDTO loanFormInquiry = new LoanFormInquiryDTO("11111117711",Date.valueOf("1998-01-01"));

        assertThrows(InvalidLoanFormInquiryException.class, () -> loanFormService.getLoanFormApplicationResults(loanFormInquiry));
    }

}
