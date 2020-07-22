Feature: FlightReservation
Scenario: FlightReservation
Given User navigates to flight application
When User in flights selection page
Then User enters source "Kochi (COK)" and user enters destination "Hyderabad (HYD)" and date "10/10/2020"
And User searches flights
Then User finds flights