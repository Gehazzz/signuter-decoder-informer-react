package com.game.of.thrones.signuterdecoderinformerreact.adjuster;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Evgeny Borisov
 */
@Data
@ConfigurationProperties("datasender.location")
public class AdjustmentProperties {
    private String url;
    private int numberOfThreads=1;
}
