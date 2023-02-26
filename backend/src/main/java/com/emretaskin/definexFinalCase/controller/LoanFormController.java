package com.emretaskin.definexFinalCase.controller;

import com.emretaskin.definexFinalCase.dto.request.LoanFormInputDTO;
import com.emretaskin.definexFinalCase.dto.request.LoanFormInquiryDTO;
import com.emretaskin.definexFinalCase.dto.response.LoanFormInquiryResponse;
import com.emretaskin.definexFinalCase.dto.response.LoanResultResponse;
import com.emretaskin.definexFinalCase.service.impl.LoanFormServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class LoanFormController {

    private final LoanFormServiceImpl loanFormService;

    @Operation(summary = "Apply for loan with Loan Form")
    @PostMapping("/apply")
    public LoanResultResponse createLoanForm(@Valid @RequestBody LoanFormInputDTO loanFormInput){
        log.info("Received loan application from user with phone number {}", loanFormInput.getPhoneNumber());
        LoanResultResponse loanResultResponse = loanFormService.applyForLoan(loanFormInput);
        log.info("Loan application result for user with phone number {}: {}", loanFormInput.getPhoneNumber(), loanResultResponse);
        return loanResultResponse;
    }

    @Operation(summary = "Get loan application results and credit limits by ID Number and Birthdate")
    @PostMapping("/results")
    public List<LoanFormInquiryResponse> getLoanFormApplicationResults(@Valid @RequestBody LoanFormInquiryDTO loanFormInquiry){
        log.info("Loan form Inquiry received for id number: {} and birth date: {}", loanFormInquiry.getIdNumber(), loanFormInquiry.getBirthDate());
        return loanFormService.getLoanFormApplicationResults(loanFormInquiry);
    }

}
