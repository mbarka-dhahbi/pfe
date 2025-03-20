package com.example.Pfe.service.impl;


import com.example.Pfe.entites.User;
import com.example.Pfe.repository.UserRepository;
import com.example.Pfe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;




@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
            }
        };
    }

    public String createPasswordResetToken(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user); // Ensure the user is saved with the new token
        return token;
    }

    public boolean validatePasswordResetToken(String token) {
        return userRepository.findByResetToken(token).isPresent();
    }

    public boolean resetPassword(String token, String newPassword) {
        var user = userRepository.findByResetToken(token).orElse(null);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean confirmEmail(String token) {
        var user = userRepository.findByConfirmationToken(token).orElse(null);
        if (user != null) {
            user.setEmailConfirmed(true);
            user.setConfirmationToken(null); // Supprime le token après confirmation
            userRepository.save(user);
            return true;
        }
        return false;
    }

    /*@Override
    public User saveUtilisateur(User u) {

        return userRepository.save(u);

    }*/

    @Override
    public User getUtilisateur(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUtilisateurs() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUtilisateur(User u) {

        return userRepository.save(u);
    }
}
