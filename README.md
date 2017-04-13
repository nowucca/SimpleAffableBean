# SimpleAffableBean
A rewrite of the Affable Bean EJB web app, moving away from EJB and using a simpler technology stack.

This software is an adaptation from the Affable Bean project, released under the 2-clause Berkeley licence by Sun and then Oracle.
Please see the LICENCE file for compliance and licensing concerns.

The starting point for this project is the complete Affable beans project at: https://netbeans.org/projects/samples/downloads/download/Samples/JavaEE/ecommerce/AffableBean_complete.zip

## Problems Being Addressed

Hardly anyone I know uses the EJB stack as it was meant to be used, with session and entity beans, data sources, services and the rest of it.  I've found that some of the best technology stacks avoid too much abstraction, and are straightforward to use, and lead to code that is straightforward to reason about.  I would classify a full-blown EJB container as getting low-marks in those areas.

## Solutions Being Proposed

The goal of this rewrite is to enable the use of a pure servlet container (such as Tomcat or Jetty) to run the AffableBean project.
To achieve this, we will be writing our own DataSource wrapper, writing a separate business logic layer (services and daos) and using a combination of servlets and JSPs for the front layer (including the ViewModel pattern of modelling the view at the servlet layer with bean classes). 

As progress is made, I'll be tweaking the implementation here and perhaps strategies as I learn limitations along the way.

## Changes to Implementation
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

## Future Implementation Possibilities
- A basic local build using gradle or maven
- Checkstyle file with rules
- Logging for service classes to make them useful and justifiable
- Visualize the session using an /admin/session view to see attributes stored
- Enforce session cleanliness by using a business level CustomerSession object embedded in the Http session.

## Student Project Ideas from here
- Re-architect the /admin pages to use MVVM patterns.
- Implement an AJAX call for andToCart and/or updateCart (including updating header).
- Implement client and server-side double-ordering protections

# Current Tutorial Guide


# Proposed Tutorial Guide

This tutorial guide gives an introduction to the structure of a web application 
and how to build a small site like SimpleAffableBean.

## Site Requirements and Scope
## Setting up the Development Environment
## Visual Design
### Design your homepage and checkout pages (using templates)
## Database Design
### Introduce the Layered Model for a web application (View, Controller, Business Logic)
## Integrate View and Controller Layers: Homepage
### Establish your JSP folders
### Split out your header and footer as fragments
### Utilize the ${p} MVVM pattern
### Implement your first Controller and ViewModel for your Homepage
## The Business Logic Layer
### The Dao Pattern
### The Service Pattern
### Business Integration Testing
### Implement CategoryDao methods given ProductDao methods
### Pass unit tests for those

