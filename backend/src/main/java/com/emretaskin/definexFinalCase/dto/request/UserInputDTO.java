package com.emretaskin.definexFinalCase.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDTO {

    @NotNull
    private String nameSurname;

    @NotNull
    private String password;
}
