package week4.day2.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Snapdeal {

	public static void main(String[] args) throws InterruptedException, IOException {

		// Browser Setup
		WebDriverManager.chromedriver().setup();

		ChromeDriver driver = new ChromeDriver();

		ChromeOptions options = new ChromeOptions();

		Actions builder = new Actions(driver);

		// Go to Snapdeal
		driver.get("https://www.snapdeal.com/");

		options.addArguments("--disable-notifications");

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// Mouse hover Men and open sports shoes
		WebElement men = driver.findElement(By.xpath("//a/span[text() = \"Men's Fashion\"]"));
		builder.moveToElement(men).perform();
		builder.pause(Duration.ofSeconds(2));
		WebElement shoes = driver.findElement(By.linkText("Sports Shoes"));

		builder.moveToElement(men).pause(2000).click(shoes).perform();

		// Count of sports shoes
		String shoeCount = driver.findElement(By.xpath("//div[@class='child-cat-name selected']/following-sibling::div")).getText();
		System.out.println("Total sports shoes: " + shoeCount);

		// Click on training shoes
		driver.findElement(By.xpath("//div[@class='child-cat-name '][text()='Training Shoes']")).click();

		// Sort by Low to High
		driver.findElement(By.xpath("//div[@class='sort-drop clearfix']")).click();
		driver.findElement(By.xpath("//ul[@class='sort-value']/li[@data-index=1]")).click();
		Thread.sleep(2000);

		// Check if the items displayed are sorted correctly
		List<WebElement> price = driver.findElements(By.xpath("//span[@class='lfloat product-price']"));
		List<Integer> sortPrice = new LinkedList<Integer>();

		for (WebElement string : price) {
			sortPrice.add(Integer.parseInt(string.getText().substring(4).replaceAll(",", "")));
		}
		
		List<Integer> sorted = new ArrayList<Integer>(sortPrice);
		//int size = sorted.size();

		Collections.sort(sorted);

		if (sorted.equals(sortPrice)) {
			System.out.println("The items displayed are sorted correctly ");
		} else {
			System.out.println("Sorting issue !!!");
		}

		// Select the price range (900-1200)
		String price1 = "900";
		String price2 = "1200";
		String color = "Navy";
		WebElement fromPrice = driver.findElement(By.xpath("//input[@name='fromVal']"));
		fromPrice.clear();
		fromPrice.sendKeys(price1);
		WebElement toPrice = driver.findElement(By.xpath("//input[@name='toVal']"));
		toPrice.clear();
		toPrice.sendKeys(price2);
		driver.findElement(By.xpath("//div[contains(text(),'GO')]")).click();
		Thread.sleep(2000);

		// Filter with color Navy
		driver.findElement(By.xpath("//button[@data-filtername='Color_s']")).click();
		driver.findElement(By.xpath("//label[@for='Color_s-Navy']")).click();

		// Verify the applied filters
		// div[@class='navFiltersPill']/a[contains(@data-key,'Price') and
		// contains(text(),'Rs. 900 - Rs. 1200')]
		// div[@class='navFiltersPill']/a[contains(@data-key,'Color') and
		// contains(text(),'Navy')]

		String priceFilter = driver
				.findElement(By.xpath("//div[@class='navFiltersPill']/a[contains(@data-key,'Price')]")).getText();
		String colorFilter = driver
				.findElement(By.xpath("//div[@class='navFiltersPill']/a[contains(@data-key,'Color')]")).getText();
		if (priceFilter.contains(price1) && priceFilter.contains(price2) && colorFilter.contains(color)) {
			System.out.println("Filters are applied successfully");
		} else {
			System.out.println("Issue with filters");
		}

		// Mouse Hover on first resulting Training shoes
		WebElement selectedShoe = driver
				.findElement(By.xpath("//section[@class='js-section clearfix dp-widget dp-fired'][1]/div[1]"));
		builder.moveToElement(selectedShoe).perform();

		// Click QuickView button
		driver.findElement(By.xpath("(//div[contains(text(),'Quick View')])[1]")).click();

		// Print the cost and the discount percentage
		String discountedPrice = driver
				.findElement(By.xpath("//div[@class='product-price pdp-e-i-PAY-l clearfix']/span[@class='payBlkBig']"))
				.getText();
		String discount = driver
				.findElement(
						By.xpath("//div[@class='product-price pdp-e-i-PAY-l clearfix']/span[@class='percent-desc ']"))
				.getText();

		System.out.println("Discounted price: " + discountedPrice);
		System.out.println("Discount %: " + discount);

		// Take the snapshot of the shoes.
		
		  WebElement pic = driver.findElement(By.xpath("//div[contains(@class,'col-xs-11 quickViewModal')]")); 
		  File input = pic.getScreenshotAs(OutputType.FILE); 
		  File output = new File("./target/Images/SnapdealShoes.png"); 
		  FileUtils.copyFile(input, output);
		 
		// Close the current window
		driver.findElement(By.xpath("//div[contains(@class,'close close1 marR10')]")).click();

		// Close the main window
		driver.close();

	}

}
