package com.github.devcat24.util.string;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class StringManipulator {
    public void stringJoinerEx01 (){
        StringJoiner fruits = new StringJoiner(", ");
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Mango");
        System.out.println("Result:" + fruits.toString());

        StringJoiner pets = new StringJoiner(", ", "[", "]");
        pets.add("Cat");
        pets.add("Dog");
        pets.add("Rabbit");
        System.out.println("Result:" + pets.toString());

        List<String> colors = Arrays.asList("red", "blue", "white");
        String colorString = colors.stream().collect(Collectors.joining("-"));
        System.out.println("Result:" + colorString);

    }

}
