package com.igortullio.barber.adapter.database.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

@Converter(autoApply = true)
public class OffsetDateTimeAttributeConverter implements AttributeConverter<OffsetDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(OffsetDateTime attribute) {
        return Objects.nonNull(attribute) ? new Date(attribute.toInstant().toEpochMilli()) : null;
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(Date dbData) {
        return Objects.nonNull(dbData) ? dbData.toInstant().atOffset(ZoneOffset.UTC) : null;
    }

}
