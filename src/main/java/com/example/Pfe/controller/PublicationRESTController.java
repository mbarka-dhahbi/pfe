package com.example.Pfe.controller;

import com.example.Pfe.entites.Publication;
import com.example.Pfe.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/publication")
@CrossOrigin(origins = "http://localhost:4200")

public class PublicationRESTController {
    @Autowired
    private PublicationService publicationService;

    @PostMapping (consumes ="application/json")
    public ResponseEntity<Publication> createPublication(@RequestBody Publication publication) {
        Publication createdPublication = publicationService.createPublication(publication);
        return ResponseEntity.ok(createdPublication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publication> updatePublication(@PathVariable Long id, @RequestBody Publication publication) {
        Publication updatedPublication = publicationService.updatePublication(id, publication);
        return ResponseEntity.ok(updatedPublication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Publication>> getAllPublications() {
        List<Publication> publications = publicationService.getAllPublications();
        return ResponseEntity.ok(publications);
    }
    @PutMapping("/fermer/{id}")
    public ResponseEntity<String> fermerPublication(@PathVariable Long id) {
        publicationService.fermerPublication(id);
        return ResponseEntity.ok("Publication désactivée avec succès !");
    }
    @GetMapping("/{id}")
    public Publication getPublication(@PathVariable Long id) {return publicationService.getPublication(id);}


}

