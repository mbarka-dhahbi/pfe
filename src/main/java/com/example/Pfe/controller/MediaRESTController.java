package com.example.Pfe.controller;

import com.example.Pfe.entites.Media;
import com.example.Pfe.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media")
public class MediaRESTController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/save")
    public ResponseEntity<Media> saveMedia(@RequestBody Media media) {
        System.out.println("Requête POST reçue pour sauvegarder un média : " + media);
        Media savedMedia = mediaService.saveMedia(media);
        System.out.println("Média sauvegardé : " + savedMedia);
        return ResponseEntity.ok(savedMedia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> getMedia(@PathVariable Long id) {
        Media media = mediaService.getMedia(id);
        return ResponseEntity.ok(media);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }
}