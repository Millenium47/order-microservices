package com.micro.identity.controller;

import com.micro.identity.dto.LoginRequest;
import com.micro.identity.dto.LoginResponse;
import com.micro.identity.dto.RegisterRequest;
import com.micro.identity.entity.User;
import com.micro.identity.service.UserService;
import com.micro.identity.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User addNewUser(@RequestBody RegisterRequest request) {
        return userService.saveUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        return new LoginResponse(jwtService.generateToken(loginRequest.email()), "Bearer");
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public void validateToken(@RequestParam("token") String token) {
        jwtService.validateToken(token);
    }
}
