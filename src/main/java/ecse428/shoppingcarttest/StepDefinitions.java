package ecse428.shoppingcarttest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

public class StepDefinitions {
	@Before
	public void setUp() {
		System.out.println("Set up?");
	}
	
	@Given("I have an empty shopping cart")
	public void I_have_an_empty_shopping_cart() {
		System.out.println("Make the cart empty here");
	}
}
