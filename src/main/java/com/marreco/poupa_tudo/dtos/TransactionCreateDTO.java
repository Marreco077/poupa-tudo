package com.marreco.poupa_tudo.dtos;

import com.marreco.poupa_tudo.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TransactionCreateDTO(
        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser positivo")
        BigDecimal amount,

        @NotNull(message = "O tipo da transação é obrigatório")
        TransactionType type,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String description
) {
}
