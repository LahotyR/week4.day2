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

import io.github.bonigarcia.wdm.WebDriverManager;

public class Myntra {

	public static void main(String[] args) throws InterruptedException, IOException {
	
		// Browser Setup
		WebDriverManager.chromedriver().setup();

		ChromeDriver driver = new ChromeDriver();

		ChromeOptions options = new ChromeOptions();

		Actions builder = new Actions(driver);
		
		// Open https://www.myntra.com/
		driver.get("https://www.myntra.com/");

		options.addArguments("--disable-notifications");

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));		
		
		// Mouse hover on Men
		WebElement men = driver.findElement(By.xpath("//a[@data-group='men']"));
		builder.moveToElement(men).pause(1000).perform();
		Thread.sleep(1000);
		
		// Click Jackets 
		driver.findElement(By.linkText("Jackets")).click();
		
		// Find the total count of item
		String jacketCount = driver.findElement(By.xpath("//span[@class='title-count']")).getText();
		String[] split = jacketCount.split(" ");
		
		int length = split.length;
		int countj = 0;
		int countcat = 0;
		int sumCat = 0;
		for (int i = 0; i < length; i++) {
			countj = Integer.parseInt(split[2]);
			
			
		}
		
		System.out.println("Total count of jackets: " + countj);
		
		// Validate the sum of categories count matches
		List<WebElement> countCategories = driver.findElements(By.xpath("//span[@class='categories-num']"));
		for (WebElement ele : countCategories) {
			countcat = Integer.parseInt(ele.getText().replace(")", "").substring(1));
				
				sumCat = countcat + sumCat;		
		}
		
		System.out.println("Count of jackets in individual category: "  + sumCat);
		
		if (countj == sumCat) {
			System.out.println("Sum of category count match total count");
		} else {
			System.out.println("The count of jackets don't match the sum of jacket category count");
		}
		
		// Check jackets
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='checkbox' and @value = 'Jackets']/following-sibling::div")).click();
		Thread.sleep(2000);
		
		// Click + More option under BRAND
		driver.findElement(By.xpath("//div[@class='brand-more']")).click();
		
		//	Type Duke and click checkbox
		driver.findElement(By.xpath("//input[@placeholder='Search brand']")).sendKeys("Duke");
		//driver.findElement(By.xpath("//label[@class=' common-customCheckbox']/input[1]")).click();
		driver.findElement(By.xpath("//input[@type='checkbox' and @value = 'Duke']/following-sibling::div")).click();
		
		// Close the pop-up x
		driver.findElement(By.xpath("//span[@class = 'myntraweb-sprite FilterDirectory-close sprites-remove']")).click();
		
		// Confirm all the Coats are of brand Duke Hint : use List
		List<WebElement> coatsBrand = driver.findElements(By.xpath("//h3[@class= 'product-brand' and contains(text(),'Duke')]"));
		
		for (int i = 0; i < coatsBrand.size(); i++) {
		String text = coatsBrand.get(i).getText();	
		
		if (text.contains("Duke")) {
			continue;
		} else {
			System.out.println("Brand name mismatch");
			break;
		}
		
		}
		
		// Sort by Better Discount
		driver.findElement(By.xpath("//div[@class= 'sort-sortBy']")).click();
		driver.findElement(By.xpath("//ul[@class='sort-list']/li[3]")).click();
		
		// Find the price of first displayed item
		List<WebElement> price = driver.findElements(By.xpath("//span[@class='product-discountedPrice']"));
		
		for (int i = 0; i < 1; i++) {
			System.out.println("Price of first item displayed: " + price.get(0).getText());
		}
		
		// Click on the first product
		driver.findElement(By.xpath("//li[@class='product-base'][1]")).click();
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> win = new ArrayList<String>(windowHandles);
		driver.switchTo().window(win.get(1));
		
		// Take a screen shot
		File input = driver.getScreenshotAs(OutputType.FILE);
		File output = new File("./target/Images/MyntraJackets.png");
		FileUtils.copyFile(input, output);
		
		// Click on WishList Now
		driver.findElement(By.xpath("//div[contains(@class,'pdp-add-to-wishlist')]")).click();
		
		// Close Browser
		driver.quit();
		
	}

}
