package week4.day2.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LeafgroundSort {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("-incognito");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("http://www.leafground.com/pages/sorttable.html");
		
		List<WebElement> list = driver.findElements(By.xpath("//table[@id='table_id']/tbody/tr/td[2]"));
		
		List<String> addToList = new ArrayList<String>();
		
		for (WebElement ele : list) {	
			String text = ele.getText();
			addToList.add(text);
		}
		
		System.out.println(" Before Sort: " + addToList);
		Collections.sort(addToList);
		
		
		driver.findElement(By.xpath("//table[@id='table_id']//th[contains(text(),'Name')]")).click();
		
		List<WebElement> list1 = driver.findElements(By.xpath("//table[@id='table_id']/tbody/tr/td[2]"));
		
		List<String> addToList1 = new ArrayList<String>();
		
		for (WebElement ele1 : list1) {	
			String text = ele1.getText();
			addToList1.add(text);
		}
		
		Collections.sort(addToList1);
		System.out.println(addToList1);
		
		addToList.retainAll(addToList1);
		System.out.println("Final List: " + addToList);
		
		driver.close();
	}

}
