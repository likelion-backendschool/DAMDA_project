package com.ll.exam.damda.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue","/topic"); //spring 내장 브로커 사용
        //prefix가 붙은 메시지를 발행 시 브로커가 처리
        //convention으로 queue는 1:1 통신, topic은 1:N 통신일 때 사용
        registry.setApplicationDestinationPrefixes("/app");
        //해당 경로를 처리하는 handler로 라우팅하는 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //websocket handshake를 위한 공통 주소, 일반 websocket처럼 handler별로 설정해줄 필요 없음
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
