package com.github.devcat24.util.stream;


import java.util.*;
import java.util.stream.Collectors;


@SuppressWarnings({"unused", "UnusedAssignment"})
public class StreamSample03 {
    public Object steamSample03a(){
        List<StreamArticle> articles = new ArrayList<>();
        //noinspection RedundantArrayCreation
        articles.add(StreamArticle.builder().author("Maria Hawking").title("Cloud Architecture").tags(Arrays.asList(new String[]{"cloud", "architecture", "infrastructure"})).build());
        articles.add(StreamArticle.builder().author("John Doe").title("Java Stream API").tags(Arrays.asList("java", "programming", "web")).build());
        articles.add(StreamArticle.builder().author("Jane Smith").title("Microservice Design").tags(Arrays.asList("microservice", "java", "cloud")).build());
        articles.add(StreamArticle.builder().author("Max Newton").title("REST Security").tags(Arrays.asList("microservice", "java", "security")).build());


        // type #00
        // --- old style ---
        // for (StreamArticle article : articles) {
        //     System.out.println(article.getAuthor());
        //  }
        //
        // --- new style ---
        articles.forEach(System.out::println);
        //noinspection SimplifyStreamApiCallChains
        articles.stream().forEach(System.out::println);

        // type #01
        // --- old style ---
        // StreamArticle rtnArticle = null;
        // for(StreamArticle article : articles){
        //    if(article.getTags().contains("java")) { return article; }
        // }
        //
        // --- new style ---
        Object rtnObj = articles.stream().filter(article -> article.getTags().contains("java")).findFirst();


        // type #02
        List<StreamArticle> resultList02 = new ArrayList<>();
        // --- old style ---
        // for (StreamArticle article : articles) {
        //    if(article.getTags().contains("java")) { resultList02.add(article); }
        // }
        //
        // --- new style ---
        resultList02 = articles.stream().filter(article -> article.getTags().contains("java")).collect(Collectors.toList());


        // type #03
        Set<String> resultSet03 = new HashSet<>();
        // --- old style ---
        // for (StreamArticle article : articles){
        //     resultSet03.addAll(article.getTags());
        // }
        //
        // --- new style ---
        resultSet03 = articles.stream().flatMap(article -> article.getTags().stream()).collect(Collectors.toSet());


        // type #04
        Map<String, List<StreamArticle>> resultMap04 = new HashMap<>();
        // --- old style ---
        // for (StreamArticle article : articles){
        //    if (resultMap04.containsKey(article.getAuthor())){
        //        resultMap04.get(article.getAuthor()).add(article);
        //   }   else {
        //        ArrayList<StreamArticle> articles04 = new ArrayList<>();
        //        articles04.add(article);
        //        resultMap04.put(article.getAuthor(), articles04);
        //    }
        // }
        //
        // --- new style ---
        resultMap04 = articles.stream().collect(Collectors.groupingBy(StreamArticle::getAuthor));


        return null;

    }
}
