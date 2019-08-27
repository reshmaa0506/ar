package com.example.zoom.Service;

import com.example.zoom.Repository.AuthUserRepository;
import com.example.zoom.entity.AuthUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final AuthUserRepository authUserRepository;

    public UserServiceImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public AuthUser findUserByEmailAddress(final String email) {
        return authUserRepository.findByEmailAndIsDeletedFalseAndIsDisabledFalse(email);
    }

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        AuthUser user = findUserByEmailAddress(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<String> role = new HashSet<>();
        role.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), role.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
    }

}
