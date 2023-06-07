package com.serethewind.Arkticles.service.users;

import com.serethewind.Arkticles.dto.users.UserRequestDto;
import com.serethewind.Arkticles.dto.users.UserResponseDto;

import java.util.List;

public interface UsersServiceInterface {

    UserResponseDto createUser (UserRequestDto userRequestDto);

    UserResponseDto viewSingleUser(Long id);

    List<UserResponseDto> viewAllUsers();

    UserResponseDto updateUserInformation(Long id, UserRequestDto userRequestDto);

    String deleteUser(Long id);
}
