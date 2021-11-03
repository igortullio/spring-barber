package com.igortullio.barber.adapter.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddressDtoOutput extends AbstractDtoOutput {

    private String zipCode;
    private CityDtoOutput city;
    private String place;
    private String number;
    private String district;
    private String complement;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
