package com.emretaskin.definexFinalCase.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanFormInquiryDTO {

    @NotNull
    private String idNumber;

    @NotNull
    private Date birthDate;
}
