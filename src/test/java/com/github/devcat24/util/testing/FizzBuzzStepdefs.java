package com.github.devcat24.util.testing;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.Assertions;


public class FizzBuzzStepdefs {
    FizzBuzz fizzBuzz;
    String result;


   /* @Given("^Create a FizzBuzz game to get $")
    public void createAFizzBuzzGamePlay() throws Throwable {
        fizzBuzz = new FizzBuzz();

    }*/
    @Given("^Create a FizzBuzz game play$")
    public void createAFizzBuzzGameToGetFizz() throws Throwable {
       fizzBuzz = new FizzBuzz();
    }

    @When("^I play with number (\\d+)$")
    public void iPlayWithNumber(int num) throws Throwable {
        result = fizzBuzz.play(num);
    }

    /*@Then("^The result is Fizz$")
    public void theResultWasFizz() throws Throwable {
        Assertions.assertEquals(result, "Fizz");
    }*/

    @Then("^The result is \"([^\"]*)\"$")
    public void theResultIs(String resultString) throws Throwable {
        Assertions.assertEquals(result, resultString);
    }


}
