package com.blog.webService.user;

import com.blog.webService.error.ApiError;
import com.blog.webService.shared.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String username = user.getUsername();
        if (username == null || username.isEmpty()) {
            ApiError error = new ApiError(400, "Validation error", "/api/1.0/users");
            
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("username", "Username cannot be null");
            error.setValidationErrors(validationErrors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        userService.save(user);
        log.info(user.toString());

        return ResponseEntity.ok(new GenericResponse("User created."));
    }
}
