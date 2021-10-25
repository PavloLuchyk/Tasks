package org.project.controller;

import org.project.dto.AuthenticationUserDto;
import org.project.model.Author;
import org.project.security.jwt.JwtTokenProvider;
import org.project.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*", maxAge=3600)
@RestController("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthorService authorService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                        AuthorService authorService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authorService = authorService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationUserDto requestDto) {
        try {
            String username = requestDto.getUsername();
            Authentication authentication   =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, requestDto.getPassword())
                    );
            Author author = authorService.getByEmail(username);
            if (author == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, List.of(author.getRole()));

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("role", author.getRole());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
