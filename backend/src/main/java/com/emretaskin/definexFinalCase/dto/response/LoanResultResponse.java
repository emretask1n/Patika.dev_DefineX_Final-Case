package com.emretaskin.definexFinalCase.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResultResponse {

    private String result;
    private int limit;

}
