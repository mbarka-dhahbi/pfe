package com.example.Pfe.controller;


import com.example.Pfe.entites.*;
import com.example.Pfe.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    private final PublicationService publicationService;
    private final CommentaireService commentaireService;
    private  final InteractionService interactionService;
    private final MediaService mediaService;



    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ok("Hello User");
    }

   /* @GetMapping("/{id}")
    public User getUtilisateur(@PathVariable Long id) {
        return userService.getUtilisateur(id);
    }
/*
    @GetMapping
    public List<User> getAllUtilisateurs() {
        return userService.getAllUtilisateurs();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
    @PutMapping("/update")
    public User updateUtilisateur(@RequestBody User user) {
        return userService.updateUtilisateur(user);
    }

    @PostMapping (consumes ="application/json")
    public ResponseEntity<Publication> createPublication(@RequestBody Publication publication) {
        Publication createdPublication = publicationService.createPublication(publication);
        return ResponseEntity.ok(createdPublication);
    }

    @PostMapping("/upload")
    public ResponseEntity<Media> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("publicationId") Long publicationId) throws IOException {
        System.out.println("Requête POST reçue pour uploader un média : " + file.getOriginalFilename());
        Media savedMedia = mediaService.saveMedia(file, publicationId);
        System.out.println("Média sauvegardé : " + savedMedia);
        return ResponseEntity.ok(savedMedia);
    }*/

    // Gestion des publications
    @PostMapping ("/publications")
    public ResponseEntity<Publication> createPublication(@RequestBody Publication publication) {
        Publication createdPublication = publicationService.createPublication(publication);
        return ResponseEntity.ok(createdPublication);
    }

    @PutMapping("/publications/{id}")
    public ResponseEntity<Publication> updatePublication(@PathVariable Long id, @RequestBody Publication publication) {
        Publication updatedPublication = publicationService.updatePublication(id, publication);
        return ResponseEntity.ok(updatedPublication);
    }

    @DeleteMapping("/publications/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping ("/publications")
    public ResponseEntity<List<Publication>> getAllPublications() {
        List<Publication> publications = publicationService.getAllPublications();
        return ResponseEntity.ok(publications);
    }



    // Gestion des médias

    @PostMapping("/media/upload")
    public ResponseEntity<Media> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("publicationId") Long publicationId) throws IOException {
        System.out.println("Requête POST reçue pour uploader un média : " + file.getOriginalFilename());
        Media savedMedia = mediaService.saveMedia(file, publicationId);
        System.out.println("Média sauvegardé : " + savedMedia);
        return ResponseEntity.ok(savedMedia);
    }

    @GetMapping("/media/{id}")
    public ResponseEntity<Media> getMedia(@PathVariable Long id) {
        Media media = mediaService.getMedia(id);
        return ResponseEntity.ok(media);
    }

    @DeleteMapping("/media/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }

    // Gestion des commentaires

    @PostMapping("/commentaires")
    public Commentaire saveCommentaire(@RequestBody Commentaire commentaire){return commentaireService.saveCommentaire(commentaire);}

    @GetMapping("/commentaires/{id}")
    public Commentaire getCommentaire(@PathVariable Long id) {return commentaireService.getCommentaire(id);}

    @GetMapping ("/commentaires")
    public List<Commentaire> getAllCommentaire(){return commentaireService.getAllCommentaire();}
    @DeleteMapping("/commentaires/{id}")
    public ResponseEntity<Void> deletecommentaires(@PathVariable Long id) {
        commentaireService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Gestion des interactions

    @PostMapping("/interactions")
    public Interaction saveInteraction(@RequestBody Interaction interaction){return interactionService.saveInteraction(interaction);}

    @GetMapping("/interactions/{id}")
    public Interaction getInteraction(@PathVariable Long id) {return interactionService.getInteraction(id);}

    @GetMapping("/interactions")
    public List<Interaction> getAllInteraction(){return interactionService.getAllInteraction();}

    @DeleteMapping("/interactions/{id}")
    public ResponseEntity<Void> deleteinteractions(@PathVariable Long id) {
        interactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

