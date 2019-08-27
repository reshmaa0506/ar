package com.example.zoom.Service;

import com.example.zoom.Security.JwtTokenUtil;
import com.example.zoom.Dto.AuthRequestDTO;
import com.example.zoom.Dto.AuthResponseDTO;
import com.example.zoom.Repository.AuthUserRepository;
import com.example.zoom.entity.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.zoom.utils.AppConstants.USER;
import static com.example.zoom.utils.AppConstants.USER_ACTIVE;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthUserRepository authUserRepository;


    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UserService userService,
                                     JwtTokenUtil jwtTokenUtil,
                                     AuthUserRepository authUserRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authUserRepository = authUserRepository;
    }


    @Override
    public AuthResponseDTO loginCheck(AuthRequestDTO authReq) {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authReq.getEmail(), authReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final AuthUser authUser = userService
                .findUserByEmailAddress(authReq.getEmail());
        if (authUser != null && !authUser.getIsDisabled()) {
            final String token = jwtTokenUtil.generateToken(authUser,
                    authReq.isRememberMe());
            if (authUser.getRole().equals(USER)) {
                authUser.setRole(USER_ACTIVE);
            }

            authUser.setLastLoggedInDate(LocalDateTime.now());
            authUserRepository.save(authUser);
            return buildAuthResponseDTO(authUser, token);
        }
        return null;

    }
    private AuthResponseDTO buildAuthResponseDTO(final AuthUser authUser,
                                                 final String token) {
        AuthResponseDTO response = new AuthResponseDTO();
        response.setUserId(authUser.getId());
        response.setEmail(authUser.getEmail());
        response.setRole(authUser.getRole());
        response.setToken(token);
        return response;
    }
}
