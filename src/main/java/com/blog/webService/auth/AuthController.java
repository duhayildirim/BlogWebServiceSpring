package com.blog.webService.auth;

import com.blog.webService.error.ApiError;
import com.blog.webService.shared.Views;
import com.blog.webService.user.User;
import com.blog.webService.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.dialect.SpannerSqlAstTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization", required = false) String authorization) {
        if (authorization == null) {
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        System.out.println(authorization);
        String base64encoded = authorization.split("Basic ")[1]; // dxAIX6Fijl9qWE=
        String decoded = new String(Base64.getDecoder().decode(base64encoded)); // example@gmail.com:Example12.
        String[] parts = decoded.split(":"); // ["example@gmail.com", "Example12."]
        String email = parts[0];
        String password = parts[1];

        User inDB = userRepository.findByEmail(email);
        if (inDB == null) {
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        String hashedPassword = inDB.getPassword();
        if(!passwordEncoder.matches(password, hashedPassword)) {
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        return ResponseEntity.ok(inDB);
    }
}
