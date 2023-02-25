package com.emretaskin.definexFinalCase.service;

import com.emretaskin.definexFinalCase.dto.request.LoanFormInputDTO;
import com.emretaskin.definexFinalCase.dto.request.LoanFormInquiryDTO;
import com.emretaskin.definexFinalCase.dto.response.LoanFormInquiryResponse;
import com.emretaskin.definexFinalCase.dto.response.LoanResultResponse;

import java.util.List;

public interface LoanFormService {

    LoanResultResponse applyForLoan(LoanFormInputDTO loanFormInput);

    List<LoanFormInquiryResponse> getLoanFormApplicationResults(LoanFormInquiryDTO loanFormInquiry);


}
