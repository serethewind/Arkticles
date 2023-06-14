package com.serethewind.Arkticles.service.users.serviceImpl;

import com.serethewind.Arkticles.dto.users.UserRequestDto;
import com.serethewind.Arkticles.dto.users.UserResponseDto;
import com.serethewind.Arkticles.entity.UsersEntity;
import com.serethewind.Arkticles.exceptions.BadRequestException;
import com.serethewind.Arkticles.exceptions.ResourceNotFoundException;
import com.serethewind.Arkticles.repository.UserRepository;
import com.serethewind.Arkticles.service.users.UsersServiceInterface;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersServiceInterface {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        UsersEntity user = modelMapper.map(userRequestDto, UsersEntity.class);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto viewSingleUser(Long id) {
        UsersEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> viewAllUsers() {
        List<UsersEntity> allUsers = userRepository.findAll();
        return allUsers.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUserInformation(Long id, UserRequestDto userRequestDto) {
        if (!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not found");
        }

        UsersEntity foundUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        modelMapper.map(userRequestDto, foundUser);
        userRepository.save(foundUser);
        return modelMapper.map(foundUser, UserResponseDto.class);
    }

    @Override
    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw new BadRequestException("User not found. Delete option failed");
        }

        userRepository.deleteById(id);
        return "User with " + id + " successfully deleted.";
    }
}
