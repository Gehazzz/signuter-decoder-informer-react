package com.game.of.thrones.signuterdecoderinformerreact.decoder;

import com.game.of.thrones.signuterdecoderinformerreact.adjuster.SpeedAdjusterService;
import com.game.of.thrones.signuterdecoderinformerreact.model.DecodedLetter;
import com.game.of.thrones.signuterdecoderinformerreact.model.Letter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Field;

@Slf4j
@Service
public class LetterDecoderImpl implements LetterDecoder {
    @Autowired
    private SpeedAdjusterService speedAdjuster;

    @Setter
    private long delay=1;

    @Override
    public Mono<ServerResponse> decode(ServerRequest serverRequest) {
        Mono<Void> result = serverRequest.bodyToFlux(Letter.class)
                .parallel().runOn(Schedulers.parallel()).sequential()
                .handle(this::handleLetter)
                .then();
        return ServerResponse.ok().build(result);
    }


    private void handleLetter(Letter letter, SynchronousSink sink){
        long start = System.currentTimeMillis();
        delay();
        String author = getAuthor(letter);
        DecodedLetter decodedLetter = DecodedLetter.builder().author(author).location(letter.getLocation()).content(letter.getContent()).build();
        long end = System.currentTimeMillis();
        speedAdjuster.newDelay(end-start);
        log.info(String.valueOf(Thread.currentThread()));
        log.info(decodedLetter + " was sent to police");
    }

    @SneakyThrows
    private void delay(){
            Thread.sleep(this.delay);
    }

    @SneakyThrows
    private String getAuthor(Letter letter){
        Field field = Letter.class.getDeclaredField("signature");
        field.setAccessible(true);
        return (String) field.get(letter);
    }
}
