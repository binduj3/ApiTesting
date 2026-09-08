Feature: Booking related features

  Scenario: Authenticate and get token
    Given a request is prepared to authenticate
    When a post request is made for "auth"
    Then a statuscode of 200 is received
    And the token is stored as a global variable

 Scenario: Create a booking
   Given a request is prepared to create a booking
   When a post request is made for "create"
   Then a statuscode of 200 is received
   And the bookingid is stored as a global variable
   And the firstname in the response is "Jim" for "create"

  Scenario: Get all booking
    Given a request is prepared to get all booking
    When a get request is made for "all booking"
    Then a statuscode of 200 is received
    And the booking list should not be empty

  Scenario: Get a booking
   Given a request is prepared to get a booking using the stored bookingid
   When a get request is made for "a booking"
   Then a statuscode of 200 is received

 Scenario: Update a booking
   Given a request is prepared to update a booking using the stored token
   When a put request is made
   Then a statuscode of 200 is received
   And the firstname in the response is "James updated" for "update"

  @negative
  Scenario: Update a booking without authentication
    Given a request is prepared to update a booking without a token
    When a put request is made
    Then a statuscode of 403 is received

  @negative
  Scenario: Get a booking with an invalid id
    Given a request is prepared to get a booking with an invalid id
    When a get request is made for "invalid booking"
    Then a statuscode of 404 is received

 Scenario: Delete a booking
   Given a request is prepared to delete a booking using the stored token
   When a delete request is made
   Then a statuscode of 201 is received
   And the deleted booking should no longer exist
