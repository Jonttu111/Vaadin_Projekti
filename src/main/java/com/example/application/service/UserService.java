package com.example.application.service;

import com.example.application.entitys.Measurement;
import com.example.application.entitys.Role;
import com.example.application.entitys.User;
import com.example.application.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        registerUser("1", "1");
        registerUser("2", "2");
        registerUser("admin", "123");

        var x = getAllUsers();

        System.out.println(x);
    }


    public boolean isUserRegistered(String username) {
        return userRepository.existsByUsername(username);
    }

    public void registerUser(String username, String password) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("User already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(passwordEncoder.encode(password));

        //TODO: REMOVE when db is ready. This is more then bad idea :D
        if(username.equals("admin")){
            newUser.setRoles(Set.of(Role.ADMIN, Role.USER));
        }
        else{
            newUser.setRoles(Set.of(Role.USER));
        }
        userRepository.save(newUser);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return true;
        }
        return false;

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }





}
