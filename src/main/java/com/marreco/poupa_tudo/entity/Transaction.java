package com.marreco.poupa_tudo.entity;

import com.marreco.poupa_tudo.enums.TransactionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Column(nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    protected Transaction() {}

    public Transaction(UUID id, BigDecimal amount, TransactionType type,String description, Account account) {
        this.id = id;
        setAmount(amount);
        setType(type);
        setDescription(description);
        setAccount(account);

        this.createdAt = LocalDateTime.now();
    }

    public Transaction(UUID id, BigDecimal amount, TransactionType type, Account account) {
        this(id, amount, type, null, account);
    }


    public void setAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo");
        }

        this.amount = amount;
    }

    public void setDescription(String description) {
        if (description != null &&  description.length() > 255) {
            throw new IllegalArgumentException("Descrição muito longa (máx 255)");
        }

        this.description = description;
    }

    public void setType(TransactionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Tipo da transação não pode ser nulo");
        }

        this.type = type;
    }

    public void setAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("A transação precisa de uma conta vinculada");
        }

        this.account = account;
    }


    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getDescription() { return description; }
    public Account getAccount() { return account; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
