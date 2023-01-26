package com.example.lab2.controller;

import com.example.lab2.dto.AuthenticationRequestDto;
import com.example.lab2.dto.UserDto;
import com.example.lab2.model.Role;
import com.example.lab2.model.Status;
import com.example.lab2.model.User;
import com.example.lab2.repository.UserRepository;
import com.example.lab2.security.jwt.JwtTokenProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        System.out.println(requestDto);
        try {
            String username = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userRepository.findUserByLogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDto.setRole(user.getRole());

            String token = jwtTokenProvider.createToken(username, user.getRole());

            Map<Object, Object> response = new HashMap<>();
            response.put("user", userDto);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/auth/create")
    public ResponseEntity createUser(@RequestBody AuthenticationRequestDto dto) {

        User user = new User(dto.getLogin(), jwtTokenProvider.passwordEncoder().encode(dto.getPassword()), Role.USER, Status.ACTIVE);
        userRepository.save(user);
        return ResponseEntity.ok(null);
    }
}
