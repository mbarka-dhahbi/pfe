package com.example.Pfe.service.impl;

import com.example.Pfe.entites.Publication;
import com.example.Pfe.repository.PublicationRepository;
import com.example.Pfe.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PublicationServiceImpl  implements PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public Publication createPublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    @Override
    public Publication getPublication(Long id) {
        return publicationRepository.findById(id).get();
    }

    @Override
    public Publication updatePublication(Long id, Publication publication) {
        Optional<Publication> existingPublication = publicationRepository.findById(id);
        if (existingPublication.isPresent()) {
            publication.setId(id);
            return publicationRepository.save(publication);
        }
        throw new RuntimeException("Publication not found");
    }


    public void deletePublication(Long id) {
        publicationRepository.deleteById(id);
    }

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }


    public void fermerPublication(Long id) {
        Publication publication = publicationRepository.findById(id).orElse(null);
        if (publication != null) {
            publication.setActive(false);
            publicationRepository.save(publication);
        }
    }
}
