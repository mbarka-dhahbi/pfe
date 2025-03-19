package com.example.Pfe.service;

import com.example.Pfe.entites.Publication;

import java.util.List;

public interface PublicationService {

    Publication createPublication(Publication publication);
    //   Publication updatePublication(Publication Publication);
    Publication updatePublication(Long id, Publication publication);
    void deletePublication(Long id);
    //void deleteById(int id);
    Publication getPublication(Long id);
    List<Publication> getAllPublications();
    void fermerPublication(Long id);
    //String uploadFilesToPublication(List<MultipartFile> files, int publicationId, String fileType);
}
