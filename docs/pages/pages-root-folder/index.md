---
#
# Use the widgets beneath and the content will be
# inserted automagically in the webpage. To make
# this work, you have to use › layout: frontpage
#
layout: frontpage
header:
  #image_fullwidth: you-can-delete-me-header.png
#widget1:
#  title: "Widget 1"
#  url: 'http://domain.de/must-be-absolut-url-like-this-one/'
#  image: 'http://dummyimage.com/302x183/334d5c/efc94c.png&text=Placeholder'
#  text: ''
#widget2:
#  title: "Widget 2"
#  url: 'http://domain.de/must-be-absolut-url-like-this-one/'
#  image: 'http://dummyimage.com/302x183/334d5c/efc94c.png&text=Placeholder'
#  text: ''
#widget3:
#  title: "Widget 3"
#  url: 'http://domain.de/must-be-absolut-url-like-this-one/'
#  image: 'http://dummyimage.com/302x183/334d5c/efc94c.png&text=Placeholder'
#  text: ''
#
# Use the call for action to show a button on the frontpage
#
# To make internal links, just use a permalink like this
# url: /getting-started/
#
# To style the button in different colors, use no value
# to use the main color or success, alert or secondary.
# To change colors see sass/_01_settings_colors.scss
#
#callforaction:
#  url: https://tinyletter.com/feeling-responsive
#  text: Inform me about new updates and features ›
#  style: alert
permalink: /index.html
---

# Welcome to SimpleAffableBean!  

This project is a coding and tutorial website for development of e-commerce websites using Java technology.
The source code for the project is available at [the GitHub site](http://www.github.com/nowucca/SimpleAffableBean).

This software is an adaptation from the [Affable Bean project](https://netbeans.org/kb/docs/javaee/ecommerce/intro.html), moving away from EJB and using a simpler modern technology stack.
That original AffableBean project was released under the 2-clause Berkeley licence by Sun and then Oracle.
Please see the [LICENCE.affablebean.txt](https://raw.githubusercontent.com/nowucca/SimpleAffableBean/master/LICENSE.affablebean.txt) file for compliance and licensing concerns for the original project.
This project is being released under the "new" [BSD 3-clause license](https://raw.githubusercontent.com/nowucca/SimpleAffableBean/master/LICENSE).


This site hosts a tutorial, follow-on design topics,  and a blog: all helping to describe and understand how to build an e-commerce web application.

The tutorial starts with an overview of website architecture, and requirements for the SimpleAffableBEan grocery store.
Next is how to set up the development environment, and then download and deploy the web application.  The tutorial then presents a
code "walkabout", where the key elements of the structure of the website ("what" and "where") are explained with code samples from the project.

The follow-on design topics help explain the reasons ("why") driving the structure of the code.

The blog area will capture project improvements and announcements over time.

Feel free to look around!


## Prerequisites

To follow the tutorial and explanations, we assume a working knowledge of the basics of Java programming.


# Why Rewrite AffableBean?

In my experience, hardly anyone uses the EJB stack as it was meant to be used, with session and entity beans, data sources, services and the rest of it.  
I've found that some of the EJB technology have too much abstraction.  This means the frameworks are seductively easy to use.  
However they tend to lead to:

* code that is difficult to reason about and 
* code that is less amenable to change (i.e. the developer has diminished ability to make changes easily when business context changes).

So, when starting the rewrite of the original project, the key goals were:

1. to require a servlet container rather than an EJB container,
2. to maintain all functionality in the original project.  
3. to avoid the use of high-level frameworks to better demonstrate the basic building blocks of web application development.

The starting point for this project was the complete [Affable beans project](https://netbeans.org/kb/docs/javaee/ecommerce/intro.html) 
(download the [complete ZIP file](https://netbeans.org/projects/samples/downloads/download/Samples/JavaEE/ecommerce/AffableBean_complete.zip)).

While rewriting the code, it became apparent that there were many improvements to make.  (In fact, there are always more improvements to make, even now!).
We decided to focus on a few major concerns: having a better code architecture (separating business logic, database logic from controller logic),
using a low-level no-framework database layer, and using the View Model design pattern to simplify view templates.  

Please feel free to look at the commit history for [the GitHub site](http://www.github.com/nowucca/SimpleAffableBean)
to see the approaches taken to achieve the rewrite.

> **The rewrite is now complete - feature-parity with AffableBean has been achieved.**
The application now runs inside Tomcat, has clear separation between view, business and database layers, 
and uses fewer frameworks.

### Summary of Changes to the Original AffableBean Implementation
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


# SimpleAffableBean Blog

