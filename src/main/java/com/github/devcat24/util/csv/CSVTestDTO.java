package com.github.devcat24.util.csv;

import lombok.*;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      //(exclude = {"evidenceFiles", "uris"})
@ToString //(exclude = {"evidenceFiles", "uris", "staff"})  // prevents infinite loop ! (Gson Deserialiser)
@Builder
public class CSVTestDTO implements Serializable {

    @Setter @Getter
    Long id;

    @Setter @Getter
    String name;

    @Setter @Getter
    String order;
}