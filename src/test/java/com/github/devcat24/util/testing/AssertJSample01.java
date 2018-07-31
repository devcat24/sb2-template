package com.github.devcat24.util.testing;

import com.github.devcat24.util.stream.StreamArticle;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


// http://joel-costigliola.github.io/assertj/

public class AssertJSample01 {

    @Test
    public void assertJ01(){
        assertThat("Hello, world! Nice to meet you.")  // source data for testing
                .isNotEmpty()
                .contains("Nice")
                .contains("world")
                .doesNotContain("NaN")
                .doesNotContain("Name", "Age")   // multiple string
                .startsWith("Hell")
                .endsWith("u.")
                .isEqualTo("Hello, world! Nice to meet you.")
                .isNotEqualToIgnoringCase("No Matches");


        assertThat(3.14d)
                //.isNegative()
                .isNotNegative()
                .isPositive()
                .isGreaterThan(3)
                .isLessThan(4)
                .isEqualTo(3, offset(1d))
                .isEqualTo(3.1, offset(0.1d))
                .isEqualTo(3.14);

        List<String> nameList = Arrays.asList("Jane", "John", "Dave");
        assertThat(nameList).hasSize(3)
                            .contains("Jane")
                            .doesNotContain("Sims");

        // Print message to assertj test log when test fails
        assertThat(20).as("check %s's age", "Dave").isEqualTo(20);

        // Java 8 exception assertion, standard style ...
        assertThatThrownBy(() -> { throw new Exception("boom!"); }).hasMessage("boom!");
        // ... or BDD style
        Throwable thrown = catchThrowable(() -> { throw new Exception("boom!"); });
        assertThat(thrown).hasMessageContaining("boom");


        List<StreamArticle> articles = new ArrayList<>();
        //noinspection RedundantArrayCreation
        articles.add(StreamArticle.builder().author("Maria Hawking").title("Cloud Architecture").tags(Arrays.asList(new String[]{"cloud", "architecture", "infrastructure"})).build());
        articles.add(StreamArticle.builder().author("John Doe").title("Java Stream API").tags(Arrays.asList("java", "programming", "web")).build());
        articles.add(StreamArticle.builder().author("Jane Smith").title("Java Microservices").tags(Arrays.asList("microservice", "java", "cloud")).build());
        articles.add(StreamArticle.builder().author("Max Newton").title("REST Security").tags(Arrays.asList("microservice", "java", "security")).build());

        // ... or in Java 8
        StreamArticle article01 = StreamArticle.builder().author("John Doe").title("Java Stream API").tags(Arrays.asList("java", "programming", "web")).build();
        StreamArticle article02 = StreamArticle.builder().author("Jane Smith").title("Java Microservices").tags(Arrays.asList("microservice", "java", "cloud")).build();
        //StreamArticle article02 = StreamArticle.builder().author("Jane Smith").title("Java Microservices002").tags(Arrays.asList("microservice", "java", "cloud")).build();
        assertThat(articles).filteredOn(a -> a.getTitle().contains("Java"))
                .containsOnly(article01, article02);

        // combining filtering and extraction
        //noinspection Convert2MethodRef
        assertThat(articles).filteredOn(a -> a.getTitle().contains("Java"))
                .containsOnly(article01, article02)
                .extracting(a -> a.getAuthor())
                .doesNotContain("Max Newton")
                .contains("John Doe", "Jane Smith");

        // works on Java 7
        assertThat(articles).filteredOn("author", "John Doe")
                .containsOnly(article01);
        assertThat(articles)
                .extracting("author")
                .contains("John Doe", "Jane Smith");

        assertThat(articles) //.filteredOn("author", "John Doe")
                .extracting("author", "title")
                //.contains( tuple("John Doe", "Java Stream API") );
                .contains(  tuple("John Doe", "Java Stream API"),
                            tuple("Jane Smith", "Java Microservices"));


    }

}
