package com.nisum.ccplnisumusersapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.ccplnisumusersapi.crosscutting.constant.MessageErrorEnum;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.repository.IUserRepository;
import com.nisum.ccplnisumusersapi.model.ErrorDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final IJWTService jwtService;
    private final IUserRepository userRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, IJWTService jwtService, IUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/login", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        username = Objects.nonNull(username) ? username.trim() : "";
        password = Objects.nonNull(password) ? password : "";

        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);

        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String username = authResult.getName();
        String token = this.jwtService.generateJwtToken(username);

        Optional<UserEntity> userEntityOptional = this.userRepository.findByEmail(username);

        if (userEntityOptional.isPresent()) {
            this.updateLastLoginAndTokenUser(token, userEntityOptional.get());

            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("token", token);

            response.getWriter().write(new ObjectMapper().writeValueAsString(map));
            response.setStatus(200);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        ErrorDto error = new ErrorDto();
        error.setCode(MessageErrorEnum.NISUM005.getCode());
        error.setMessage(String.format(MessageErrorEnum.NISUM005.getDescription(), failed.getMessage()));
        error.setTimeStamp(String.valueOf(LocalDateTime.now()));

        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    }

    private void updateLastLoginAndTokenUser(String token, UserEntity userEntity) {
        userEntity.setLastLogin(LocalDateTime.now());
        userEntity.setAccessToken(token);
        this.userRepository.save(userEntity);
    }
}
