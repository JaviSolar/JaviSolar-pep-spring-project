package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        Message msginDB = messageRepository.findByPostedBy(message.getPostedBy());
        if (msginDB != null) {
            if (!(message.getMessageText().isEmpty()) && message.getMessageText().length() <= 255) {
                return messageRepository.save(message);
            }
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findByMessageId(id);
    }

    public boolean deleteMessageById(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateMessageTextById(Message newMsg, int id) {
        Message message = messageRepository.findByMessageId(id);

        if (message != null && !newMsg.getMessageText().isEmpty() && newMsg.getMessageText().length() <= 255) {
            message.setMessageText(newMsg.getMessageText());
            messageRepository.save(message);
            return true;
        } else {
            return false;
        }
    }

    public List<Message> getAllMessagesByUser(int accountId) {
        Account account = accountRepository.findByAccountId(accountId);

        if (account != null) {
            return messageRepository.findAllByPostedBy(accountId);
        } else {
            return null;
        }
    }
    
}
