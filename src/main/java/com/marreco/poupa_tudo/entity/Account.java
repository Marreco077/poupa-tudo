package com.marreco.poupa_tudo.entity;

import com.marreco.poupa_tudo.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    @Length(max = 100)
    private String name;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    protected Account() {}

    public Account(UUID id, String name,User user) {
        this.id = id;
        this.user = user;
        setName(name);

        createdAt = LocalDateTime.now();
        balance = BigDecimal.ZERO;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }

        this.name = name;
    }

    public UUID getId() { return id; }

    public String getName() { return name; }

    public BigDecimal getBalance() { return balance; }

    public User getUser() { return user; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void updateBalance(BigDecimal amount, TransactionType type) {
        if (type == TransactionType.INCOME) {
            this.balance = this.balance.add(amount);
        } else {
            this.balance = this.balance.subtract(amount);
        }
    }
}
