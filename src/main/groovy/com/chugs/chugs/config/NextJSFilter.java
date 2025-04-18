package com.chugs.chugs.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class NextJSFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Decide whether to filter the request or not
        if (!Collections.list(request.getHeaderNames()).contains("x-auth-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Check credentials and [authenticate | reject ]
        // set an actual access token loaded from a component in the future based on the .yml file and use env vars
        if (!Objects.equals(request.getHeader("x-auth-token"), "123")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Content-Type", "text/plain; charset=UTF-8");
            response.getWriter().write("Nope");
            return;
        }
        var auth = new NextJSAuthenticationToken();
        var newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(auth);
        SecurityContextHolder.setContext(newContext);

        // 4. Call next
        filterChain.doFilter(request, response);
    }
}
