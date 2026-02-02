package com.example.treinamento_azure.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.example.treinamento_azure.controller.response.ImageResponse;
import com.example.treinamento_azure.model.Image;
import com.example.treinamento_azure.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ImageService {

    private final BlobServiceClient blobServiceClient;
    private final String containerName = "imagens";
    private final ImageRepository imageRepository;

    public ImageService(BlobServiceClient blobServiceClient, ImageRepository imageRepository) {
        this.blobServiceClient = blobServiceClient;
        this.imageRepository = imageRepository;
    }

    public ImageResponse upload(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String blobName = file.getOriginalFilename();

        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(Objects.requireNonNull(blobName));

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        Image img = new Image(uuid.toString(), blobName, blobClient.getBlobUrl(), LocalDateTime.now());
        Image save = imageRepository.save(img);
        return new ImageResponse(save.getId(), save.getName(), save.getUrl());
    }

    public List<ImageResponse> list() {
        Iterable<Image> images = imageRepository.findAll();

        Stream<Image> stream =
                StreamSupport.stream(images.spliterator(), false);

        return stream.map(image -> new ImageResponse(image.getId(), image.getName(), image.getUrl())).toList();
    }

    public Image create(Image image) {
        return imageRepository.save(image);
    }
}

