package week4.day2.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ErailUnique {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("-incognito");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("https://erail.in/");
		
		WebElement checkBox = driver.findElement(By.id("chkSelectDateOnly"));
		if(checkBox.isSelected() == true) {
			checkBox.click();
		} else {
			System.out.println("Check box is already un-selected");
		}
		
		WebElement from = driver.findElement(By.id("txtStationFrom"));
		from.clear();
		from.sendKeys("SLGR",Keys.ENTER);
		
		WebElement to = driver.findElement(By.id("txtStationTo"));
		to.clear();
		to.sendKeys("GHY",Keys.ENTER);
			
		Thread.sleep(2000);
		
		int size = driver.findElements(By.xpath("//table[@class='DataTable TrainList TrainListHeader']//tr")).size();
		
		List<String> trainList = new ArrayList<String>();
		
		for (int i = 1; i <= size; i++) {
			
			String train = driver.findElement(By.xpath("//table[@class='DataTable TrainList TrainListHeader']//tr[" +i+ "]/td[2]")).getText();
			System.out.println("Train Name: " + train);
			trainList.add(train);
			
		}
	
		Set<String> trainSet = new HashSet<String>(trainList);
		
		if(trainList.size() == trainSet.size()) {
			System.out.println("List has no duplicate Train Names");
			System.out.println("List size is: " + trainList.size());
		} else {
			System.out.println("List has duplicate Train Names");
			System.out.println(trainList.size() + " " + trainSet.size());
		}
		
		driver.close();

	}

}
