package com.marreco.poupa_tudo.controllers;


import com.marreco.poupa_tudo.dtos.AccountCreateDTO;
import com.marreco.poupa_tudo.dtos.AccountResponseDTO;
import com.marreco.poupa_tudo.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/users/{userId}/accounts")
    public ResponseEntity<AccountResponseDTO> create(@PathVariable UUID userId, @RequestBody @Valid AccountCreateDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        AccountResponseDTO accountCreated = accountService.createAccount(userId, dto);

        URI uri = uriComponentsBuilder.path("/accounts/{id}").buildAndExpand(accountCreated.id()).toUri();

        return ResponseEntity
                .created(uri)
                .body(accountCreated);
    }

    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> listAccounts(@PathVariable UUID userId) {
        List<AccountResponseDTO> accounts = accountService.listAccounts(userId);

        return ResponseEntity.ok(accounts);
    }

}
