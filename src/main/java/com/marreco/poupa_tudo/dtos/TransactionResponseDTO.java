package com.marreco.poupa_tudo.dtos;

import com.marreco.poupa_tudo.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID id,
        BigDecimal amount,
        TransactionType type,
        String description,
        LocalDateTime createdAt
) {}
