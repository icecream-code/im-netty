package com.iqy.im.security.filter;

import com.iqy.im.security.AuthUser;
import com.iqy.im.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 授权过滤器
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public AuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        // 解析token
        if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(token);
            if (null != usernamePasswordAuthenticationToken) {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // 放行
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        // Invalid or expired token
        if (!jwtUtil.verify(token)) {
            return null;
        }
        // Get user information
        AuthUser user = jwtUtil.getAuthUser(token);
        if (null == user) {
            return null;
        }

        List<SimpleGrantedAuthority> authorities = user.getPermissions().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        user.getRoles().forEach(s -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + s));
        });

        return new UsernamePasswordAuthenticationToken(user.getId(), null, authorities);
    }
}
