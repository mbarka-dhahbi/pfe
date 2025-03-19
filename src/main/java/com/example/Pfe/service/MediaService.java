package com.example.Pfe.service;

import com.example.Pfe.entites.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {
    Media saveMedia(MultipartFile file, Long publicationId) throws IOException;
    Media getMedia(Long id);
    void deleteMedia(Long id);
}