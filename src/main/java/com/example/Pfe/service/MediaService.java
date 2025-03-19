package com.example.Pfe.service;

import com.example.Pfe.entites.Media;

public interface MediaService {
    Media saveMedia(Media media);
    Media getMedia(Long id);
    void deleteMedia(Long id);
}