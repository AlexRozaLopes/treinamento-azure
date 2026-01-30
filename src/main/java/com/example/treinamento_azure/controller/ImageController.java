package com.example.treinamento_azure.controller;

import com.example.treinamento_azure.model.Link;
import com.example.treinamento_azure.model.Mensagem;
import com.example.treinamento_azure.service.ImageStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageStorageService imageStorageService;

    public ImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file)
            throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new Mensagem("Arquivo vazio"));
        }

        String url = imageStorageService.upload(file);
        return ResponseEntity.ok(new Link(UUID.randomUUID().toString(), url));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(imageStorageService.list());
    }
}

