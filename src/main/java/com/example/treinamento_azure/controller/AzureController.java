package com.example.treinamento_azure.controller;

import com.azure.security.keyvault.secrets.SecretClient;
import com.example.treinamento_azure.model.Mensagem;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AzureController {

    private final SecretClient secretClient;

    @GetMapping("/hello")
    public ResponseEntity<Mensagem> hello() {
        return ResponseEntity.ok(new Mensagem("Hello Azure"));
    }

    @GetMapping("/key")
    public ResponseEntity<Mensagem> key() {
        String secret = secretClient.getSecret("minha-primeira-key").getValue();
        return ResponseEntity.ok(new Mensagem("Minha secret: " + secret));
    }
}
