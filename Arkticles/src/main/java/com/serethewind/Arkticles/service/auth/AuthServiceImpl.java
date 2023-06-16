package com.serethewind.Arkticles.service.auth;

import com.serethewind.Arkticles.dto.users.UserLoginRequestDto;
import com.serethewind.Arkticles.dto.users.UserRegisterRequestDto;
import com.serethewind.Arkticles.entity.RolesEntity;
import com.serethewind.Arkticles.entity.UsersEntity;
import com.serethewind.Arkticles.exceptions.BadRequestException;
import com.serethewind.Arkticles.repository.RolesRepository;
import com.serethewind.Arkticles.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthServiceInterface {
    private UserRepository userRepository;
    private RolesRepository rolesRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        //check if username or email exist
        if (userRepository.existsByUsernameOrEmail(userRegisterRequestDto.getUsername(), userRegisterRequestDto.getEmail())) {
            throw new BadRequestException("Username or email already exists. Choose another one.");
        } else {

            RolesEntity roles = rolesRepository.findByName("USER");
            UsersEntity user = UsersEntity.builder()
                    .fullname(userRegisterRequestDto.getFullname())
                    .username(userRegisterRequestDto.getUsername())
                    .email(userRegisterRequestDto.getEmail())
                    .password(passwordEncoder.encode(userRegisterRequestDto.getPassword()))
                    .roles(Collections.singleton(roles))
                    .build();

            userRepository.save(user);
            return "User successfully registered";
        }
    }

    @Override
    public String loginUser(UserLoginRequestDto userLoginRequestDto) {
        //get authentication object from the
        //get context from security context holder and set the authentication object with the authentication object gotten from the authentication manager.
        //return string login successful

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User login successfully";
    }
}

