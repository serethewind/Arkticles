package com.serethewind.Arkticles.service.users;

import com.serethewind.Arkticles.dto.users.UserRegisterRequestDto;
import com.serethewind.Arkticles.dto.users.UserResponseDto;

import java.util.List;

public interface UsersServiceInterface {

    UserResponseDto createUser (UserRegisterRequestDto userRegisterRequestDto);

    UserResponseDto viewSingleUser(Long id);

    List<UserResponseDto> viewAllUsers();

    UserResponseDto updateUserInformation(Long id, UserRegisterRequestDto userRegisterRequestDto);

    String deleteUser(Long id);
}
