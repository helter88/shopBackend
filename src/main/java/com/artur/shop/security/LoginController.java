package com.artur.shop.security;

import com.artur.shop.security.exception.RegisterException;
import com.artur.shop.security.model.User;
import com.artur.shop.security.model.UserRole;
import com.artur.shop.security.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private long expirationTime;
    private String secret;

    public LoginController(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           @Value("${jwt.expirationTime}") long expirationTime,
                           @Value("${jwt.secret}") String secret) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials loginCredentials){

        return authenticate(loginCredentials.getUsername(), loginCredentials.getPassword());
    }

    private Token authenticate(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails principal = (UserDetails) authenticate.getPrincipal();

        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));

        return new Token (token, principal.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .filter(s -> UserRole.ROLE_ADMIN.name().equals(s))
                .map(s -> true)
                .findFirst()
                .orElse(false));
    }

    @PostMapping("/register")
    public Token register(@RequestBody @Valid RegisterCredentials registerCredentials){
       if(!registerCredentials.getPassword().equals(registerCredentials.getRepeatedPassword())){
           throw new RegisterException("Passwords are not identical");
       }
       if(userRepository.existsByUsername(registerCredentials.getUsername())){
           throw new RegisterException("The user already exists");
       }
       userRepository.save(User.builder()
                       .username(registerCredentials.getUsername())
                       .password("{bcrypt}" + new BCryptPasswordEncoder().encode(registerCredentials.password))
                       .enabled(true)
                       .authorities(List.of(UserRole.ROLE_CUSTOMER))
               .build());
        return authenticate(registerCredentials.getUsername(), registerCredentials.getPassword());
    }

    @Getter
    private static class LoginCredentials{
        private String username;
        private String password;
    }

    @Getter
    private static class RegisterCredentials{
        @Email
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String repeatedPassword;
    }

    @Getter
    @AllArgsConstructor
    private static class Token {
        private String token;
        private boolean adminAccess;
    }
}
