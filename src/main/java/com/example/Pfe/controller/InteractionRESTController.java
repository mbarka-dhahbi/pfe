package com.example.Pfe.controller;

import com.example.Pfe.entites.Interaction;
import com.example.Pfe.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interaction")
public class InteractionRESTController {

    @Autowired
    private InteractionService interactionService;

    @PostMapping("saveInteraction")
    public Interaction saveInteraction(@RequestBody Interaction interaction){return interactionService.saveInteraction(interaction);}

    @GetMapping("/{id}")
    public Interaction getInteraction(@PathVariable Long id) {return interactionService.getInteraction(id);}

    @GetMapping
    public List<Interaction> getAllInteraction(){return interactionService.getAllInteraction();}

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){interactionService.deleteById(id);}
}
