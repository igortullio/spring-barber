package com.igortullio.barber.adapter.controller;

import com.igortullio.barber.adapter.config.filter.JwtTokenUtil;
import com.igortullio.barber.adapter.database.entity.UserEntity;
import com.igortullio.barber.adapter.dto.input.AuthDtoInput;
import com.igortullio.barber.adapter.dto.output.AuthDtoOutput;
import com.igortullio.barber.core.exception.BarberException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDtoOutput> login(@RequestBody @Valid AuthDtoInput request) {
        try {
            var authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            var authenticate = authenticationManager.authenticate(authentication);

            var userEntity = (UserEntity) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(userEntity))
                    .body(modelMapper.map(userEntity, AuthDtoOutput.class));
        } catch (InternalAuthenticationServiceException | BadCredentialsException exception) {
            throw new BarberException(exception.getMessage(), exception);
        }
    }

}
