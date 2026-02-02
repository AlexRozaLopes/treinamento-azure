package com.example.treinamento_azure.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Container(containerName = "image")
public class Image {

    @Id
    private String id = UUID.randomUUID().toString();
    @PartitionKey
    private String name;
    private String url;
    private LocalDateTime createdAt;
}
