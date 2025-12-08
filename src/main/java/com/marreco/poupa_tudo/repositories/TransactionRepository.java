package com.marreco.poupa_tudo.repositories;

import com.marreco.poupa_tudo.entity.Account;
import com.marreco.poupa_tudo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // <--- Faltou importar

import java.util.List;
import java.util.UUID;

@Repository // <--- Faltou anotar
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByAccountOrderByCreatedAtDesc(Account account);
}