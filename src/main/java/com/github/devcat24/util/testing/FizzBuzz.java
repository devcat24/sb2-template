package com.github.devcat24.util.testing;

public class FizzBuzz {
    public String play(int num) {
        if (num == 0) throw new IllegalArgumentException("number should not be 0");
        if (isMultipleOf(num, 3) && isMultipleOf(num, 5)) return "FizzBuzz";
        if (isMultipleOf(num, 3)) return "Fizz";
        if (isMultipleOf(num, 5)) return "Buzz";


        return String.valueOf(num);
    }

    private boolean isMultipleOf(int num, int i) {
        return (num % i == 0);
    }
}