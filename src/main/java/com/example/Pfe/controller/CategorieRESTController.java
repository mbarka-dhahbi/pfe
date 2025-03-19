package com.example.Pfe.controller;

import com.example.Pfe.entites.Categorie;
import com.example.Pfe.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorie")
@CrossOrigin(origins = "http://localhost:4200")
public class CategorieRESTController {

    @Autowired
    private CategorieService categorieService;

    @PostMapping("saveCategorie")
    public Categorie saveCategorie(@RequestBody Categorie categorie){return categorieService.saveCategorie(categorie);}

    @GetMapping("/{id}")
    public Categorie getCategirie(@PathVariable Long id) {return categorieService.getCategorie(id);}

    @GetMapping
    public List<Categorie> getAllCategorie(){return categorieService.getAllCategorie();}

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){categorieService.deleteById(id);}

}
