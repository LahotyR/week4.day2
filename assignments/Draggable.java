package week4.day2.assignments;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Draggable {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		
		ChromeDriver driver = new ChromeDriver();
		
		driver.get("https://jqueryui.com/draggable");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		Actions builder = new Actions(driver);
		
		driver.switchTo().frame(0);
		
		Thread.sleep(1000);
		
		WebElement drag = driver.findElement(By.id("draggable"));
		
		builder.dragAndDropBy(drag, 100, 50).perform();
		
		System.out.println("Dragging successful");
		
		driver.close();
		
		

	}

}
