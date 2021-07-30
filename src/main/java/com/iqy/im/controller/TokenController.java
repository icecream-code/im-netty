package com.iqy.im.controller;

import com.iqy.im.security.AuthUser;
import com.iqy.im.security.JwtToken;
import com.iqy.im.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/token")
@RestController
public class TokenController extends BaseController {

    private final JwtUtil jwtUtil;

    public TokenController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/refresh")
    public JwtToken refreshToken() {
        AuthUser user = AuthUser.builder()
                .id(getUserId())
                .roles(getRoles())
                .permissions(getPermissions())
                .build();
        return jwtUtil.generateToken(user);
    }
}
