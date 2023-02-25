package com.emretaskin.definexFinalCase.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanFormInputDTO {

    @NotNull
    private String idNumber;

    @NotNull
    private String nameSurname;

    @NotNull
    private int monthlyIncome;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Date birthDate;

    private int deposit;

}
