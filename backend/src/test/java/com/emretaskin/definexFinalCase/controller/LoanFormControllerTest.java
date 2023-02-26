package com.emretaskin.definexFinalCase.controller;

import com.emretaskin.definexFinalCase.dto.request.LoanFormInputDTO;
import com.emretaskin.definexFinalCase.dto.request.LoanFormInquiryDTO;
import com.emretaskin.definexFinalCase.dto.response.LoanFormInquiryResponse;
import com.emretaskin.definexFinalCase.dto.response.LoanResultResponse;
import com.emretaskin.definexFinalCase.exception.InvalidLoanFormInquiryException;
import com.emretaskin.definexFinalCase.service.impl.LoanFormServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



class LoanFormControllerTest {

    @Mock
    private LoanFormServiceImpl loanFormService;

    @InjectMocks
    private LoanFormController loanFormController = new LoanFormController(loanFormService);

    @BeforeEach
    public void setUp() {
        loanFormService = mock(LoanFormServiceImpl.class);
        loanFormController = new LoanFormController(loanFormService);
    }

    @Test
    @DisplayName("Test create loan form application successfully")
    public void testCreateLoanForm() {
        LoanFormInputDTO loanFormInput = LoanFormInputDTO.builder()
                .idNumber("123")
                .birthDate(Date.valueOf("1998-07-17"))
                .deposit(1000)
                .phoneNumber("123456789")
                .monthlyIncome(1000)
                .build();

        LoanResultResponse expectedResponse = new LoanResultResponse();
        expectedResponse.setResult("Approved");

        when(loanFormService.applyForLoan(loanFormInput)).thenReturn(expectedResponse);

        LoanResultResponse actualResponse = loanFormController.createLoanForm(loanFormInput);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test get loan form application results successfully")
    public void testGetLoanFormApplicationResults() {
        LoanFormInquiryDTO loanFormInquiryDTO = new LoanFormInquiryDTO();
        loanFormInquiryDTO.setIdNumber("123");
        loanFormInquiryDTO.setBirthDate(Date.valueOf("1998-07-17"));

        LoanFormInquiryResponse loanFormInquiryResponse = new LoanFormInquiryResponse();
        loanFormInquiryResponse.setIdNumber("123");
        loanFormInquiryResponse.setBirthDate(Date.valueOf("1998-07-17"));
        loanFormInquiryResponse.setLoanResult("Approved");
        loanFormInquiryResponse.setLoanLimit("10000");

        List<LoanFormInquiryResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(loanFormInquiryResponse);

        when(loanFormService.getLoanFormApplicationResults(loanFormInquiryDTO)).thenReturn(expectedResponse);

        List<LoanFormInquiryResponse> actualResponse = loanFormController.getLoanFormApplicationResults(loanFormInquiryDTO);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test get loan form applications throws Invalid loan form Inquiry")
    public void testGetLoanFormApplicationResultsThrowsException() {
        LoanFormInquiryDTO loanFormInquiryDTO = new LoanFormInquiryDTO();
        loanFormInquiryDTO.setIdNumber("123");
        loanFormInquiryDTO.setBirthDate(Date.valueOf("1998-07-17"));

        when(loanFormService.getLoanFormApplicationResults(loanFormInquiryDTO)).thenThrow(new InvalidLoanFormInquiryException("Invalid Loan Form Inquiry"));

        assertThrows(InvalidLoanFormInquiryException.class, () -> {
            loanFormController.getLoanFormApplicationResults(loanFormInquiryDTO);
        });
    }



}