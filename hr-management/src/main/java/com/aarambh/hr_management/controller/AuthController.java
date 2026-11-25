package com.aarambh.hr_management.controller;

import com.aarambh.hr_management.dto.LoginRequest;
import com.aarambh.hr_management.dto.LoginResponse;
import com.aarambh.hr_management.dto.RegisterRequest;
import com.aarambh.hr_management.dto.UserResponse;
import com.aarambh.hr_management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{
   private final UserService userService;

   @PostMapping("/register")
   public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request)
   {
      UserResponse response = userService.registerUser(request);

      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   @PostMapping("/login")
   public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request)
   {
       LoginResponse response = userService.login(request);

       return ResponseEntity.status(HttpStatus.OK).body(response);
   }
}
