package com.nisum.ccplnisumusersapi.security;

import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

public interface IJWTService {

    String generateJwtToken(String username);

    String resolveToken(HttpServletRequest request);

    Claims resolveClaims(HttpServletRequest request);

}