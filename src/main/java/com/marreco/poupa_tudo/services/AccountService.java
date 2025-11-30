package com.marreco.poupa_tudo.services;

import com.github.f4b6a3.uuid.UuidCreator;
import com.marreco.poupa_tudo.dtos.AccountCreateDTO;
import com.marreco.poupa_tudo.dtos.AccountResponseDTO;
import com.marreco.poupa_tudo.entity.Account;
import com.marreco.poupa_tudo.entity.User;
import com.marreco.poupa_tudo.repositories.AccountRepository;
import com.marreco.poupa_tudo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AccountResponseDTO createAccount(UUID userId, AccountCreateDTO dto) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("Uusário não encontrado"));

        if (accountRepository.existsByNameAndUser(dto.name(), user)) {
            throw new IllegalArgumentException("Esse usuário já tem uma carteira com esse nome");
        }

        UUID newId = UuidCreator.getTimeOrderedEpoch();

        Account newAccount = new Account(
                newId,
                dto.name(),
                user
        );

        accountRepository.save(newAccount);

        return new AccountResponseDTO(
                newAccount.getId(),
                newAccount.getName(),
                newAccount.getBalance()
        );
    }

    public List<AccountResponseDTO> listAccounts(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return accountRepository.findByUser(user)
                .stream()
                .map(account -> new AccountResponseDTO(
                        account.getId(),
                        account.getName(),
                        account.getBalance()
                ))
                .toList();
    }
}
