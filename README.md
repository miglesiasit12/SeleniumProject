The requirements for the test project are:
1) Write an automated test for an e-commerce site:
 * Pick one of your favorite e-commerce websites
 * You need to implement following tests:
   * Login
   * Searching products by three criteria
   * Adding products to the cart
   * Removing products from the cart
   * Checkout process
   * If possible, implement a sign-up / registration test
 * Add an HTML report of test results
 * Report any bugs you find by writing a bug ticket
 * Please prepare a document with the test flows and test cases. The documents have to be clear both to the developer and to someone who is not familiar with the technology.
 * Run tests in a continuous integration tool and optionally in the cloud

2) Write an automated test for a REST API service
 * Implement REST API tests for some of “location” services by your choice. The idea of this test is to implement tests for creating data, modifying existing data or deleting data. For example, you can create your own map with pins on it.
 * You may pick any REST API provider which offers such service (as long it’s free to use).
 * Implement test cases of sending location information to the map. Test sending proper information, invalid information, incorrect format, and other possible edge cases.
 * Test for at least three different HTTP response codes in your tests. For example, 200 OK, 401 Unauthorized or 304 Not Modified.
 * Add an HTML report of test results
 * Report any bugs you find by writing a bug ticket

3) Pick a random web application and create a load test with a tool of your choice but using an HTTP/S protocol. Load test needs to simulate 1000 users who will visit the homepage in a period of 15s. Measure web application response time before and during the test run.
  
  a) Explain the test in details\
  1 request is sent before sending the 1000 user requests over 15 seconds and 1 request after. This is repeated for the GET index.html(homepage) and OPTIONS bycat path to compare the difference in response time.

  b) Did the load test have an impact on web application response time?\
  No impact on the application's response time before and after

  c) What is the optimal application response time for modern day web applications?\
  0.1 seconds is optimal, under 2 seconds is acceptable

  d) Analyze few HTTP/S responses\
  The responses all returned 200 ok and expected bodies.

The results and test plan are located in src/test/resources/LoadTesting

Html results http://allureresults.s3-website.us-east-2.amazonaws.com/#
