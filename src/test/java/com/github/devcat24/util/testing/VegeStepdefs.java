package com.github.devcat24.util.testing;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"RedundantThrows", "MalformedFormatString"})
public class VegeStepdefs {
    @Before
    public void setUp() {
    }

    @Given("^I have a cucumber step$")
    public void i_have_a_cucumber_step() throws Throwable {
        System.out.println ("Step definition exact match");
    }

    @Given("^I have a (?:tasty|nasty|rusty) cucumber step$")
    public void i_have_a_X_cucumber_step() throws Throwable {
        System.out.println ("Step definition with a non-capturing group");
    }

    @Given("^I have (\\d+) (.*) in my basket$")
    public void i_have_in_my_basket(int number, String veg) throws Throwable {
        System.out.println(String.format("I have {0} {1} in my basket", number, veg));
    }

    @Given("^There (?:is|are) (\\d+) (?:cats?|ox|oxen) fed by (\\d+) (?:persons?|people)$")
    public void animals_fed_by_people(int animals, int persons) throws Throwable {
        System.out.println(String.format("{0} animal(s) fed by {1} person(s)", animals, persons));
    }

    @Given ("^I have the following order$")
    public void i_have_the_following_order (DataTable table) throws Throwable {
        for (Map<String, String> map : table.asMaps(String.class, String.class)) {
            String vegetable = map.get("vegetable");
            String amount = map.get("amount");
            String cost = map.get("cost");
            System.out.println (String.format("Order of {0} {1}s at the cost of {2}", amount, vegetable, cost));
        }
    }
    @Given("^I have another order$")
    public void i_have_another_order(List<VegeItem> list) throws Throwable {
        for (VegeItem vegeItem : list) {
            String vegetable = vegeItem.getVegetable ();
            int amount = vegeItem.getAmount();
            int cost = vegeItem.getCost ();
            System.out.println (String.format("Order of {0} {1}s at the cost of {2}", amount, vegetable, cost));
        }
    }

}
