package com.game.of.thrones.signuterdecoderinformerreact.decoder;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


public interface LetterDecoder {
    Mono<ServerResponse> decode(ServerRequest serverRequest);
}
