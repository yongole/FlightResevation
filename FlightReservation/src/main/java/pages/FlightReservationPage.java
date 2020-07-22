package pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.FlightReservationBase;

public class FlightReservationPage extends FlightReservationBase {
	
	public FlightReservationPage(WebDriver driver){
		PageFactory.initElements(driver, this);	
	}
	@FindBy(xpath="//a[contains(.,'Flights')]")
	WebElement flightButton;
	
	@FindBy(xpath ="//div[contains(@id,'switch-display')][1]")
	WebElement switchTrip;
	
	
	@FindBy(xpath="//li[contains(@data-value,'oneway')]")
	WebElement selectOneWayTrip;
	
	@FindBy(xpath="//div[contains(@id,'origin-input-wrapper')]//following-sibling::div")
	WebElement sourceInputWrapper;
	
	@FindBy(xpath="//input[contains(@id,'origin-airport')][1]")
	WebElement source;
	
	@FindBy(xpath="//div[contains(@id,'destination-input-wrapper')]//following-sibling::div")
	WebElement destinationInputWrapper;
	
	@FindBy(xpath="//input[contains(@id,'destination-airport')][1]")
	WebElement destination;
	
	@FindBy(xpath="//div[contains(@id,'dateRangeInput-display-start')]//following-sibling::div")
	WebElement departInputWrapper;
	
	@FindBy(xpath="//div[contains(@id,'depart-input')]")
	WebElement departDate;
	
	@FindBy(xpath="//button[@type='submit']//following-sibling::span[contains(text(),'Search')][1]")
	WebElement search;
	
	@FindBy(xpath = "//div[contains(@id,'above-button')]//child::span[contains(@class,'price option-text')]//parent::span[contains(@class,'price-text')]")
	List<WebElement> pricesList;
	
	@FindBy(xpath = "//*[contains(@id,'searchResultsList')]//descendant::*[contains(@class,'col-field duration')]")
	List<WebElement> durationList;
	
	@FindBy(xpath = "//*[contains(@id,'searchResultsList')]//descendant::*[contains(@class,'depart-time base-time')]")
	List<WebElement> deprtTime;
	
	@FindBy(xpath = "//*[contains(@id,'searchResultsList')]//descendant::*[contains(@class,'time-meridiem meridiem')]")
	List<WebElement> merdianTime;
	

	public void clickFlightButton() throws InterruptedException{
		wait.until(ExpectedConditions.elementToBeClickable((flightButton)));
		flightButton.click();
	}
	
	public void selectOneWayTrip(){
		wait.until(ExpectedConditions.elementToBeClickable((switchTrip)));
		switchTrip.click();
		selectOneWayTrip.click();
	}
	
	public void selectSource(String sourceName) throws InterruptedException{
		sourceInputWrapper.click();
		source.clear();
		source.sendKeys(sourceName);
		Thread.sleep(5000);
		source.sendKeys(Keys.ENTER);
	}
	
	public void selectDestination(String destinationName) throws InterruptedException{
		destinationInputWrapper.click();
		destination.clear();
		destination.sendKeys(destinationName);
		Thread.sleep(5000);
		destination.sendKeys(Keys.ENTER);
		
	}
	
	public void enterDate(String fromDate) throws InterruptedException{
		departInputWrapper.click();
		departDate.clear();
		departDate.sendKeys(fromDate);
		Thread.sleep(5000);
		departDate.sendKeys(Keys.ENTER);
	}
	
	public void clickSearchFlights() throws InterruptedException{
		search.click();
		//Thread.sleep(30000);
	}
	public List<WebElement> getFlightPrice(){
		wait.until(ExpectedConditions.visibilityOfAllElements(((pricesList))));
		return  pricesList;
	}
	public List<WebElement> getDurationList(){
		return durationList;
	}

}
