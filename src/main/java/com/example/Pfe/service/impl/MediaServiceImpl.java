package com.example.Pfe.service.impl;

import com.example.Pfe.entites.Media;
import com.example.Pfe.entites.Publication;
import com.example.Pfe.repository.MediaRepository;
import com.example.Pfe.repository.PublicationRepository;
import com.example.Pfe.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    @Transactional
    public Media saveMedia(MultipartFile file, Long publicationId) throws IOException {
        // Vérifie si le dossier d'upload existe, sinon le crée
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Génère un nom unique pour le fichier
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        // Sauvegarde le fichier sur le disque
        Files.write(filePath, file.getBytes());

        // Détermine le type de média basé sur l'extension
        Media.MediaType type = file.getContentType().startsWith("image") ? Media.MediaType.IMAGE : Media.MediaType.VIDEO;

        // Récupère la Publication existante depuis la base
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + publicationId));

        // Crée l'entité Media
        Media media = new Media();
        media.setUrl(filePath.toString());
        media.setType(type);
        media.setPublication(publication); // Associe la Publication

        return mediaRepository.save(media);
    }

    @Override
    public Media getMedia(Long id) {
        return mediaRepository.findById(id).orElseThrow(() -> new RuntimeException("Media not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteMedia(Long id) {
        Media media = getMedia(id);
        File file = new File(media.getUrl());
        if (file.exists()) {
            file.delete();
        }
        mediaRepository.deleteById(id);
    }
}