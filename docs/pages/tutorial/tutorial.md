---
layout: page
title: "Tutorial"
subheadline: "The Simple Affable Bean Tutorial"
teaser: ""
permalink: /tutorial/
---

- Introduction to Website Architecture
  - Content from https://netbeans.org/kb/docs/javaee/ecommerce/intro.html#whatEcommerce
  - Content from the first parts of: https://netbeans.org/kb/docs/javaee/ecommerce/design.html
   - Evernote post: https://www.evernote.com/l/ACnatacTJQJDhZYpHKvMHeTsELbyXM2_jkw
  - AffableBean content: https://netbeans.org/kb/docs/javaee/ecommerce/design.html

  - Principles
    * Avoiding vendor lockin
    * Accepting Page-driven design AND MVC
    * Flexibility for expected changes
    * Testing for assurance only

  - Have an architecture diagram showing MVC or browser/container/database blocks.
  
- Simple Affable Bean Site Requirements

- Setting up the Development Environment
  - Objectives: Install necessary development tools
     - $SAB_HOME
     - Install Java
     - Install Gradle
     - Install Chrome browser
     - Install MySQL (platform-specific)
     - Install Tomcat in $SAB_HOME/containers/apache-tomcat-8.5.13
     - Follow SSL setup instructions for setting up tomcat.
     - Add simpleAffableBean admin user in tomcat-users.xml

  - Summary: [Building Block diagram: {MySQL, Tomcat}  {Java, Gradle, Chrome}
    Tie back in to the architecture diagram.


- Getting started building and deploying
  - Setting up the MySQL database
   - user, password

  -  Building SAB
    - Gradle howto
    - what's going on when we run tests

  - Deploying SAB into Tomcat
    - Describe logging output
    - Cover re-deploying automatically by copying over WAR file
    - Changing settings.gradle to change WAR name and context path automatically
    - Hitting the test.jsp to test database connectivity

  - Running qa tests

- Code Walkabout

  - Database Layer

  - Business Layer
      - business layer is a separate compilable unit
      - services and daos

  - Business Testing
  
  - Controller and View Model Layer
      - JSP is the template language
      - ViewModel simplifies JSPs making them readable

  - View Layer
  - Acceptance Testing 
