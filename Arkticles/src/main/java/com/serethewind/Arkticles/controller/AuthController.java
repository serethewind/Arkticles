package com.serethewind.Arkticles.controller;

import com.serethewind.Arkticles.dto.AuthResponseDto;
import com.serethewind.Arkticles.dto.users.UserLoginRequestDto;
import com.serethewind.Arkticles.dto.users.UserRegisterRequestDto;
import com.serethewind.Arkticles.service.auth.AuthServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/arkticles/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto){
        return new ResponseEntity<>(authService.registerUser(userRegisterRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        return new ResponseEntity<>(authService.loginUser(userLoginRequestDto), HttpStatus.OK);
    }

}
