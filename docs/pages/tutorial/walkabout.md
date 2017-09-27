---
layout: page-tutorial
title: "Code Walkabout"
subheadline: "The Simple Affable Bean Tutorial"
teaser: ""
permalink: /tutorial/walkabout/
---
<div class="row t30">

<div class="medium-8 columns{% if page.sidebar == NULL %} medium-offset-2 end{% endif %}{% if page.sidebar == "left" %} medium-push-4{% endif %}" markdown="1">
<div class="panel radius" markdown="1">
{% include tutorial_toc %}
**Tutorial Page Contents**
{: #toc }
*  TOC
{:toc}
</div>
</div><!-- /.medium-4.columns -->



<div class="medium-8 medium-pull-4 columns" markdown="1">



</div><!-- /.medium-8.columns -->
</div><!-- /.row -->


## Code Layout

The code is organized into the following major module/folder structures.

* src/main/java: this folder contains

  * all database scripts (/db) Java source code for the site.

  * the business layer (/business),

  * the controller classes (/controller), and

  * the view model classes (/viewmodel).

* src/main/webapp: this folder essentially becomes the structure of the website as it it being served by the web application server (Tomcat).
The main deployment descriptor for the web server to read is in /WEB-INF/web.xml, and the view files are in the /WEB-INF/jsp folder.

* src/test: this folder contains unit tests and integration tests for the business logic layer.
Currently it uses junit5 as the testing platform.

* src/qa: this folder contains quality assurance tests that run against a running website.
Currently it uses junit5 as the testing platform.

## Website Architecture - Review


###  Web Application Organization

Let's consider the architecture of the running web application inside the web application server.

There will be five architectural layers into which code is organized.

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

* The Data Access Layer: The data for our website is stored persistently in a database.  The code for reading and writing the stored
data is in the data access layer.  The structure and sample data for the database are provided in src/main/db.  These files are
used during business logic testing to reset the database into a known state between tests.

I've captured the geography of these layers in the following diagram.

![website-architecture-introduction]({{site.url}}{{site.baseurl}}/images/website-architecture-introduction.png){:class="img-responsive"}

When a client browser issues a request, the Tomcat web server container forwards the request to a servlet in the controller layer.
For example, a request to http://<yourhost:8080>/SimpleAffableBean/category will be received by the CategoryServlet in the src/main/java/controller package.

From there, the controller servlet is responsible for two tasks: first, build the "view model" object for the page, and second,
to send the "view model" object to a page template.

In our example, we will first be constructing a CategoryViewModel object (source located in the src/main/java/viewmodel package).
Second, the Category servlet will then forward control to the category page template (in this case it will be the category.jsp template in src/main/webapp/WEB-INF/jsp).
The Tomcat container will then send the constructed page to the client browser and the request is complete.

So how does one construct a CategoryViewModel? The CategoryViewModel is responsible for gathering all the data/model objects necessary for display of the page.  For example,
we will need to know the default category to display, and a list of all available product categories to render the page.
The CategoryViewModel constructor uses the business logic services via a Singleton object called "ApplicationContext" that
houses the DAO classes.  In turn the DAO classes serve as factories for reading data (model objects) from the database.

That wraps up the handling of a single request through the architecture of the SimpleAffableBean web application.

In the next few walkabout pages, we will dive into code and look at specific files to help demonstrate how and why the application
is structured in this way.



## Walkabout Links
<ul>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/database">Database</a>: how are the database files used?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/business">Business</a>: how and why are the business files organized?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/controller">Controller</a>: what are controllers and view models?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/view">View</a>: let's walk through some JSP template files</li>
</ul>
