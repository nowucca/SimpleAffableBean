# SimpleAffableBean
This project is a rewrite of the Affable Bean EJB web app, moving away from EJB and using a simpler technology stack.

This software is an adaptation from the Affable Bean project.  
That project was released under the 2-clause Berkeley licence by Sun and then Oracle.
Please see the LICENCE.affablebean.txt file for compliance and licensing concerns for the original project.

This project is being released under the "new" BSD 3-clause license.

The starting point for this project is the complete Affable beans project at: https://netbeans.org/projects/samples/downloads/download/Samples/JavaEE/ecommerce/AffableBean_complete.zip

## Problems Being Addressed

Hardly anyone I know uses the EJB stack as it was meant to be used, 
with session and entity beans, data sources, services and the rest of it.  
I've found that some of the best technology stacks avoid too much abstraction, 
and are straightforward to use, and lead to code that is straightforward to reason about.  

I would classify a full-blown EJB container as getting low-marks in those areas.


## Solutions Being Proposed

The goal of this rewrite is to enable the use of a pure servlet container (such as Tomcat or Jetty) to run the AffableBean project.
To achieve this, we will be writing our own DataSource wrapper, writing a separate business logic layer (services and daos) and using a combination of servlets and JSPs for the front layer (including the ViewModel pattern of modelling the view at the servlet layer with bean classes). 

As progress is made, I'll be tweaking the implementation here and perhaps strategies as I learn limitations along the way.

## Changes to the Original AffableBean Implementation
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
