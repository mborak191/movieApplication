package com.borak.movieApp.service;

import com.borak.movieApp.domain.Role;
import com.borak.movieApp.domain.User;
import com.borak.movieApp.repository.RoleRepository;
import com.borak.movieApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {

        try {
            if (userRepository.findByUsername(user.getUsername()) == null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
        } catch (Exception e) {
            log.error("Something gone wrong : " + e);
        }
        log.error("User with {} username already exists in database", user.getUsername());
        return null;
    }

    @Override
    public Role saveRole(Role role) {

        try {
            if (roleRepository.findByName(role.getName()) == null) {
                return roleRepository.save(role);
            }
        } catch (Exception e) {
            log.error("Role {} already exists in database", role.getName());
        }
        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        try {
            if (!Objects.isNull(user) && !Objects.isNull(role) && !user.getRoles().contains(role)) {
                log.info("Adding role {} to the user {}", roleName, username);
                user.getRoles().add(role);
            }
        } catch (Exception e) {
            log.error("Try again" + e);
        }
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }
}
