package com.example.Pfe.controller;


import com.example.Pfe.entites.User;
import com.example.Pfe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ok("Hello User");
    }

    @GetMapping("/{id}")
    public User getUtilisateur(@PathVariable Long id) {
        return userService.getUtilisateur(id);
    }
/*
    @GetMapping
    public List<User> getAllUtilisateurs() {
        return userService.getAllUtilisateurs();
    }*/

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
    @PutMapping("/update")
    public User updateUtilisateur(@RequestBody User user) {
        return userService.updateUtilisateur(user);
    }

}

