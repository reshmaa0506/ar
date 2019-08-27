package com.example.zoom.Security;


import lombok.extern.slf4j.Slf4j;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.zoom.utils.AppConstants.HEADER_STRING;
import static com.example.zoom.utils.AppConstants.TOKEN_PREFIX;



/**
 * The Jwt Authentication Filter.
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * Filter.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @param chain    the filter chain
     * @throws IOException      the I/O exception
     * @throws ServletException the servlet exception
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, StringUtils.EMPTY);
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.error("Error getting username from token.", e);
            }
        } else {
            log.warn("Couldn't find bearer string, will ignore the header.");
        }
        if (username != null && SecurityContextHolder.getContext()
                .getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                log.info("Authenticated user " + username
                        + ", setting security context.");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}

