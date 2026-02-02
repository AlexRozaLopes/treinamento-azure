package com.example.treinamento_azure.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.example.treinamento_azure.model.Image;

public interface ImageRepository extends CosmosRepository<Image, String> {
}
