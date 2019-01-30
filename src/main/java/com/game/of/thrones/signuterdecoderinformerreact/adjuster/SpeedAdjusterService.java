package com.game.of.thrones.signuterdecoderinformerreact.adjuster;

import com.game.of.thrones.signuterdecoderinformerreact.config.LocationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Evgeny Borisov
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SpeedAdjusterService {
    private final WebClient webClient;
    private final LocationProperties locationProperties;
    private AtomicLong lastDelay = new AtomicLong(Long.MIN_VALUE);
    private AtomicLong delta = new AtomicLong(10);

    public void changeDelta(long newDelta) {
        delta.set(newDelta);
    }


    public void newDelay(long delay) {
        if (lastDelay.get() - delay > delta.get()) {
            lastDelay.set(delay);
            requestForChangeSpeed(delay);
        }
    }

    private void requestForChangeSpeed(long delay) {
            webClient.get().uri(locationProperties.getDelay() + "/" + delay / locationProperties.getNumberOfThreads())
                    .retrieve().onStatus(HttpStatus::is2xxSuccessful, response -> {
                        log.info("delay was requested for " + delay);
                        return Mono.empty();
                    })
                    .onStatus(HttpStatus::isError, response -> {
                        log.error("no sender url found");
                        return Mono.empty();
                    }).bodyToMono(Void.class).subscribe();
    }
}
