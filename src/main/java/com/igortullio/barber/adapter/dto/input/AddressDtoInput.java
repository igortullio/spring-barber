package com.igortullio.barber.adapter.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class AddressDtoInput extends AbstractDtoInput {

    @NotBlank
    private String zipCode;

    @Valid
    @NotNull
    private CityAddressDtoInput city;

    @NotBlank
    private String place;

    private String number;

    @NotBlank
    private String district;

    private String complement;

    @Digits(integer = 2, fraction = 7)
    private BigDecimal latitude;

    @Digits(integer = 3, fraction = 7)
    private BigDecimal longitude;

    @Getter
    @Setter
    private static class CityAddressDtoInput extends AbstractDtoInput {

    }

}
