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
import com.emretaskin.definexFinalCase.service.LoanFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanFormServiceImpl implements LoanFormService {

    private final LoanFormRepository loanFormRepository;
    private final UserServiceImpl userService;

    @Override
    public LoanResultResponse applyForLoan(LoanFormInputDTO loanFormInput) {
        User user = userService.getUserByIdNumber(loanFormInput.getIdNumber());
        if (user == null) {
            log.info("User not found for id number: {}", loanFormInput.getIdNumber());
            throw new UserNotFoundException("User not found for id number: " + loanFormInput.getIdNumber());
        }

        int creditScore = userService.getCreditScore(loanFormInput.getIdNumber());
        int deposit = loanFormInput.getDeposit();

        if (isNotQualifiedForLoan(creditScore)) {
            log.info("Loan application rejected due to low credit score");
            throw new CreditScoreTooLowException("Loan application rejected due to low credit score");
        }

        int monthlyIncome = loanFormInput.getMonthlyIncome();
        int creditLimit = calculateCreditLimit(creditScore, monthlyIncome);

        if (hasDeposit(deposit)) {
            creditLimit += calculateDepositBonus(creditScore, monthlyIncome, deposit);
        }

        LoanResultResponse loanResultResponse = new LoanResultResponse("Approved", creditLimit);
        log.info("Loan application approved for id number: {} with credit limit: {}", loanFormInput.getIdNumber(), creditLimit);
        saveLoanApplication(loanFormInput, loanResultResponse);
        sendMessageToUsersPhoneNumber(loanFormInput.getPhoneNumber(), loanResultResponse);
        return loanResultResponse;
    }

    @Override
    public List<LoanFormInquiryResponse> getLoanFormApplicationResults(LoanFormInquiryDTO loanFormInquiry) {
        log.info("Getting loan form applications with ID Number {} and Birthdate {}", loanFormInquiry.getIdNumber(),loanFormInquiry.getBirthDate());
        List<LoanForm> loanForms = loanFormRepository.findAllByIdNumberAndBirthDate(
                loanFormInquiry.getIdNumber(),
                loanFormInquiry.getBirthDate()
        );

        if (loanForms.isEmpty()) {
            log.error("Loan form not found, please be sure about your ID Number and birthdate");
            throw new InvalidLoanFormInquiryException("Loan form not found, please be sure about your ID Number and birthdate");
        }

        List<LoanFormInquiryResponse> loanFormInquiryResponses = new ArrayList<>();

        log.info("Loan form Inquiry responses list created and processing data from database");
        for (LoanForm loanForm : loanForms) {
            loanFormInquiryResponses.add(LoanFormInquiryResponse.fromLoanForm(loanForm));
        }
        log.info("loan form Inquiry responses are done");
        return loanFormInquiryResponses;
    }

    private boolean isNotQualifiedForLoan(int creditScore) {
        return creditScore < 500;
    }

    private int calculateCreditLimit(int creditScore, int monthlyIncome) {
        int creditLimitMultiplier = 4;
        if (creditScore < 1000) {
            if (monthlyIncome < 5000) {
                return 10000;
            } else if (monthlyIncome < 10000) {
                return 20000;
            } else {
                return (monthlyIncome * creditLimitMultiplier) / 2;
            }
        }
        return monthlyIncome * creditLimitMultiplier;
    }

    private boolean hasDeposit(int deposit) {
        return deposit > 0;
    }

    private int calculateDepositBonus(int creditScore, int monthlyIncome, int deposit) {
        if (creditScore < 1000) {
            if (monthlyIncome < 5000) {
                return (int) (deposit * 0.1);
            } else if (monthlyIncome < 10000) {
                return (int) (deposit * 0.2);
            } else {
                return (int) (deposit * 0.25);
            }
        }
        return (int) (deposit * 0.5);
    }

    private void sendMessageToUsersPhoneNumber(String phoneNumber, LoanResultResponse loanResult) {
        System.out.println("Sending message to " + phoneNumber + " with loan result: " + loanResult.getResult() + " and loan limit: " + loanResult.getLimit());
    }

    private void saveLoanApplication(LoanFormInputDTO loanFormInput, LoanResultResponse loanResult) {
        User user = userService.getUserByIdNumber(loanFormInput.getIdNumber());

        LoanForm loanForm = createLoanApplication(loanFormInput, loanResult, user);
        loanFormRepository.save(loanForm);
    }

    private LoanForm createLoanApplication(LoanFormInputDTO loanFormInput, LoanResultResponse loanResult, User user) {

        return LoanForm.builder()
                .monthlyIncome(loanFormInput.getMonthlyIncome())
                .phoneNumber(loanFormInput.getPhoneNumber())
                .deposit(loanFormInput.getDeposit())
                .birthDate(loanFormInput.getBirthDate())
                .result(loanResult.getResult())
                .loanLimit(String.valueOf(loanResult.getLimit()))
                .user(user)
                .build();
    }
}
