package com.game.of.thrones.signuterdecoderinformerreact.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Evgeny Borisov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecodedLetter {
    private String id;
    private String author;
    private String content;
    private String location;

}
