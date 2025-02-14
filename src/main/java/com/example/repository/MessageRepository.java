package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;
import com.example.entity.Message;

public interface MessageRepository extends JpaRepository <Message, Integer> {
    Message findByPostedBy(int postedBy);
    Message findByMessageId(int messageId);
    List<Message> findAllByPostedBy(int postedBy);
}
