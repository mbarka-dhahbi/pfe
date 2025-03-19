package com.example.Pfe.service.impl;

import com.example.Pfe.entites.Media;
import com.example.Pfe.repository.MediaRepository;
import com.example.Pfe.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    @Transactional
    public Media saveMedia(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public Media getMedia(Long id) {
        return mediaRepository.findById(id).orElseThrow(() -> new RuntimeException("Media not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
    }
}