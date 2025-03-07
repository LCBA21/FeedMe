package com.lcba.service;

import com.lcba.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;




}
