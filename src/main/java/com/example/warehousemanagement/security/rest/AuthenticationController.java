package com.example.warehousemanagement.security.rest;

import com.example.warehousemanagement.security.component.JwtTokenUtil;
import com.example.warehousemanagement.security.domain.dto.UserLoginDTO;
import com.example.warehousemanagement.security.domain.dto.UserLoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Value("${security.jwt.header:Authorization}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        // generate token from UserDetails object created by the UserDetailsService
        final UserDetails userDetails = getAuthenticationUserDetails(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // add created token to response header
        final HttpHeaders headers = new HttpHeaders();
        headers.add(tokenHeader, token);



        // Return the token
        final UserLoginResponseDTO responseElement = new UserLoginResponseDTO();
        responseElement.setAccessToken(token);
        responseElement.setTokenType("bearer");
        responseElement.setRoles(userDetails.getAuthorities().stream().map(String::valueOf).toList());

        return new ResponseEntity<>(responseElement, headers, HttpStatus.OK);
    }

    private UserDetails getAuthenticationUserDetails(final String username, final String password) {
        // Perform the security
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (UserDetails) authentication.getPrincipal();
    }
}
