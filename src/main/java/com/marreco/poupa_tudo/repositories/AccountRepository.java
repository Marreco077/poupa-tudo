package com.marreco.poupa_tudo.repositories;


import com.marreco.poupa_tudo.entity.Account;
import com.marreco.poupa_tudo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByUser(User user);

    boolean existsByNameAndUser(String name, User user);
}
