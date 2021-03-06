package net.ddns.eeitdemo.eeit106team01.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import net.ddns.eeitdemo.eeit106team01.websocket.interceptor.UserInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/simple")
				.withSockJS();

	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registry) {
		registry.interceptors(new UserInterceptor());
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
		registry.setMessageSizeLimit(10 * 1024 * 1024);
		registry.setSendBufferSizeLimit(50 * 1024 * 1024);
	}

}
