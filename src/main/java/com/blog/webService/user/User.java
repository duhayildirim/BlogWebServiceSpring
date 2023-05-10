package com.blog.webService.user;

import com.blog.webService.shared.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "cannot be null")
    @Size(min = 4, max = 36, message = "Minimum of 4 characters required")
    @UniqueUsername(message = "Already registered with this username")
    @JsonView(Views.Base.class)
    private String username;

    @NotNull(message = "cannot be null")
    @Email(message = "not a proper e-mail address spelling")
    @UniqueEmail(message = "Already registered with this e-mail")
    @JsonView(Views.Base.class)
    private String email;

    @NotNull(message = "cannot be null")
    @Size(min = 4, message = "Minimum of 4 characters required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Lower case, upper case and numbers should be used.")
    @JsonView(Views.Sensitive.class)
    private String password;

    @JsonView(Views.Base.class)
    private String image;
}
