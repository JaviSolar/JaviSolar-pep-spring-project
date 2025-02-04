package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
    AccountRepository accountRepository;
    private List<Account> accountList = new ArrayList<>();

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        if (!(account.getUsername().isEmpty()) && account.getPassword().length() >= 4) {
            return accountRepository.save(account);
        }
        return null;
    }

    public Account login(String username, String password) throws AuthenticationException {
        for (Account account : accountList) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;
            }
        }
        // Account loginAcc = accountRepository.findByUsernameAndPassword(username, password);
        throw new AuthenticationException("Check username and password credentials as they are invalid");
    }
}
