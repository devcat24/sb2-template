package com.github.devcat24.util.activemq;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      //(exclude = {"evidenceFiles", "uris"})
@ToString //(exclude = {"evidenceFiles", "uris", "staff"})  // prevents infinite loop ! (Gson Deserialiser)
@Builder
public class AMQMessageExample01 {

    @Setter @Getter
    String to;

    @Setter @Getter
    String body;

}

