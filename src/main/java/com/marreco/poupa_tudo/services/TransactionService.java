package com.marreco.poupa_tudo.services;


import com.github.f4b6a3.uuid.UuidCreator;
import com.marreco.poupa_tudo.dtos.AccountResponseDTO;
import com.marreco.poupa_tudo.dtos.TransactionCreateDTO;
import com.marreco.poupa_tudo.dtos.TransactionResponseDTO;
import com.marreco.poupa_tudo.entity.Account;
import com.marreco.poupa_tudo.entity.Transaction;
import com.marreco.poupa_tudo.enums.TransactionType;
import com.marreco.poupa_tudo.repositories.AccountRepository;
import com.marreco.poupa_tudo.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionResponseDTO createTransaction(UUID accountId, TransactionCreateDTO dto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Deve existir uma conta vinculada à transação"));

        UUID newId = UuidCreator.getTimeOrderedEpoch();

        Transaction newTransaction = new Transaction(
                newId,
                dto.amount(),
                dto.type(),
                dto.description(),
                account
        );

        transactionRepository.save(newTransaction);

        account.updateBalance(dto.amount(), dto.type());

        accountRepository.save(account);

        return new TransactionResponseDTO(
                newTransaction.getId(),
                newTransaction.getAmount(),
                newTransaction.getType(),
                newTransaction.getDescription(),
                newTransaction.getCreatedAt()
        );
    }
}
