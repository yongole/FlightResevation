package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/java/Features/FlightReservation.feature", glue = "stepDefinition", plugin = {
		"pretty", "html:target/cucumber-reports/cucumber-pretty",
		"json:target/cucumber-reports/cucumberTestReports.json",
		"rerun:target/cucumber-reports/rerun.txt" }, monochrome = true)

public class FlightReservationRunner {

}
