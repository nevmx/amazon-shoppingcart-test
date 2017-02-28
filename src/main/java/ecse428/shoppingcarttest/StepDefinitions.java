package ecse428.shoppingcarttest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	private WebDriver wd;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver-v0.14.0-win32\\geckodriver.exe");
		wd = new FirefoxDriver();
		wd.get("http://amazon.ca/");
	}
	
	@Given("I have an empty shopping cart")
	public void I_have_an_empty_shopping_cart() {
		// Get to the shopping cart page
		WebElement navCart = wd.findElement(By.id("nav-cart"));
		navCart.click();
		
		// Empty the shopping cart
		List<WebElement> deleteButtons = wd.findElements(By.name("Delete"));
		for (WebElement db : deleteButtons) {
			db.click();
		}
	}
	
	@Given("I am adding item (.*)")
	public void I_am_adding_item(final String itemName) throws InterruptedException {
		// The following code avoids the StaleElementReferenceException
		new WebDriverWait(wd, 5)
	    .ignoring(StaleElementReferenceException.class)
	    .until(new Predicate<WebDriver>() {
	        public boolean apply(@Nullable WebDriver driver) {
	        	// Search for the item
	            WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
	            searchInput.click();
	            searchInput.sendKeys(itemName);
	    		searchInput.submit();
	            
	            return true;
	        }
	    });
				
		// Click the item name in the results
		WebElement itemResult = fluentWait(By.id("result_0"));
		itemResult.findElement(By.tagName("h2")).click();
	}
	
	@Given("I am adding quantity (.*)")
	public void I_am_adding_quantity(String itemQuantity) {
		// Find the quantity dropdown
		Select quantityDropdown = new Select(fluentWait(By.id("quantity")));
		
		// Select the right quantity
		quantityDropdown.selectByValue(itemQuantity);
	}
	
	@Given("The item has status (.*)")
	public void The_item_has_status(String status) {
		System.out.println("Check item status");
	}
	
	@When("I click (.*)")
	public void I_click(String button) {
		if (button.equals("\"Add to Cart\"")) {
			WebElement addToCart = fluentWait(By.id("add-to-cart-button"));
			addToCart.click();
		}
	}
	
	@Then("I have (.*) of (.*) in my cart")
	public void I_have_of_in_my_cart(String quantity, String item) {
		// Go to the shopping cart page
		fluentWait(By.id("hlb-view-cart-announce")).click();
		
		// Verify quantity of the item
		String quantityStr = fluentWait(By.className("a-dropdown-prompt")).getText();
		Assert.assertTrue(quantityStr.equals(quantity));
	}
	
	@After
	public void tearDown() {
		wd.quit();
	}
	
	private WebElement fluentWait(final By locator) {
	    Wait<WebDriver> wait = new FluentWait<WebDriver>(wd)
	            .withTimeout(30, TimeUnit.SECONDS)
	            .pollingEvery(2, TimeUnit.SECONDS)
	            .ignoring(NoSuchElementException.class);

	    WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
	        public WebElement apply(WebDriver driver) {
	            return driver.findElement(locator);
	        }
	    });

	    return  foo;
	};
	
}
