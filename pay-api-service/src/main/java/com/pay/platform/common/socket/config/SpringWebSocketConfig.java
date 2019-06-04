package com.pay.platform.common.socket.config;

import com.pay.platform.common.socket.handler.AppWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * 配置WebSocket入口
 *
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class SpringWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    private static Logger logger = LoggerFactory.getLogger(SpringWebSocketConfig.class);

    /**
     * 添加处理器：
     *
     * 1、当客户端与/websocket/app/server.do地址建立连接时,会转发到对应的处理器
     * 2、addInterceptors可指定拦截器：实现握手之前和之后的逻辑操作,此处主要存储account,以便后续
     *
     * @param registry
     */
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler() ,"/websocket/app/server.do");
    }

    public TextWebSocketHandler webSocketHandler() {
        return new AppWebSocketHandler();
    }

}