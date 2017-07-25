package com.github.devcat24.util.stream;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      //(exclude = {"evidenceFiles", "uris"})
@ToString //(exclude = {"evidenceFiles", "uris", "staff"})  // prevents infinite loop ! (Gson Deserialiser)
@Builder
public class StreamFoo {
    @Setter @Getter
    String name;

    @Setter @Getter
    List<StreamBar> bars = new ArrayList<>();

    StreamFoo(String name){
        this.name = name;
    }
}
