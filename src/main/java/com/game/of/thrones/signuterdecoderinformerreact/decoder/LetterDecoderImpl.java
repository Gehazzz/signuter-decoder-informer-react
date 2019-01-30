package com.game.of.thrones.signuterdecoderinformerreact.decoder;

import com.game.of.thrones.signuterdecoderinformerreact.adjuster.SpeedAdjusterService;
import com.game.of.thrones.signuterdecoderinformerreact.model.DecodedLetter;
import com.game.of.thrones.signuterdecoderinformerreact.model.Letter;
import com.game.of.thrones.signuterdecoderinformerreact.model.Notification;
import com.game.of.thrones.signuterdecoderinformerreact.notifier.LetterDecodingStatusNotifier;
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
import java.time.LocalDateTime;

@Slf4j
@Service
public class LetterDecoderImpl implements LetterDecoder {
    @Autowired
    private SpeedAdjusterService speedAdjuster;
    @Autowired
    private LetterDecodingStatusNotifier notifier;

    @Setter
    private long delay=10000;

    @Override
    public Mono<ServerResponse> decode(ServerRequest serverRequest) {
        Mono<Void> result = serverRequest.bodyToFlux(Letter.class)
                .doOnNext(this::notifyLetterWillBeDecoded)
                .handle(this::handleLetter)
                .doOnNext(this::notifyLetterDecoded)
                .parallel().runOn(Schedulers.parallel()).sequential()
                .then();
        return ServerResponse.ok().build(result);
    }

    private void handleLetter(Letter letter, SynchronousSink<Letter> sink){
        long start = System.currentTimeMillis();
        delay();
        String author = getAuthor(letter);
        DecodedLetter decodedLetter = DecodedLetter.builder().author(author).location(letter.getLocation()).content(letter.getContent()).build();
        long end = System.currentTimeMillis();
        speedAdjuster.newDelay(end-start);
        sink.next(letter);
        log.info(String.valueOf(Thread.currentThread()));
        log.info(decodedLetter + " was sent to police");
    }

    private void notifyLetterWillBeDecoded(Letter letter) {
        String message = "We started letter - " + letter.getId() + "decoding at: " + LocalDateTime.now() + " it will take some time";
        notifier.sendNotification(Notification.builder().letterId(letter.getId()).message(message).build());
    }

    private void notifyLetterDecoded(Letter letter) {
        String message = ("Letter - " + letter.getId() + "decoded at "  + LocalDateTime.now());
        notifier.sendNotification(Notification.builder().letterId(letter.getId()).message(message).build());
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
