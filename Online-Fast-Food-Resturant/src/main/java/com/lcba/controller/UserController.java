package com.lcba.controller;


import com.lcba.model.User;
import com.lcba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> findUserByJwtToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Validate Authorization header
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Authorization header is missing or invalid.");
            }

            // Extract token from Bearer string
            String jwt = authorizationHeader.substring(7).trim();

            try {
                // Find user based on the token
                User user = userService.findUserByJwtToken(jwt);
                return ResponseEntity.ok(user);
            } catch (io.jsonwebtoken.io.DecodingException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid token format: " + e.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the request: " + e.getMessage());
        }
    }
}
