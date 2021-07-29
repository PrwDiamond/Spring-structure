package com.example.demo.business;

import com.example.demo.exception.BaseException;
import com.example.demo.exception.ChatException;
import com.example.demo.model.ChatMessage;
import com.example.demo.model.ChatMessageRequest;
import com.example.demo.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatBusiness {

    private final SimpMessagingTemplate template;

    public ChatBusiness(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void post(ChatMessageRequest request) throws BaseException {
        final String destination = "/topic/chat";

        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

        //Validate Message

        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());

        template.convertAndSend(destination, payload);
    }
}
