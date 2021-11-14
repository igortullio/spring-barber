package com.igortullio.barber.adapter.dto.output;

import com.igortullio.barber.adapter.dto.input.AbstractDtoInput;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BarbershopFindAllDtoOutput extends AbstractDtoOutput {

    private String name;
    private Boolean active;
    private AddressDtoBarbershopFindAllOutput address;

    @Getter
    @Setter
    private static class AddressDtoBarbershopFindAllOutput extends AbstractDtoInput {

        private String zipCode;
        private String cityName;
        private String place;
        private String number;
        private String district;
        private String complement;
        private BigDecimal latitude;
        private BigDecimal longitude;

    }

}
