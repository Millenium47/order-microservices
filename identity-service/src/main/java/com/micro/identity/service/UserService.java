package com.micro.identity.service;

import com.micro.identity.dto.RegisterRequest;
import com.micro.identity.entity.User;
import com.micro.identity.exception.AlreadyInUseException;
import com.micro.identity.exception.NotFoundException;
import com.micro.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(RegisterRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new AlreadyInUseException("Email already in use!");
        }
        return repository.save(new User(request.name(), request.email(), passwordEncoder.encode(request.password())));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
