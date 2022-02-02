package week4.day2.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nykaa {

	public static void main(String[] args) throws InterruptedException {
		
		// Browser Setup
		WebDriverManager.chromedriver().setup();
		
		ChromeDriver driver = new ChromeDriver();
		
		ChromeOptions options = new ChromeOptions();
		
		Actions builder = new Actions(driver);
		
		// Go to https://www.nykaa.com/
		driver.get("https://www.nykaa.com/");
		
		options.addArguments("--disable-notifications");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		// Mouse hover and select brand
		WebElement brand = driver.findElement(By.xpath("//ul[@class='HeaderNav css-f7ogli']//a[contains(text(),'brands')]"));
		builder.moveToElement(brand).perform();
		driver.findElement(By.xpath("//input[@id='brandSearchBox']")).sendKeys("L'Oreal Paris");
		driver.findElement(By.linkText("L'Oreal Paris")).click();
		
		String title = driver.getTitle();
		
		if(title.contains("L'Oreal Paris")) {
			System.out.println("On page: " +title);
		} else {
			System.out.println("On wrong page");
		}
		
		// Sort
		driver.findElement(By.xpath("//span[contains(text(),'Sort By : popularity')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'customer top rated')]")).click();
		
		Thread.sleep(2000);
		
		// Select Category -> Shampoo
		driver.findElement(By.xpath("//span[contains(text(),'Category')]")).click();
		driver.findElement(By.xpath("//span[text()='Hair']")).click();
		driver.findElement(By.xpath("//span[text()='Hair Care']")).click();
		WebElement shampoo = driver.findElement(By.xpath("//span[text()='Shampoo']"));
		String text1= shampoo.getText();
		shampoo.click();
		System.out.println("Shampoo is selected");
		
		// Select Concern -> Color Protection
		driver.findElement(By.xpath("//span[text()='Concern']")).click();
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("Color Protection");
		WebElement color = driver.findElement(By.xpath("//span[text()='Color Protection']"));
		String text2 = color.getText();
		color.click();
		
		// Validation for filters
		String filterShampoo = driver.findElement(By.xpath("//span[text()='Shampoo' and @class='filter-value']")).getText();
		String filterColor = driver.findElement(By.xpath("//span[text()='Color Protection' and @class='filter-value']")).getText();
		
		if(filterShampoo.equals(text1) && filterColor.equals(text2)) {
			System.out.println("Shampoo is selected in filters");
		} else {
			System.out.println("Filters are not applied properly");
		}
		
		// Click on selected product
		driver.findElement(By.xpath("//div[@class=\"productWrapper css-la441k\"][1]")).click();
		
		// Switch to the new tab
		Set<String> handles = driver.getWindowHandles();
		List<String> window = new ArrayList<String>(handles);
		String child = window.get(1);
		driver.switchTo().window(child);
		
		// Select 175 ml shampoo
		WebElement size = driver.findElement(By.xpath("//select[@title='SIZE']"));
		Select drop = new Select(size);
		drop.selectByVisibleText("175ml");
		
		// Print price
		String price = driver.findElement(By.xpath("//span[@class='css-12x6n3h']")).getText();
		System.out.println("Product price is: " + price);
		
		driver.findElement(By.xpath("//div[@class='css-1rn9gza']//span[contains(text(),'ADD TO BAG')]")).click();
		
		// Go to shopping bag
		driver.findElement(By.xpath("//span[@class='cart-count']")).click();
		driver.switchTo().frame(0);
		Thread.sleep(5000);
		
		// Print Grand Total
		String grandTotal = driver.findElement(By.xpath("//div[@class='sticky-bottom proceed-cart-btn']//span[text()='Grand Total']/following::div[@class='value']")).getText();
		System.out.println("Grand Total in shopping cart: " + grandTotal);
		
		// Proceed
		driver.findElement(By.xpath("//span[@class='vernacular-string' and text()='PROCEED']")).click();
		
		//Continue as guest
		driver.findElement(By.xpath("//button[ text()='CONTINUE AS GUEST']")).click();
		
		// Compare prices
		String compareTotal = driver.findElement(By.xpath("//div[text()='Grand Total']/following-sibling::div[@class='value']")).getText();
		System.out.println("Final price" + compareTotal);
		
		if(grandTotal.equals(compareTotal)) {
			System.out.println("Prices match");
		} else {
			System.out.println("Mismatch in prices");
		}
		
		// Close browser
		driver.quit();
	}

}
