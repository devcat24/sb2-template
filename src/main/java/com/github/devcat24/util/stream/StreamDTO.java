package com.github.devcat24.util.stream;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      //(exclude = {"evidenceFiles", "uris"})
@ToString //(exclude = {"evidenceFiles", "uris", "staff"})  // prevents infinite loop ! (Gson Deserialiser)
@Builder
public class StreamDTO {
    @Setter @Getter
    String name;

    @Setter @Getter
    int age;
}
