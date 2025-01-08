package com.lcba.service;

import com.lcba.Appconfig.JwtTokenService;
import com.lcba.model.User;
import com.lcba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtTokenService.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email);
    }
}
