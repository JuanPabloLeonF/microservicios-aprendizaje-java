package com.auth.jwt.controller;

import com.auth.jwt.dto.AuthUserDto;
import com.auth.jwt.dto.NewUserDto;
import com.auth.jwt.dto.RequestDto;
import com.auth.jwt.dto.TokenDto;
import com.auth.jwt.entities.AuthUser;
import com.auth.jwt.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto) {
        return authService.login(authUserDto);
    }
    @PostMapping("/validate")
    public @ResponseBody ResponseEntity<TokenDto> validate(@RequestParam String token, @RequestBody RequestDto requestDto) {
        return authService.validate(token, requestDto);
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<AuthUser> create(@RequestBody NewUserDto authUserDto) {
        return authService.save(authUserDto);
    }
}
