package com.example.Pfe.service.impl;


import com.example.Pfe.dto.JwtAuthentificationResponse;
import com.example.Pfe.dto.RefreshTokenRequest;
import com.example.Pfe.dto.SignInRequest;
import com.example.Pfe.dto.SignUpRequest;
import com.example.Pfe.entites.Role;
import com.example.Pfe.entites.User;
import com.example.Pfe.repository.UserRepository;
import com.example.Pfe.service.AuthenticationService;
import com.example.Pfe.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final JavaMailSender mailSender; // Injecte JavaMailSender

    @Override
    public User signUp(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.COLLABORATOR);
        user.setEmailConfirmed(false); // L'email n'est pas encore confirmé
        user.setConfirmationToken(UUID.randomUUID().toString()); // Génère un token de confirmation

        // Sauvegarde l'utilisateur
        User savedUser = userRepository.save(user);

        // Envoie un email de confirmation
        sendConfirmationEmail(savedUser);
        return userRepository.save(user);
    }

    private void sendConfirmationEmail(User user) {
        String confirmationLink = "http://localhost:8080/api/v1/auth/confirm?token=" + user.getConfirmationToken();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Confirmez votre adresse email");
        message.setText("Merci de vous être inscrit ! Veuillez confirmer votre adresse email en cliquant sur ce lien : " + confirmationLink);
        mailSender.send(message);
    }
    @Override
    public JwtAuthentificationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.isEmailConfirmed() == true) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthentificationResponse response = new JwtAuthentificationResponse();
        response.setToken(jwt);
        response.setRefreshToken(refreshToken);
            return response;
        }else{
            throw new RuntimeException("Email not confirmed");
        }
    }


    public JwtAuthentificationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            JwtAuthentificationResponse response = new JwtAuthentificationResponse();
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            return response;
        }
        return null;
    }

}
