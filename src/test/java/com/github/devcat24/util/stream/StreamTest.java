package com.github.devcat24.util.stream;


import com.github.devcat24.util.collection.ValueComparator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import static org.assertj.core.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class StreamTest {


    @Test
    public void streamSample01Test() throws Exception {
        StreamSample01 sample01 = new StreamSample01();
        sample01.streamSample01();
    }

    @Test
    public void streamSample02Test() throws Exception {
        StreamSample01 sample02 = new StreamSample01();
        sample02.streamSample02();
    }

    @Test
    public void streamSample02aTest() throws Exception {
        StreamSample02 streamSample02 = new StreamSample02();
        streamSample02.steamSample02a();
    }

    @Test
    public void optional01Test() throws Exception {
        StreamArticle article00 = null;
        StreamArticle article01 = StreamArticle.builder().author("John").title("Java").tags(Arrays.asList("IT", "PROGRAMMING")).build();
        StreamArticle article02 = StreamArticle.builder().author("Jane").title("Oracle").tags(Arrays.asList("IT", "DATABASE")).build();

        Optional<StreamArticle> optionalArticle0A = Optional.empty();
        //Optional<StreamArticle> optionalArticle0B = Optional.of(article00); // <-- throws NPE
        Optional<StreamArticle> optionalArticle0C = Optional.ofNullable(article00);  // <-- returns empty Optional instead of NPE
        Optional<StreamArticle> optionalArticle0D = Optional.ofNullable(article01);
        Optional<StreamArticle> optionalArticle0E = Optional.of(article01);

        StreamArticle optionalArticle0F = optionalArticle0E.filter(a -> a.getAuthor().contains("John")).orElse(article02);
        assertThat(optionalArticle0F.getAuthor()).isEqualToIgnoringCase("John");

        assertThat(optionalArticle0C.orElse(article02).getAuthor()).isEqualToIgnoringCase("Jane");
        assertThat(optionalArticle0D.orElse(article02).getAuthor()).isEqualToIgnoringCase("John");


    }


}
