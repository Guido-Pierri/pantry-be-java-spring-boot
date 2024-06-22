package com.guidopierri.pantrybe.config;

import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.services.UserService;
import com.guidopierri.pantrybe.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Autowired
    public JwtRequestFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String tokenProvider = null;
        String token = null;


        try {

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                username = jwtUtil.extractUsernameWithKey(token);
                tokenProvider = "custom";
            } else if (authorizationHeader != null && authorizationHeader.startsWith("Oauth2 ")) {
                token = authorizationHeader.substring(7);
                Jwt jwt = jwtUtil.jwtDecoder().decode(token);
                username = jwt.getClaimAsString("email");
                tokenProvider = "Oauth2";
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && tokenProvider.equals("custom")) {
                User user = userService.getUserByEmail(username);
                Boolean isTokenValid = jwtUtil.validateTokenWithKey(token, user);
                if (Boolean.TRUE.equals(isTokenValid)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } else if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && tokenProvider.equals("Oauth2")) {
                //FIXME: replace with loadUserByEmail
                UserDetails userDetails = userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Token expired");
        }

        filterChain.doFilter(request, response);

    }


}
