package tr.com.argela.chess.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import tr.com.argela.chess.repository.ChessRepository;
import tr.com.argela.chess.repository.impl.MemoryChessRepository;

@Configuration
@Getter
public class GameConfig {
    
    @Bean
    public ChessRepository getChessRepository() {
        return new MemoryChessRepository();
    
    }
}
