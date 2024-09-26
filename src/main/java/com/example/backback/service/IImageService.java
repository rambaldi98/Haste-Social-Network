package com.example.backback.service;


import com.example.backback.domain.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IImageService extends IGeneralService<Image> {
    Page<Image> findAll(Pageable pageable);
}
