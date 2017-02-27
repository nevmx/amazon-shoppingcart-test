package ecse428.shoppingcarttest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

public class StepDefinitions {
	
	private WebDriver wd;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver-v0.14.0-win32\\geckodriver.exe");
		wd = new FirefoxDriver();
	}
	
	@Given("I have an empty shopping cart")
	public void I_have_an_empty_shopping_cart() {
		System.out.println("Make the cart empty here");
		wd.get("http://amazon.ca/");
	}
}
