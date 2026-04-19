package com.osc.backend.controller;

import com.osc.backend.dto.UserRequest;
import com.osc.backend.dto.UserResponse;
import com.osc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest request, HttpServletRequest httpRequest) {
        // Enforce MINISTRY_ADMIN role
        if (!httpRequest.isUserInRole("MINISTRY_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Only MINISTRY_ADMIN can create users");
        }

        try {
            UserResponse response = userService.createUser(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    // Simple record class for JSON error response
    record ErrorResponse(String error) {}
}
