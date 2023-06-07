package com.serethewind.Arkticles.controller;

import com.serethewind.Arkticles.dto.users.UserRequestDto;
import com.serethewind.Arkticles.dto.users.UserResponseDto;
import com.serethewind.Arkticles.service.users.serviceImpl.UsersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arkticles/v1/users")
@AllArgsConstructor
public class UsersController {

    private UsersServiceImpl usersService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(usersService.viewAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getSingleUser(@PathVariable("id") Long userId){
        return ResponseEntity.ok(usersService.viewSingleUser(userId));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(usersService.createUser(userRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserDetails(@PathVariable("id") Long id, @RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(usersService.updateUserInformation(id, userRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        return ResponseEntity.ok(usersService.deleteUser(userId));
    }
}
