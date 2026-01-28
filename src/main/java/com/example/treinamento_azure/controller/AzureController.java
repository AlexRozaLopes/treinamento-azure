package com.example.treinamento_azure.controller;

import com.example.treinamento_azure.model.Mensagem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AzureController {


    @GetMapping("/hello")
    public ResponseEntity<Mensagem> hello() {
        return ResponseEntity.ok(new Mensagem("Hello Azure"));
    }
}
