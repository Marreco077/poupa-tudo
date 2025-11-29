package com.marreco.poupa_tudo.dtos;

import java.util.UUID;

public record UserResponseDTO (
    UUID id,
    String name,
    String lastName,
    String email
) {}
