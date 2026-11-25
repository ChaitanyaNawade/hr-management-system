package com.aarambh.hr_management.service;

import com.aarambh.hr_management.dto.LoginRequest;
import com.aarambh.hr_management.dto.LoginResponse;
import com.aarambh.hr_management.dto.RegisterRequest;
import com.aarambh.hr_management.dto.UserResponse;
import com.aarambh.hr_management.entity.User;
import com.aarambh.hr_management.enums.Role;
import com.aarambh.hr_management.repository.UserRepository;
import com.aarambh.hr_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserResponse registerUser(RegisterRequest request)
    {
        //Check whether the email is already exists
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new IllegalArgumentException("Email is already registered");
        }

        //Create a new User object
        User user = new User();

        //Uses BCrypt to has the password
        //Assign the default role Employee
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EMPLOYEE);

        //Save in mySql
        User savedUser = userRepository.save(user);

        //Return thr response without the password
        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;
    }

    public LoginResponse login(LoginRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail());

        LoginResponse response = new LoginResponse();

        response.setToken(token);
        response.setTokenType("Bearer");
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }
}
