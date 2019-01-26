package com.game.of.thrones.signuterdecoderinformerreact.router;

import com.game.of.thrones.signuterdecoderinformerreact.decoder.LetterDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class LetterReceiverRouter {
    @Bean
    public RouterFunction<ServerResponse> delay(LetterDecoder decoder){
        return RouterFunctions.route(POST("/analyse/letter").and(contentType(APPLICATION_STREAM_JSON)), decoder::decode);
    }
}
