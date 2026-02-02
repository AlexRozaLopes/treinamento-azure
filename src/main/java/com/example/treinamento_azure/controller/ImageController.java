package com.example.treinamento_azure.controller;

import com.example.treinamento_azure.model.Image;
import com.example.treinamento_azure.model.Mensagem;
import com.example.treinamento_azure.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file)
            throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new Mensagem("Arquivo vazio"));
        }

        return ResponseEntity.ok(imageService.upload(file));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(imageService.list());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Image image) {
        return ResponseEntity.ok(imageService.create(image));
    }
}

