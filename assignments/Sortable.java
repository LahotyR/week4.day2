package week4.day2.assignments;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Sortable {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		
		ChromeDriver driver = new ChromeDriver();
		
		driver.get("https://jqueryui.com/sortable");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		Actions builder = new Actions(driver);
		
		driver.switchTo().frame(0);
		
		int size = driver.findElements(By.xpath("//li[@class='ui-state-default ui-sortable-handle']")).size();
		System.out.println("List size: " + size);
		
		for (int i = 1; i < size; i++) {
			
			WebElement item1 = driver.findElement(By.xpath("//ul[@id='sortable']/li[text()='Item " + i + "']"));
			int j=size-i;
			WebElement item2 = driver.findElement(By.xpath("//ul[@id='sortable']/li[text()='Item " + j + "']"));
			System.out.println(item1.getText());
			builder.clickAndHold(item1).perform();
			builder.moveToElement(item2).release().perform();
	
		}
		
		System.out.println("Sort successful");
		
		driver.close();

	}

}
