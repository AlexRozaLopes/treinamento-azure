package com.example.treinamento_azure.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.example.treinamento_azure.model.Link;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageStorageService {

    private final BlobServiceClient blobServiceClient;
    private final String containerName = "imagens";

    public ImageStorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    public String upload(MultipartFile file) throws IOException {
        String blobName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }

    public List<Link> list() {
        return blobServiceClient
                .getBlobContainerClient(containerName).listBlobs()
                .stream().map(b -> new Link(UUID.randomUUID().toString(),b.getName())).collect(Collectors.toList());
    }
}

