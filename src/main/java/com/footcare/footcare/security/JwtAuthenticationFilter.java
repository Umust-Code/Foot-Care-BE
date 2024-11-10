package com.footcare.footcare.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = getJwtFromRequest(request);
        String refreshToken = getRefreshTokenFromRequest(request);

        try {
            if (StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
                // Access 토큰이 유효할 때 인증 설정
                setAuthenticationContext(accessToken, request);
            } else if (StringUtils.hasText(refreshToken) && jwtTokenProvider.validateToken(refreshToken)) {
                // Access 토큰이 만료되고 Refresh 토큰이 유효한 경우 새 Access 토큰 발급
                String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
                String newAccessToken = jwtTokenProvider.generateAccessToken(username); // 새 Access 토큰 생성
                response.setHeader("New-Access-Token", "Bearer " + newAccessToken); // 새 Access 토큰을 응답 헤더로 설정
                setAuthenticationContext(newAccessToken, request); // 새로 발급된 Access 토큰으로 인증 설정
            }
        } catch (Exception ex) {
            System.err.println("Could not set user authentication in security context: " + ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username, null, null);  // 필요한 경우 권한을 추가 가능
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization-Refresh");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
