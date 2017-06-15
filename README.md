# SimpleAffableBean
This project is a rewrite of the Affable Bean EJB web app, moving away from EJB and using a simpler technology stack.
This software is an adaptation from the Affable Bean project.  

See [the SimpleAffableBean tutorial and information site](http://simpleaffablebean.info)
for more information.

## Packing List

To run this project, you will need the following items installed:

* MySQL database version 5.5+
* Tomcat servlet container version 8.5+
* Java 8 JDK (full development kit)

The project is packaged to be automatically built using the Gradle build system (or an IDE that supports Gradle). 
The output of the build is a "web application archive" (WAR) file that can be copied into $TOMCAT_HOME/webapps for deployment.


## Summary of Changes to the Original AffableBean Implementation
- We forego the use of EJB database patterns for a DAO pattern using JDBC access to MySQL
- All pages except the Admin pages use the MVVM model for presentation layer simplicity.
- Servlets have been separated out for ease of readability and maintenance. 
- Fewer URLs are used and correspond to pages
- Language switching now uses a returnUrl parameter to know what page to return to (allowing removal of the 'view' session parameter mechanism)
- All POST operations use the POST-redirect-GET pattern to avoid page reloads performing POST actions
- Checkout no longer invalidates the session but does clear the cart
- Added a "Continue Shopping" button to the confirmation page that goes to the home page
- Header information is separated into a collection of @included JSP fragments for clarity.
- Customer details entered on checkout are remembered in case of error (except for credit card)
- Validation has been moved into the business layer and uses an exception
- All model objects are immutable once constructed
- Cache control headers are page-specific to tell browsers to avoid caching customer-specific pages.
- A basic local WAR build using gradle
- A central error servlet handling all error pages
- Logging for service classes to allow for easier debugging
- Added a servlet context listener to log servlet context and session context events
- Use a customer form model object to model the data in the customer form
- Added second-level caching using Guava for category and product model objects, with ability to periodically refresh the caches 
- Moved folders to be standard for the build system
- Added acceptance test regime using Selenium-HtmlUnitDriver
