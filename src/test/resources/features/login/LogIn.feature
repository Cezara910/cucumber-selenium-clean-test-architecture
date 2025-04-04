Feature: Log In
  In order to rent books
  As an registered reader
  I want to log in with username or barcode and PIN

  @sanity
  Scenario: Reject invalid barcode and pin
    Given the user is on the main page
    And clicks on Log In
    When user enters invalid barcode
    And invalid PIN
    And clicks on Log In button
    Then pop-up with reject message appears
    And the pop-up fades after 20 seconds

#  Scenario: A different situation
#  ...