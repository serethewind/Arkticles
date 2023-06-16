package com.serethewind.Arkticles.controller;

import com.serethewind.Arkticles.dto.users.UserRegisterRequestDto;
import com.serethewind.Arkticles.service.auth.AuthServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthServiceImpl authService;

    @PostMapping()
    public ResponseEntity<String> register(UserRegisterRequestDto userRegisterRequestDto){
        return new ResponseEntity<>(authService.registerUser(userRegisterRequestDto), HttpStatus.CREATED);
    }

}
