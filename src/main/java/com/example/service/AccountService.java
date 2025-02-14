package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) {
        Account checkDupUsername = accountRepository.findByUsername(account.getUsername());
        if (checkDupUsername != null) {
            throw new DuplicateUsernameException(account.getUsername() + " is already a username in the database.");
        }

        if (!(account.getUsername().isEmpty()) && account.getPassword().length() >= 4) {
            return accountRepository.save(account);            
        }

        return null;
    }

    public Account login(String username, String password) throws AuthenticationException {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Check username and password credentials as they are invalid");
    }
}
