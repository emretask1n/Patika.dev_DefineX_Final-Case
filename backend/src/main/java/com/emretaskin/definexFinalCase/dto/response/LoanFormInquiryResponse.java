package com.emretaskin.definexFinalCase.dto.response;

import com.emretaskin.definexFinalCase.entity.LoanForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanFormInquiryResponse {

    private String idNumber;
    private Date birthDate;
    private String loanResult;
    private String loanLimit;

    public static LoanFormInquiryResponse fromLoanForm(LoanForm loanForm) {
        return LoanFormInquiryResponse.builder()
                .idNumber(loanForm.getUser().getIdNumber())
                .birthDate(loanForm.getBirthDate())
                .loanResult(loanForm.getResult())
                .loanLimit(loanForm.getLoanLimit())
                .build();
    }
}
