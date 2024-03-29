package com.serethewind.Arkticles.service.auth;

import com.serethewind.Arkticles.dto.AuthResponseDto;
import com.serethewind.Arkticles.dto.users.UserLoginRequestDto;
import com.serethewind.Arkticles.dto.users.UserRegisterRequestDto;
import com.serethewind.Arkticles.entity.RolesEntity;
import com.serethewind.Arkticles.entity.TokenEntity;
import com.serethewind.Arkticles.entity.TokenType;
import com.serethewind.Arkticles.entity.UsersEntity;
import com.serethewind.Arkticles.exceptions.BadRequestException;
import com.serethewind.Arkticles.repository.RolesRepository;
import com.serethewind.Arkticles.repository.TokenRepository;
import com.serethewind.Arkticles.repository.UserRepository;
import com.serethewind.Arkticles.securityConfig.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthServiceInterface {
    private UserRepository userRepository;
    private RolesRepository rolesRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private TokenRepository tokenRepository;
    private JWTService jwtService;

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
//            String jwtToken = jwtService.generateToken(user.getUsername());
            return "User successfully registered";
        }
    }

    @Override
    public AuthResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        //get authentication object from the
        //get context from security context holder and set the authentication object with the authentication object gotten from the authentication manager.
        //return string login successful
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication.getName());
        UsersEntity user = userRepository.findUserByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        revokeValidTokens(user);
        TokenEntity tokenEntity = TokenEntity.builder()
                .user(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(tokenEntity);

        return new AuthResponseDto(token);
    }


    private void revokeValidTokens(UsersEntity users) {
        List<TokenEntity> tokenEntityList = tokenRepository.findAllValidTokensByUser(users.getId());
        if (tokenEntityList.isEmpty())
            return;
        tokenEntityList.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(tokenEntityList);
    }
}

