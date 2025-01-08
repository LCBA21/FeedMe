package com.lcba.service;

import com.lcba.model.USER_ROLE;
import com.lcba.model.User;
import com.lcba.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        USER_ROLE role=user.getRole();

        List<GrantedAuthority> authorities =new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));



        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),authorities);


    }
}
