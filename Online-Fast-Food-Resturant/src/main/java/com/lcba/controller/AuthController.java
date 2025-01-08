package com.lcba.controller;


import com.lcba.Appconfig.JwtTokenService;
import com.lcba.Request.LoginRequest;
import com.lcba.model.Cart;
import com.lcba.model.USER_ROLE;
import com.lcba.model.User;
import com.lcba.repository.CartRepository;
import com.lcba.repository.UserRepository;
import com.lcba.response.AuthResponse;
import com.lcba.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private CartRepository  cartRepository;



    @PostMapping(value = "/signUp", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponse> SignUp(@RequestBody User user) throws Exception {
        User isEmailExisting = userRepository.findByEmail(user.getEmail());
        if (isEmailExisting != null) {
            throw new Exception("Email Already Exists");
        }

        // Create and save the user
        User createUser = new User();
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());
        createUser.setRole(user.getRole());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(createUser);

        // Create and save the cart
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        // Authenticate the user
        Authentication authentication = authenticate(user.getEmail(), user.getPassword());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtTokenService.generateToken(userDetails);

        // Create the AuthResponse
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registration Successful");
        authResponse.setRole(savedUser.getRole());

        // Return the response
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    // Login Endpoint
    @PostMapping(value = "/signIn", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponse> SignIn(@RequestBody LoginRequest request) {
        String username = request.getEmail();
        String password = request.getPassword();

        try {
            // Authenticate the user with provided credentials
            Authentication authentication = authenticate(username, password);

            // Retrieve user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate JWT Token
            String jwt = jwtTokenService.generateToken(userDetails);

            // Prepare AuthResponse
            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwt);
            authResponse.setMessage("Login Successful");
            authResponse.setRole(USER_ROLE.valueOf(userDetails.getAuthorities().iterator().next().getAuthority()));

            return new ResponseEntity<>(authResponse, HttpStatus.OK);

        } catch (Exception e) {
            // Handle authentication failure
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Login Failed: " + e.getMessage());
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    // Helper method for authentication
    private Authentication authenticate(String username, String password) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return authentication;
    }

}
