package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightReservationBase extends Constants{
	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverWait wait;
	public  FlightReservationBase() {
		prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("src/main/java/config/config.properties");
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	public static WebDriver init(){
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "D:\\eclipse\\chromedriver\\chromedriver.exe");	
			driver = new ChromeDriver(); 
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			wait=new WebDriverWait(driver, EXPLICIT_WAIT_TIME);
			driver.get(prop.getProperty("url"));
			driver.manage().window().maximize();
		}
		return driver;
	}

}
