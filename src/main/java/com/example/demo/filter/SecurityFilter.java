package com.example.demo.filter;

import com.example.demo.common.TokenProvider;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/19/2023
 * Time: 8:26 PM
 */
@Controller
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {
    private static final   SecurityFilter SECURITY_FILTER= new SecurityFilter();

    public SecurityFilter() {
    }

    public static SecurityFilter getInstance(){
        return SECURITY_FILTER;
    }

    private  TokenProvider tokenProvider;

    private  UserService userService;
    @Autowired
    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        try {
            log.info("doFilterInternal");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = tokenProvider.verifyToken(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities()));
        } catch (Exception e) {
            log.warn("request with token error: {}", e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
