package com.websocket.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage") //sendMessage 메서드를 부르기 위한 URL
    @SendTo("/topic/public")
    public ChatMessage sendMessage( // 클라이언트로부터 메시지를 받아들이고(MessageMapping), 해당 메시지를 모든 클라이언트에게 브로드캐스트하기 위한 메서드(SendTo)
           @Payload ChatMessage chatMessage
    ){
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser( // 새로운 사용자가 채팅에 참여할 때 호출되는 메서드
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in websocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender()); // 클라이언트로부터 받은 사용자 정보를 WebSocket 세션에 추가하여 모든 클라이언트에게 브로드캐스트하기 위한 메서드(SendTo)
        return chatMessage;
    }
}
