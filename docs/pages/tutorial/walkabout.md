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

* src/main/java: this folder contains all database scripts (/db) Java source code for the site.  This includes the 
business layer (/business), the controller classes (/controller), and the view model classes (/viewmodel). 

* src/main/webapp: this folder essentially becomes the structure of the website as it it being served by the web application server (Tomcat).
The main deployment descriptor for the web server to read is in /WEB-INF/web.xml, and the view files are in the /WEB-INF/jsp folder.

* src/test: this folder contains unit tests and integration tests for the business logic layer.  
Currently it uses junit5 as the testing platform.

* src/qa: this folder contains quality assurance tests that run against a running website.
Currently it uses junit5 as the testing platform.


## Walkabout Links 
<ul>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/database">Database</a>: how are the database files used?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/business">Business</a>: how and why are the business files organized?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/controller">Controller</a>: what are controllers and view models?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/view">View</a>: let's walk through some JSP template files</li>
</ul>
