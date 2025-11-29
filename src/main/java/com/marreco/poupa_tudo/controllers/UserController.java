package com.marreco.poupa_tudo.controllers;

import com.marreco.poupa_tudo.dtos.UserCreateDTO;
import com.marreco.poupa_tudo.dtos.UserResponseDTO;
import com.marreco.poupa_tudo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserCreateDTO dto, UriComponentsBuilder uriComponentsBuilder) {

        UserResponseDTO userCreated = userService.createUser(dto);

        URI uri = uriComponentsBuilder.path("users/{id}").buildAndExpand(userCreated.id()).toUri();

        return ResponseEntity
                .created(uri)
                .body(userCreated);
    }
}
