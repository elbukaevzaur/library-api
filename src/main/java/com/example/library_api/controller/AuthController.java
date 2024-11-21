package com.example.library_api.controller;

import com.example.library_api.models.Login;
import com.example.library_api.models.RefreshTokenRequest;
import com.example.library_api.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    AuthenticationManager authenticationManager;
    private final AuthService authService;

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestBody Login login) {
        try {
            Map<Object, Object> response = authService.signIn(login);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "refreshtoken", method = RequestMethod.POST)
    public ResponseEntity refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            Map<Object, Object> response = authService.refreshToken(request);
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity(e.getBody(), HttpStatus.UNAUTHORIZED);
        }
    }
}