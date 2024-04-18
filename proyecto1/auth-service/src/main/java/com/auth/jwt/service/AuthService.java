package com.auth.jwt.service;

import com.auth.jwt.dto.AuthUserDto;
import com.auth.jwt.dto.NewUserDto;
import com.auth.jwt.dto.RequestDto;
import com.auth.jwt.dto.TokenDto;
import com.auth.jwt.entities.AuthUser;
import com.auth.jwt.repository.IAuthUserRepository;
import com.auth.jwt.security.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final IAuthUserRepository iAuthUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(IAuthUserRepository iAuthUserRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.iAuthUserRepository = iAuthUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public ResponseEntity<AuthUser> save(NewUserDto authUserDto) {
        Optional<AuthUser> userOptional = iAuthUserRepository.findByUserName(authUserDto.getUserName());
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            String password = passwordEncoder.encode(authUserDto.getPassword());
            AuthUser authUser = new AuthUser();
            authUser.setUserName(authUserDto.getUserName());
            authUser.setRole(authUserDto.getRole());
            authUser.setPassword(password);
            return new ResponseEntity<>(iAuthUserRepository.save(authUser), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<TokenDto> login(AuthUserDto authUserDto) {
        Optional<AuthUser> userOptional = iAuthUserRepository.findByUserName(authUserDto.getUserName());
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (passwordEncoder.matches(authUserDto.getPassword(), userOptional.get().getPassword())) {
            TokenDto tokenDto = new TokenDto(jwtProvider.createToken(userOptional.get()));
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<TokenDto> validate(String token, RequestDto requestDto) {
        if (!jwtProvider.validateToken(token, requestDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            String userName = jwtProvider.getUserNameFromToken(token);
            if (iAuthUserRepository.findByUserName(userName).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                TokenDto newToken = new TokenDto(token);
                return new ResponseEntity<>(newToken, HttpStatus.OK);
            }
        }
    }
}
