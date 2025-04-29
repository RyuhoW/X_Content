package com.xcontent.repository;

import com.xcontent.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findById(Long id);
    List<Account> findByUserId(Long userId);
    Optional<Account> findByUsername(String username);
    List<Account> findAll();
    void delete(Long id);
} 