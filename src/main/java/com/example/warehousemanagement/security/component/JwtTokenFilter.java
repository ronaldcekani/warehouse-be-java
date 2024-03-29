package com.example.warehousemanagement.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);

    /**
     * The <code>UserDetailsService</code> to load user detail data.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * The util that internally handles tokens.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Name of the header attribute where the token is expected.
     */
    @Value("${security.jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {
        final String authToken = request.getHeader(tokenHeader);
        String username = null;
        if (authToken != null && !authToken.isEmpty()) {
            username = jwtTokenUtil.getUsernameFromToken(authToken);
        }

        LOGGER.debug("Checking authentication für user '{}'...", username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            try {
                // load user data from database, throws UsernameNotFoundException if no user with username found
                userDetails = userDetailsService.loadUserByUsername(username);

                // check token validity and set security context
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    final UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.debug("Authenticated user '{}', setting security context.", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (UsernameNotFoundException e) {
                LOGGER.debug("No user '{}' found in database: {}", username, e.getMessage(), e);
            }
        }

        chain.doFilter(request, response);
    }
}
