package week4.day2.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Amazon {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Load the uRL https://www.amazon.in/
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("-incognito");
		ChromeDriver driver = new ChromeDriver(options);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
		driver.get("https://www.amazon.in/");
		
		
		// Search as oneplus 9 pro
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Oneplus 9 pro");
		driver.findElement(By.id("nav-search-submit-text")).click();
		
		
		// Get the price of the first product
		String price = driver.findElement(By.xpath("(//a/span[contains(text(),'OnePlus')]/following::span[@class='a-price-whole'])[1]")).getText();
		System.out.println("Product price: " + price);
		
		// Mouse Hover on the stars
		driver.findElement(By.xpath("(//i[@class='a-icon a-icon-popover'])[1]")).click();

		// Print the number of customer ratings for the first displayed product
		String ratings = driver.findElement(By.xpath("//span[@class='a-size-medium a-color-base a-text-beside-button a-text-bold']")).getText();
		System.out.println("Ratings: " + ratings);
		
		// Get the percentage of ratings for the 5 star.
		List<WebElement> table = driver.findElements(By.xpath("//table[@id='histogramTable']//td[3]//a"));
		
		for (WebElement ele : table) {
			String text = ele.getText();
			System.out.println("Percentage: " + text);
			break;
		}
		
		// Click the first text link of the first image
		List<WebElement> firstEle = driver.findElements(By.xpath("//a/span[contains(text(),'OnePlus')]"));
		
		for (WebElement webElement : firstEle) {
			webElement.click();
			break;
		}
		
		Set<String> handles = driver.getWindowHandles();
		List<String> handle = new ArrayList<String>(handles);
		driver.switchTo().window(handle.get(1));
		
		// Take a screen shot of the product displayed
		File input = driver.getScreenshotAs(OutputType.FILE);
		File output = new File("./target/Images/amazon.png");
		FileUtils.copyFile(input, output);
		
		// Click 'Add to Cart' button
		driver.findElement(By.id("submit.add-to-cart")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement close = driver.findElement(By.id("attach-close_sideSheet-link"));
		wait.until(ExpectedConditions.visibilityOf(close));
		close.click();
		Thread.sleep(2000);
		driver.findElement(By.id("nav-cart-count-container")).click();
		
		System.out.println("Added to cart");
		
		// Get the cart subtotal and verify if it is correct.
		driver.findElement(By.id("nav-cart-count")).click();
		
		String subtotal = driver.findElement(By.xpath("//span[@id='sc-subtotal-amount-buybox']/span")).getText().trim();
		System.out.println("Subtotal: "+subtotal);
		String[] split = subtotal.split("\\.");
		String string = split[0].toString();
		System.out.println(string);
		Assert.assertEquals(price, string);
		
		
		// Close the browser
		driver.quit();
		
	}

}
