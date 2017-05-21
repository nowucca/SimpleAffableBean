---
layout: page-fullwidth
title: "Setting Up Your Development Environment"
subheadline: "The Simple Affable Bean Tutorial"
teaser: ""
permalink: /tutorial/setting-up-development-environments
---

In this section we present a list of development tools you will need to build and run the Simple Affable Bean web application.

Most web developers establish a computer or virtual machine as a development area for a project.


## Install General Standard Tools
Let's review some of the standard tools we are going to expect.  These tools are installed in standard places on most machines
and are generally useful for more than one project at a time.

### Java 

* Install [Java Development Kit version 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html): The Java programming language and toolset are required 
 for Simple Affable BEan development.  For web application development on your system, it's useful to have the "Developer" "JDK"
 version of the toolset, rather than simply the "JRE" runtime engine.  Simple Affable Bean requires at least Java 8.  Other older versions are deprecated.
 
 You will know java is successfully installed when you can open a terminal and see:
{% highlight bash %}
$ java -version
java version "1.8.0_77"
Java(TM) SE Runtime Environment (build 1.8.0_77-b03)
Java HotSpot(TM) 64-Bit Server VM (build 25.77-b03, mixed mode)
{% endhighlight %}

### A Browser 

* Install a Browser: You will need a web browser to view the Simple Affable Bean application.  
For development, a good choice is [Google Chrome](https://www.google.com/chrome/browser/desktop/index.html) because it has interesting and powerful 
[built-in development tools](https://developer.chrome.com/devtools).

### A Database

* Install MySQL database and workbench: This is a relational database system and workbench administration tool that is used
by many websites today.  You will need to install the database system itself and the workbench.  The instructions for installation are very platform-specific.
Videos are available on Lynda.com for ["Installing MySQL on Windows"](https://www.lynda.com/Apache-tutorials/Installing-MySQL-Windows/362875/369422-4.html) and ["Installing MySQL on Mac"](https://www.lynda.com/Apache-tutorials/Installing-MySQL-Mac-OS-X/362875/369428-4.html)

You will know when MySQL is successfully installed when you can restart your computer, open a terminal can login as MySQL root user to a mysql prompt:
{% highlight bash %}
$ mysql.server start
Starting MySQL
 SUCCESS!
 
$ mysql -u root -p
<enter you mysql root password>
mysql>
{% endhighlight %}

Installing and Running the MySQL workbench should also let you be able to create databases and users and connect to them locally.

#### Configure the Simple Affable Bean Database using MySQL  

This process makes a database, user and password for the Simple Affable Bean database using MySQL.
This description does this through MySQL command prompt. You can use the MySQL workbench also, but I am not describing that here.

{% highlight bash %}
$ mysql -u root -p
Enter your mysql root password
mysql> create database simpleaffablebean;
mysql> create user 'simpleaffablebean'@'localhost' identified by 'simpleaffablebean';
mysql> grant all privileges on simpleaffablebean.* to 'simpleaffablebean'@'localhost';
{% endhighlight %}


## Install Simple Affable Bean Tools
 
For the Simple Affable Bean e-commerce store, let's first choose a folder on our computer to do Simple Affable Bean 
project development within.  For example, you may choose C:\users\username\SimpleAffableBeanProject or 
/home/username/SimpleAffableBeanProject on Linux.  

Whatever folder you choose, let's refer to that folder as **$SAB_HOME** for the purposes of this tutorial.

### A Web Application Server

* Install the Tomcat web application server underneath **$SAB_HOME**: This application server is a Java program that is used to run web applications, such as the Simple Affable Bean web application.
Please install the [latest Tomcat 8.5 version](http://tomcat.apache.org/download-80.cgi).  The download is a .zip or .tar.gz file 
that can simply be unpacked inside **$SAB_HOME**.  Installation simply involves unpacking the file.

You will know when you are finished installing Tomcat when you can see:
{% highlight bash %}
$ cd $SAB_HOME  #<-- wherever that is that you chose.
$ tree
.
├── apache-tomcat-8.5.15  #<-- this is CATALINA_HOME
│   ├── LICENSE
│   ├── NOTICE
│   ├── RELEASE-NOTES
│   ├── RUNNING.txt
│   ├── bin
{% endhighlight %}


### Configure Your Web Application Server

#### Configure SSL and Tomcat 

By default the Tomcat appliation server does not support TLS (secure) connections.
Some configuration of a temporary keystore is required for development purposes.  The process is described on
the [Tomcat SSL-HOWTO page](https://tomcat.apache.org/tomcat-8.5-doc/ssl-howto.html) and another walkthrough 
is [available here](https://www.mkyong.com/tomcat/how-to-configure-tomcat-to-support-ssl-or-https/).

I'll walk through the process here briefly as well.

* Find the Java keytool in Windows or Mac OSX. Navigate to that directory and run:

{% highlight bash %}
$ keytool -genkey -alias tomcat -keyalg RSA -keystore $SAB_HOME/apache-tomcat-8.5.15/conf/localhost-rsa.jks
{% endhighlight %}

* This will (probably) create a localhost-rsa.jks keystore file in your **$SAB_HOME**/apache-tomcat-8.5.15/conf/ 
directory.  

* Uncomment the following code in your **$SAB_HOME**/apache-tomcat-8.5.15/conf/server.xml file:

{% highlight xml %}
<Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
           maxThreads="150" SSLEnabled="true">
    <SSLHostConfig>
        <Certificate certificateKeystoreFile="conf/localhost-rsa.jks"
                     type="RSA" />
    </SSLHostConfig>
</Connector>
{% endhighlight %}

#### Configure Administration Users

For the Simple Affable Bean admin console to work, you do in fact need to edit **$SAB_HOME**/apache-tomcat-8.5.15/conf/tomcat-users.xml.

Down the bottom, let us minimally modify the tomcat user and add "simpleAffableBeanAdmin" as a role.
 
{% highlight xml %}
<role rolename="tomcat"/>
  <role rolename="role1"/>
  <role rolename="manager-gui"/>
  <role rolename="simpleAffableBeanAdmin"/>
  <user username="tomcat" password="tomcat" roles="manager-gui,tomcat,simpleAffableBeanAdmin"/>
{% endhighlight %}

----

Now it is time to download, build and deploy the Simple Affable Bean web application into this configured webserver,
using your browser, the web application server and the MySQL database.

Please proceed to the next tutorial section: [Simple Affable Bean Tutorial: Building and Deploying]({{site.url}}{{site.baseurl}}/tutorial/building-and-deploying-sab)

