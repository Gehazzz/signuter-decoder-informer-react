package com.game.of.thrones.signuterdecoderinformerreact;

import com.game.of.thrones.signuterdecoderinformerreact.adjuster.AdjustmentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableConfigurationProperties(AdjustmentProperties.class)
@SpringBootApplication
public class SignuterDecoderInformerReactApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignuterDecoderInformerReactApplication.class, args);
    }

}

