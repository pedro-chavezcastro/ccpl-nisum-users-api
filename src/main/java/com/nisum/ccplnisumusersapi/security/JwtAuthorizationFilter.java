package com.nisum.ccplnisumusersapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.ccplnisumusersapi.crosscutting.constant.MessageErrorEnum;
import com.nisum.ccplnisumusersapi.model.ErrorDto;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final IJWTService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, IJWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {

            String accessToken = jwtService.resolveToken(request);
            if (Objects.isNull(accessToken)) {
                chain.doFilter(request, response);
                return;
            }

            Claims claims = jwtService.resolveClaims(request);

            if (Objects.nonNull(claims)) {
                String email = claims.getSubject();
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {

            ErrorDto error = new ErrorDto();
            error.setCode(MessageErrorEnum.NISUM004.getCode());
            error.setMessage(String.format(MessageErrorEnum.NISUM004.getDescription(), e.getMessage()));
            error.setTimeStamp(String.valueOf(LocalDateTime.now()));

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getWriter(), error);

        }

        chain.doFilter(request, response);
    }

}