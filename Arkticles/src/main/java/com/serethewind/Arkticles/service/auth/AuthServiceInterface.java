package com.serethewind.Arkticles.service.auth;

import com.serethewind.Arkticles.dto.AuthResponseDto;
import com.serethewind.Arkticles.dto.users.UserLoginRequestDto;
import com.serethewind.Arkticles.dto.users.UserRegisterRequestDto;

public interface AuthServiceInterface {

    String registerUser(UserRegisterRequestDto userRegisterRequestDto);

    AuthResponseDto loginUser(UserLoginRequestDto userLoginRequestDto);
}
