package week4.day2.assignments;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Selectable {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		
		ChromeDriver driver = new ChromeDriver();
		
		driver.get("https://jqueryui.com/selectable");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		Actions builder = new Actions(driver);
		
		driver.switchTo().frame(0);
		
		builder.keyDown(Keys.CONTROL);
		
		for (int i = 1; i < 7; i++) {
			
			WebElement element = driver.findElement(By.xpath("//li[text() = 'Item " + i + "']"));
			i++;
			builder.click(element).perform();
		}
		
		builder.keyUp(Keys.CONTROL);
		
		System.out.println("Selection successful");
		
		driver.close();

	}

}
