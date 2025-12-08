package com.marreco.poupa_tudo.controllers;

import com.marreco.poupa_tudo.dtos.TransactionCreateDTO;
import com.marreco.poupa_tudo.dtos.TransactionResponseDTO;
import com.marreco.poupa_tudo.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<TransactionResponseDTO> create(@PathVariable UUID accountId, @RequestBody @Valid TransactionCreateDTO dto, UriComponentsBuilder uriBuilder) {
        TransactionResponseDTO transactionCreated = transactionService.createTransaction(accountId, dto);

        URI uri = uriBuilder.path("/transactions/{id}")
                .buildAndExpand(transactionCreated.id())
                .toUri();

        return ResponseEntity.
                created(uri)
                .body(transactionCreated);
    }
}
