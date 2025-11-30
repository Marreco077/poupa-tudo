package com.marreco.poupa_tudo.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDTO(
        UUID id,
        String name,
        BigDecimal balance
) {}
