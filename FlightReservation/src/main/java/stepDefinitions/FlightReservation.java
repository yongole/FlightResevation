package stepDefinitions;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.*;
import pages.FlightReservationPage;
import util.FlightReservationBase;

public class FlightReservation extends FlightReservationBase {
	List<WebElement> listOfPrices;
	List<Integer> listOfPricesInInt;
	List<WebElement> listOfDuration;
	int cheapestFlight;
	List<Integer> chapestFlightIndex;
	Map<Integer,String[]> flightDetails;
	Map<Integer,String[]> lessDurationFlights;
	Map<Integer,String[]> eveFlights;
	Map<Integer,String[]> finalFlight;
	FlightReservationPage reserve;


	@Given("^User navigates to flight application$")

	public void navigateToApp() throws InterruptedException{
		FlightReservationBase.init();
		reserve = new FlightReservationPage(driver);
		Thread.sleep(5000);
		
	}

	@When("^User in flights selection page$")

	public void selectFlightsPage() throws InterruptedException{
		reserve.clickFlightButton();
	}

	@Then("^User enters source \"(.*)\" and user enters destination \"(.*)\" and date \"(.*)\"")

	public void user_enters_source_and_user_enters_destination_and_date(String source,String destination,String departDate) throws InterruptedException{
		reserve.selectOneWayTrip();
		reserve.selectSource(source);
		reserve.selectDestination(destination);
		reserve.enterDate(departDate);
		reserve.clickSearchFlights();
	}
	@And("^User searches flights$")
	public void user_searches_flights(){
		listOfPrices = reserve.getFlightPrice();
		listOfPricesInInt=new ArrayList<Integer>();

		/*Removing special characters from price and adding to arraylist*/

		for(WebElement priceObj: listOfPrices){
			listOfPricesInInt.add(Integer.parseInt(priceObj.getText().substring(1, priceObj.getText().length())));
		}

		/*Getting the cheapest price from list of prices*/
		cheapestFlight=listOfPricesInInt.get(0);
		chapestFlightIndex=new ArrayList<Integer>();
		for(int i=0;i<listOfPricesInInt.size();i++){
			if(listOfPricesInInt.get(i)<cheapestFlight){
				cheapestFlight=listOfPricesInInt.get(i);
			}
		}
		/*Getting index of flights with lowest price*/
		for(int i=0;i<listOfPricesInInt.size();i++){
			if(listOfPricesInInt.get(i)==cheapestFlight){
				chapestFlightIndex.add(i+1);
			}
		}
		System.out.println();
		/*Printing of index of flights with lowest price*/
		for(int i=0;i<chapestFlightIndex.size();i++){
			System.out.println("Cheapest Filghts found at: "+chapestFlightIndex.get(i));
		}
		System.out.println("Chepaest flight price is: "+cheapestFlight+" Number of Flights: "+chapestFlightIndex.size());

		/*Getting Duration of cheapest Flights- 
		 * saving flight details in map
		 * (key-index,values(String Array)-start-time,am/pm,total duration)*/

		listOfDuration = reserve.getDurationList();
		flightDetails= new HashMap<Integer, String[]>();

		for(int i=0;i<chapestFlightIndex.size();i++){
			String[] tempArray=new String[3] ;
			System.out.println("Duration of flight: "+listOfDuration.get(chapestFlightIndex.get(i)-1).getText().toString());
			int from = (chapestFlightIndex.get(i))*2-1;
			tempArray[0] = driver.findElement(By.xpath("//*[contains(@id,'searchResultsList')]//descendant::*[contains(@class,'depart-time base-time')]["+chapestFlightIndex.get(i)+"]")).getText();
			tempArray[1] = driver.findElement(By.xpath("//*[contains(@id,'searchResultsList')]//descendant::*[contains(@class,'time-meridiem meridiem')]["+from+"]")).getText();
			tempArray[2] = listOfDuration.get(chapestFlightIndex.get(i)-1).getText().toString();
			String[] tempDuration = tempArray[2].split(" ");
			int totalTime=Integer.parseInt(tempDuration[0].split("h")[0])*60;
			tempDuration[1]=tempDuration[1].substring(0,2);
			totalTime=totalTime+Integer.parseInt(tempDuration[1]);
			System.out.println("Total time in minutes: "+totalTime);
			tempArray[2]=String.valueOf(totalTime);
			flightDetails.put(chapestFlightIndex.get(i), tempArray);
		}
		lessDurationFlights= new HashMap<Integer, String[]>(flightDetails);
		Map<Integer,String[]> tempFlig= new HashMap<Integer, String[]>(flightDetails);
		/*Finding flights with less duration
		 *  from map generated with flight details*/
		for(Entry<Integer, String[]> f1: flightDetails.entrySet()){
			for(Entry<Integer, String[]> temp: tempFlig.entrySet()){
				if(Integer.parseInt(temp.getValue()[2])>Integer.parseInt(f1.getValue()[2])){
					lessDurationFlights.remove(temp.getKey());
				}
			}

			/*Selecting Evening Flight in case duration is same*/
			eveFlights= new HashMap<Integer, String[]>();
			for(Entry<Integer, String[]> flight: lessDurationFlights.entrySet()){
				String tempArray[]=flight.getValue();
				if(tempArray[1].equals("pm")){
					eveFlights.put(flight.getKey(),flight.getValue());
				}
			}
			/*Selecting evening flight which starts late
			 *  compared to other evening time*/

			finalFlight= new HashMap<Integer, String[]>(eveFlights);
			Map<Integer,String[]> tempFlights= new HashMap<Integer, String[]>(eveFlights);
			for(Entry<Integer, String[]> temp: eveFlights.entrySet()){
				for(Entry<Integer, String[]> tempFlight:tempFlights.entrySet()){
					String[] a=tempFlight.getValue()[0].split(":");
					String[] b=temp.getValue()[0].split(":");
					if(Integer.parseInt(a[0])<Integer.parseInt(b[0])){
						finalFlight.remove(tempFlight.getKey());
					}
				}
			}
		}
	}

	//Displaying final flight
	@Then("User finds flights")
	public void user_finds_flights(){
		for(Entry<Integer, String[]> temp: finalFlight.entrySet()){
			System.out.println("Final flight is located at index "+temp.getKey());
			System.out.println("Final flight is "+temp.getValue()[0]+temp.getValue()[1]);
		}

	}
}