package com.github.devcat24.util.stream;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      //(exclude = {"evidenceFiles", "uris"})
@ToString //(exclude = {"evidenceFiles", "uris", "staff"})  // prevents infinite loop ! (Gson Deserialiser)
@Builder
public class StreamContact {
    @Setter @Getter
    String name;

    @Setter @Getter
    String state;

    @Setter @Getter
    String city;

    @Setter @Getter
    String email;

    @Setter @Getter
    int age;


    public boolean equalToState(String state){
        return StringUtils.equalsIgnoreCase(this.state, state);
    }
}
