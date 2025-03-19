package com.example.Pfe.controller;

import com.example.Pfe.entites.Commentaire;
import com.example.Pfe.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("commentaire")
public class CommentaireRESTController {
    @Autowired
    private CommentaireService commentaireService;

    @PostMapping("saveCommentaire")
    public Commentaire saveCommentaire(@RequestBody Commentaire commentaire){return commentaireService.saveCommentaire(commentaire);}

    @GetMapping("/{id}")
    public Commentaire getCommentaire(@PathVariable Long id) {return commentaireService.getCommentaire(id);}

    @GetMapping
    public List<Commentaire> getAllCommentaire(){return commentaireService.getAllCommentaire();}

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){commentaireService.deleteById(id);}

}
