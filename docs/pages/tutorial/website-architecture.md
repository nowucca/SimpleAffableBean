---
layout: page-tutorial
title: "Introduction to Website Architecture"
subheadline: "The Simple Affable Bean Tutorial"
teaser: ""
permalink: /tutorial/website-architecture
---
Before we start coding, let's examine the ways in which one can architect the project. 
Specifically, we need to outline the responsibilities among functional components, 
and determine how they will interact with each other.


## What kind of site are we developing at a high level?

This tutorial focuses on business-to-customer (B2C) e-commerce, and applies the typical scenario of a small retail store seeking to create a website enabling customers to shop online. Software that accommodates a B2C scenario generally consists of two components:

1. Store Front: The website that is accessed by customers, enabling them to purchase goods over the Internet. Data from the store catalog is typically maintained in a database, and pages requiring this data are generated dynamically.
2. Administration Console: A password-protected area that is accessed over a secure connection by store staff for purposes of online management. This typically involves CRUD (create read update delete) access to the store catalog, management of discounts, shipping and payment options, and review of customer orders.


See also: [What is an e-Commerce website?](https://netbeans.org/kb/docs/javaee/ecommerce/intro.html#whatEcommerce)

From this high-level review, and some of the customer requirements, we can can determine that building a Java-based web application 
will match the requirements (as would many other web application technologies, of course).

## The Architecture for Simple Affable Bean

At a high level there are three components to the final operating website: 

* the customers running browsers

* the web server, which stores and reads data into and out of

* a database for persistent storage

![website architecture overview]({{site.url}}{{site.baseurl}}/images/website-architecture-overview.png){:class="img-responsive"}

Customers using browsers rely on Internet services (such as DNS) to find your web server to 
serve pages for your site.  When the user request arrives at the web server, it is processed, 
some data may be stored in a database, and a response, usually a web page, is returned to the customer browser.
 
It is important to realize that the Internet, and its protocols, and even the hardware infrastructure at your web hosting data center 
are all very important factors to be aware of when operating a live website.  Why? Because when things fail, it's important to have a model 
of how the basic protocols work to narrow down potential causes.

However, for this tutorial, the focus is going to be on understanding the structure of the web application itself,
and understanding the nature of a web application server/container in which applications are deployed.

At this stage, please read [the original architecture section](https://netbeans.org/kb/docs/javaee/ecommerce/design.html#architecture) of the original AffableBean project.

###  Web Application Organization
 
Let's consider the architecture of the running web application inside the web application server.

There will be four architectural layers into which code is organized.

* The View Layer: The view layer contains page templates, that describe how data from the web application is to be sent back to the client.
For Simple Affable Bean, we have continued to use the Java Server Pages (JSP) technology.  The files in the view layer are located in src/main/webapp/WEB-INF/jsp.
In the Simple Affable Bean application, there is one jsp file each page in the application.  This makes maintenance of the application very much easier.

* The View Model Layer: The view model layer builds a Java object for each page, containing all the information needed for the page.
 The files in the view model layer are located in src/main/java/viewmodel.  The intuition here is that even if the template files in the view layer are updated witha  new "look and feel", the view model files 
 represent what pieces of information from the web application are necessary to serve each page. 

* The Controller Layer: The controller layer receives requests from the clients and is responsible for using the business logic layer to gather data,
   to assemble to ViewModel object for the page requested, and to send the response through the view layer back out to the clients.
   The files in the controller layer are located in src/main/java/controller.  The intuition here is that controllers receive the requests and
   manage handling the responses - they are the "traffic cops" co-ordinating the flow of information to and from browsers.
   
* The Business Logic Layer: The business logic layer comprises the set of necessary services to perform site functions.
  For example, all CRUD (create, read, update, delete) operations on objects in the system (such a s categories, shopping carts, customers)
  will be provided by the business logic later. The files in the business logic later are located in src/main/java/business.  These
  services together form an "army of singletons" which are under siege from all the customer requests and must respond to each request 
  as fast as possible.

![website-architecture-introduction]({{site.url}}{{site.baseurl}}/images/website-architecture-introduction.png){:class="img-responsive"}

### Page Driven Design 

In web development, it is important to consider the needs not only of the customers, but also of future engineers maintaining your website.
To this end, when starting the Simple Affable Bean grocery store site, let's standardize on names for our pages, and use those names
throughout the code base consistently.

We are going to use these standard names for our pages.

* Home page 

* Category Page

* Cart Page

* Checkout Page

* Confirmation Page

Each of these pages will have a corresponding view template (JSP file), controller (a Java Servlet) and a view model (a Java class).

![website-architecture-pages-files-correspond]({{site.url}}{{site.baseurl}}/images/website-architecture-pages-files-correspond.png){:class="img-responsive"}

The reason this is called "page driven" design is that we are not planning to build "speculative" other entities - we are driven by what is on the page.
This yields a website that is simpler to understand that has less future maintenance impediments.

## Principles of Website Architecture

There are just a couple of "principles" one could apply to businesses in a growth/startup phase like the Simple Affable Bean grocery store.
Both principles could be summarized as promoting flexibility to evolve the website in the future.

* Using Page-driven design AND MVC: this will make it much easier for general and specialist engineers to maintain and
 evolve the website into the future.  Page-driven design ensures entities are named consistently, and only features that are
 needed for the current phase of the project are being built.

* Avoiding vendor lock-in: If there is any choice, choose technologies that do not involve large expensive re-writes of the system
 if you need to change them later.  For example, we are choosing to implement business logic inside our application, rather than
 implementing code in the database, which would tie us much closer to the database in the longer term.


----
Please proceed to the next tutorial section: [Simple Affable Bean Tutorial: Setting Up Your Development Environment]({{site.url}}{{site.baseurl}}/tutorial/setting-up-development-environments)
