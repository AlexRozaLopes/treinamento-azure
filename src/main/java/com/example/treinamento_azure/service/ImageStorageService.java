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

    public Link upload(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String blobName = uuid + "-" + file.getOriginalFilename();

        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return new Link(uuid.toString(),blobName,blobClient.getBlobUrl());
    }

    public List<Link> list() {
        var containerClient = blobServiceClient
                .getBlobContainerClient(containerName);

        return containerClient
                .listBlobs()
                .stream()
                .map(b -> {
                    String blobName = b.getName();

                    String url = containerClient
                            .getBlobClient(blobName)
                            .getBlobUrl();

                    return new Link(
                            UUID.randomUUID().toString(),
                            blobName,
                            url
                    );
                })
                .collect(Collectors.toList());
    }
}

