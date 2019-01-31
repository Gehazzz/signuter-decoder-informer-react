package com.game.of.thrones.signuterdecoderinformerreact.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Evgeny Borisov
 */
@Data
@ConfigurationProperties("datasender.location")
public class LocationProperties {
    private String delay;
    private String status;
    private String guard;
    private int numberOfThreads=1;
}
