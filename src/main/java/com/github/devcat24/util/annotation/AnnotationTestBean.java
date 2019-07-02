package com.github.devcat24.util.annotation;


import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      //(exclude = {"evidenceFiles", "uris"})
@ToString //(exclude = {"evidenceFiles", "uris", "staff"})  // prevents infinite loop ! (Gson Deserialiser)
@Builder
public class AnnotationTestBean {

    @CsvBindByPosition(position = 0)
    @Setter
    @Getter
    Long id;

    @CsvBindByPosition(position = 1)
    @Setter
    @Getter
    String name;

    @CsvBindByPosition(position = 2)
    @Setter
    @Getter
    String order;
}