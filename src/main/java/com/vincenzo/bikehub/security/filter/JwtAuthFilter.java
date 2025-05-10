package com.vincenzo.bikehub.security.filter;

import com.vincenzo.bikehub.exceptions.AccountNotFoundException;
import com.vincenzo.bikehub.security.JwtManager;
import com.vincenzo.bikehub.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final UserDetailService userDetailService;

    @Autowired
    public JwtAuthFilter(
            JwtManager jwtManager,
            UserDetailService userDetailService
    ) {
        this.jwtManager = jwtManager;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extractJwtFromRequest(request);

        if (token != null && jwtManager.validateToken(token)) {
            String username = jwtManager.getUsername(token);

            try {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (AccountNotFoundException exc) {
                logger.info("Account not found with username " + username);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}