package com.igortullio.barber.adapter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Objects;

@Configuration
public class LoggingConfig {

    @Bean
    @Scope("prototype")
    public Logger logger(final InjectionPoint injectionPoint) {
        if (Objects.nonNull(injectionPoint.getMethodParameter())) {
            return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
        }

        if (Objects.nonNull(injectionPoint.getField())) {
            return LoggerFactory.getLogger(injectionPoint.getField().getDeclaringClass());
        }

        throw new IllegalArgumentException();
    }

}
