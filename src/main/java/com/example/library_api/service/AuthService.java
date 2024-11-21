package com.example.library_api.service;

import com.example.library_api.config.JwtUtil;
import com.example.library_api.models.Login;
import com.example.library_api.models.RefreshTokenRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class AuthService {

    private final UserDetailsService usersService;
    private final JwtUtil jwtUtil;

    public Map<Object, Object> signIn(Login login) {
        try {
            UserDetails resUser = usersService.loadUserByUsername(login.getLogin());
            if(resUser != null && login.getPassword().equals(resUser.getPassword())){
                String accessToken = jwtUtil.createToken(resUser.getUsername());
                String refreshToken = jwtUtil.createRefreshToken(resUser.getUsername());
                Map<Object, Object> map = new HashMap<>();
                map.put("accessToken", "Bearer " + accessToken);
                map.put("refreshToken", refreshToken);
                return map;
            }
            throw new NullPointerException();
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    public Map<Object, Object> refreshToken(RefreshTokenRequest request){
        boolean isValidRefreshToken = jwtUtil.validateToken(request.getRefreshToken());
        if (isValidRefreshToken){
            String username = jwtUtil.getUsername(request.getRefreshToken());
            String accessToken = jwtUtil.createToken(username);
            String refreshToken = jwtUtil.createRefreshToken(username);
            Map<Object, Object> response = new HashMap<>();
            response.put("accessToken", "Bearer " + accessToken);
            response.put("refreshToken", refreshToken);
            return response;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token was expired");
    }
}
