package com.argela.chess.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.argela.chess.repository.ChessRepository;
import com.argela.chess.repository.impl.MemoryChessRepository;

import lombok.Getter;

@Configuration
@Getter
public class GameConfig {
    
    @Bean
    public ChessRepository getChessRepository() {
        return new MemoryChessRepository();
    
    }
}
