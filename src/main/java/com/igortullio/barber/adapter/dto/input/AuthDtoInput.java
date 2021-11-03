package com.igortullio.barber.adapter.dto.input;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthDtoInput {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

}
