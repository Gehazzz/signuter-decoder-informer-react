package com.game.of.thrones.signuterdecoderinformerreact.router;

import com.game.of.thrones.signuterdecoderinformerreact.decoder.LetterDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class LetterReceiverRouter {
    @Bean
    public RouterFunction<ServerResponse> delay(LetterDecoder decoder){
        return RouterFunctions.route(POST("/analyse/letter").and(contentType(APPLICATION_STREAM_JSON)), decoder::decode);
    }
}
