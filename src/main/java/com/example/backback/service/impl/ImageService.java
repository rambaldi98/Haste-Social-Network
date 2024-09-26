package com.example.backback.service.impl;

import com.example.backback.domain.entity.Image;
import com.example.backback.domain.entity.User;
import com.example.backback.repository.ImageRepository;
import com.example.backback.security.userprincal.UserDetailService;
import com.example.backback.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService implements IImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserDetailService userDetailService;

    @Override
    public Page<Image> findAll(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image save(Image image) {
        User user = userDetailService.getCurrentUser();
        user.setImage(image.getImageUrl());
        return imageRepository.save(image);
    }





//    @Override
//    public Image save(Image image) {
//        return imageRepository.save(image);
//    }
//    }
}
