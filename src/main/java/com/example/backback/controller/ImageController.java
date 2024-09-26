package com.example.backback.controller;

import com.example.backback.domain.entity.Image;
import com.example.backback.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(value = "*",maxAge = 3600)
@RequestMapping("/profile")
public class ImageController {
    @Autowired
    private IImageService imageService;

    @GetMapping("/image")
    public ResponseEntity<?> pageImage(@PageableDefault(sort = "url", direction = Sort.Direction.ASC) Pageable pageable){
        Page<Image> imagePage = imageService.findAll(pageable);
        if(imagePage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imagePage, HttpStatus.OK);
    }

    @DeleteMapping("/deleteImage/{imgId}")
    private ResponseEntity<?> deleteImage(@PathVariable Long imgId){
        Optional<Image> image = imageService.findById(imgId);
        imageService.delete(image.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addImage")
    private ResponseEntity<?> addImage(@RequestBody Image image){
        imageService.save(image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateImage/{id}")
    private ResponseEntity<?> updateImage(@RequestBody Image image, @PathVariable Long id){
        Optional<Image> image1 = imageService.findById(id);
        imageService.save(image1.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
