package com.example.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registerAcc = accountService.register(account);
        if (registerAcc != null) {
            return ResponseEntity.status(HttpStatus.OK).body(registerAcc);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        Account loginAcc = accountService.login(account.getUsername(), account.getPassword());
        if (loginAcc != null) {
            return ResponseEntity.status(HttpStatus.OK).body(loginAcc);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createMsg = messageService.createMessage(message);
        if (createMsg != null) {
            return ResponseEntity.status(HttpStatus.OK).body(createMsg);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createMsg);
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") int id) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("messageId") int id) {
        boolean msgExist = messageService.deleteMessageById(id);
        if (msgExist) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageTextById(@RequestBody Message newMsgTxt, @PathVariable("messageId") int id) {
        boolean msgExist = messageService.updateMessageTextById(newMsgTxt, id);
        if (msgExist) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable("accountId") int id) {
        List<Message> messages = messageService.getAllMessagesByUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
