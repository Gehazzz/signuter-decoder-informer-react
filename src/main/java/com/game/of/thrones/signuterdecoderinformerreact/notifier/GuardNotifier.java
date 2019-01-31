package com.game.of.thrones.signuterdecoderinformerreact.notifier;

import com.game.of.thrones.signuterdecoderinformerreact.config.LocationProperties;
import com.game.of.thrones.signuterdecoderinformerreact.model.Notification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service("Guard")
@AllArgsConstructor
public class GuardNotifier implements Notifier {
    WebClient webClient;
    private final LocationProperties locationProperties;
    @Override
    public void sendNotification(Notification notification) {
        webClient.post().uri(locationProperties.getGuard())
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(notification)
                .retrieve().onStatus(HttpStatus::is2xxSuccessful, response -> {
                    log.info("Guard notification sent");
                    return Mono.empty();
                })
                .onStatus(HttpStatus::isError, response -> {
                    log.error("no sender url found");
                    return Mono.empty();
                }).bodyToMono(Void.class).subscribe();
    }
}
