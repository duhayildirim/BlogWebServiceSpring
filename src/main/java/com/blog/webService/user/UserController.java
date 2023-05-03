package com.blog.webService.user;

import com.blog.webService.error.ApiError;
import com.blog.webService.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        ApiError error = new ApiError(400, "Validation error", "/api/1.0/users");
        Map<String, String> validationErrors = new HashMap<>();
        String username = user.getUsername();
        String email = user.getEmail();

        if (username == null || username.isEmpty()) {
            validationErrors.put("username", "Username cannot be null");
        }
        if (email == null || email.isEmpty()) {
            validationErrors.put("email", "Email cannot be null");
        }

        if (validationErrors.size() > 0) {
            error.setValidationErrors(validationErrors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        userService.save(user);

        return ResponseEntity.ok(new GenericResponse("User created."));
    }
}
