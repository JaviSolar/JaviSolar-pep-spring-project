package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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

    
}
