package com.github.devcat24.util.stream;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class Article {
    private String title;
    private String author;
    private List<String> tags;
}
