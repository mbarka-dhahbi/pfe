package com.example.Pfe.controller;


import com.example.Pfe.dto.PasswordResetConfirmRequest;
import com.example.Pfe.dto.PasswordResetRequest;
import com.example.Pfe.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final UserServiceImpl userService;

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        String token = userService.createPasswordResetToken(request.getEmail());
        String resetLink = "http://localhost:8080/api/v1/auth/reset-password?token=" + token;
        return ResponseEntity.ok("Password reset link sent to your email.");
    }

    @GetMapping("/reset-password")
    public ResponseEntity<String> showResetPasswordPage(@RequestParam("token") String token) {
        if (userService.validatePasswordResetToken(token)) {
            return ResponseEntity.ok("Token is valid. You can now reset your password.");
        } else {
            return ResponseEntity.badRequest().body("Invalid token.");
        }
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> confirmResetPassword(
            @RequestParam("token") String token,
            @RequestBody PasswordResetConfirmRequest request) { // Use DTO here
        if (userService.resetPassword(token, request.getNewPassword())) {
            return ResponseEntity.ok("Password has been reset successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid token or token has expired.");
        }
    }
}
