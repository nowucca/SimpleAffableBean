---
layout: page-fullwidth
title: "Building and Deploying the Simple Affable Bean website"
subheadline: "The Simple Affable Bean Tutorial"
teaser: ""
permalink: /tutorial/building-and-deploying-sab
---

The goal of this part of the tutorial is to get the website built and running locally inside your Tomcat installation,
in such a way that you can browse to it in your web browser, and in such a way that it is using your MySql simpleaffablebean 
database.

To start with, we assume you have the following:

* A **$SAB_HOME** folder with Tomcat inside it.
* All necessary development tools installed and configured

There are a few steps to running the Simple Affable Bean application we will walk through below.

# Obtain the Simple Affable Bean Source code
 
There are two main ways to download the Simple Affable BEan source code.

1. Download the most recent release as a zip or a tar file from the [latest release on GitHub](https://github.com/nowucca/SimpleAffableBean/releases/latest/), or 

1. Clone the code from Github using git

The Simple Affable Bean source code is available [on Github](https://github.com/nowucca/SimpleAffableBean)
and can be cloned using the following commands:
{% highlight bash %}
$ cd $SAB_HOME
$ git clone https://github.com/nowucca/SimpleAffableBean.git
{% endhighlight %}

Whether you are downloading or cloning, please unpack this into **$SAB_HOME**, so you end up having a tree structure like:

{% highlight bash %}
SAB_HOME
├── apache-tomcat-8.5.15  #<-- this is CATALINA_HOME
│   ....
├── SimpleAffableBean  
|   ├── LICENCE.affablebean.txt
|   ├── LICENSE
|   ├── README.md
|   ├── build.gradle
|   ├── docs
│   ....
{% endhighlight %}


# Build and Populate the simpleaffablebean database

The database scripts to build and populate sample data into your mysql database are in $SAB_HOME/SimpleAffableBean/src/main/db.

## Build the Database 

Having established the username, password and database named 'simpleaffablebean' when installing MySQL, we can now simply create 
the database using the command line:

{% highlight bash %}
$ mysql -p -u simpleaffablebean simpleaffablebean < schemaCreation.sql
Enter password:<type simpleaffablebean>
{% endhighlight %}

To verify that the schema has been created, use the mysql command line shell as follows:

{% highlight bash %}
$ mysql -u simpleaffablebean -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
....
mysql> show databases;

+--------------------+
| Database           |
+--------------------+
| information_schema |
| simpleaffablebean  |
+--------------------+
2 rows in set (0.00 sec)

mysql> use simpleaffablebean
Reading table information for completion of table and column names


You can turn off this feature to get a quicker startup with -A

Database changed
mysql> show tables;
+-----------------------------+
| Tables_in_simpleaffablebean |
+-----------------------------+
| category                    |
| customer                    |
| customer_order              |
| customer_order_line_item    |
| product                     |
+-----------------------------+
5 rows in set (0.00 sec)
{% endhighlight %}

## Populate The Database

We can now simply add populate sample data into it using the command line:

{% highlight bash %}
$ mysql -p -u simpleaffablebean simpleaffablebean < sampleData.sql
Enter password:<type simpleaffablebean>
{% endhighlight %}

To verify the sample data is present we can have a look.

{% highlight bash %}
$ mysql -p -u simpleaffablebean simpleaffablebean
Enter password:<type simpleaffablebean>
....
mysql> select * from product;
+------------+-------------+----------------------+-------+-------------------------------------------+---------------------+
| product_id | category_id | name                 | price | description                               | last_update         |
+------------+-------------+----------------------+-------+-------------------------------------------+---------------------+
|          1 |           1 | milk                 |   170 | semi skimmed (1L)                         | 2017-05-21 16:13:40 |
|          2 |           1 | cheese               |   239 | mild cheddar (330g)                       | 2017-05-21 16:13:40 |
|          3 |           1 | butter               |   109 | unsalted (250g)                           | 2017-05-21 16:13:40 |
|          4 |           1 | free range eggs      |   176 | medium-sized (6 eggs)                     | 2017-05-21 16:13:40 |
|          5 |           2 | organic meat patties |   229 | rolled in fresh herbs<br>2 patties (250g) | 2017-05-21 16:13:40 |
|          6 |           2 | parma ham            |   349 | matured, organic (70g)                    | 2017-05-21 16:13:40 |
|          7 |           2 | chicken leg          |   259 | free range (250g)                         | 2017-05-21 16:13:40 |
|          8 |           2 | sausages             |   355 | reduced fat, pork<br>3 sausages (350g)    | 2017-05-21 16:13:40 |
|          9 |           3 | sunflower seed loaf  |   189 | 600g                                      | 2017-05-21 16:13:40 |
|         10 |           3 | sesame seed bagel    |   119 | 4 bagels                                  | 2017-05-21 16:13:40 |
|         11 |           3 | pumpkin seed bun     |   115 | 4 buns                                    | 2017-05-21 16:13:40 |
|         12 |           3 | chocolate cookies    |   239 | contain peanuts<br>(3 cookies)            | 2017-05-21 16:13:40 |
|         13 |           4 | corn on the cob      |   159 | 2 pieces                                  | 2017-05-21 16:13:40 |
|         14 |           4 | red currants         |   249 | 150g                                      | 2017-05-21 16:13:40 |
|         15 |           4 | broccoli             |   129 | 500g                                      | 2017-05-21 16:13:40 |
|         16 |           4 | seedless watermelon  |   149 | 250g                                      | 2017-05-21 16:13:40 |
|         17 |           1 | milk                 |   170 | semi skimmed (1L)                         | 2017-05-21 16:14:44 |
|         18 |           1 | cheese               |   239 | mild cheddar (330g)                       | 2017-05-21 16:14:44 |
|         19 |           1 | butter               |   109 | unsalted (250g)                           | 2017-05-21 16:14:44 |
|         20 |           1 | free range eggs      |   176 | medium-sized (6 eggs)                     | 2017-05-21 16:14:44 |
|         21 |           2 | organic meat patties |   229 | rolled in fresh herbs<br>2 patties (250g) | 2017-05-21 16:14:44 |
|         22 |           2 | parma ham            |   349 | matured, organic (70g)                    | 2017-05-21 16:14:44 |
|         23 |           2 | chicken leg          |   259 | free range (250g)                         | 2017-05-21 16:14:44 |
|         24 |           2 | sausages             |   355 | reduced fat, pork<br>3 sausages (350g)    | 2017-05-21 16:14:44 |
|         25 |           3 | sunflower seed loaf  |   189 | 600g                                      | 2017-05-21 16:14:44 |
|         26 |           3 | sesame seed bagel    |   119 | 4 bagels                                  | 2017-05-21 16:14:44 |
|         27 |           3 | pumpkin seed bun     |   115 | 4 buns                                    | 2017-05-21 16:14:44 |
|         28 |           3 | chocolate cookies    |   239 | contain peanuts<br>(3 cookies)            | 2017-05-21 16:14:44 |
|         29 |           4 | corn on the cob      |   159 | 2 pieces                                  | 2017-05-21 16:14:44 |
|         30 |           4 | red currants         |   249 | 150g                                      | 2017-05-21 16:14:44 |
|         31 |           4 | broccoli             |   129 | 500g                                      | 2017-05-21 16:14:44 |
|         32 |           4 | seedless watermelon  |   149 | 250g                                      | 2017-05-21 16:14:44 |
+------------+-------------+----------------------+-------+-------------------------------------------+---------------------+
32 rows in set (0.00 sec)
{% endhighlight %}

- Setting up the MySQL database
   - user, password

# Building the Web Application Archive (WAR) 

When you build a Java web application, the output is a single "web application archive" (nicknamed: WAR) file.

For the Simple Affable Bean project, this file will be placed into $SAB_HOME/SimpleAffableBean/build/lib/SimpleAffableBean.war once the build procuedure is complete.

To start the build procedure, change directory to $SAB_HOME/SimpleAffableBean.  This is where the application source code lives.

We will be using the gradle build system, but it will be automatically downloaded if you have Java 8 installed.
For more information on gradle and starting a build, see [this quick overview](https://spring.io/guides/gs/gradle/)
Start a build by typing:

{% highlight bash %}
$ ./gradlew  build
Starting a Gradle Daemon, 1 incompatible Daemon could not be reused, use --status for details
:compileJava
:processResources NO-SOURCE
:classes
:war
:assemble
:licenseDb UP-TO-DATE
:licenseWeb UP-TO-DATE
:licenseMain UP-TO-DATE
:licenseQa UP-TO-DATE
:licenseTest UP-TO-DATE
:license UP-TO-DATE
:compileTestJava
:processTestResources NO-SOURCE
:testClasses
:junitPlatformTest

Test run finished after 511 ms
[         3 containers found      ]
[         0 containers skipped    ]
[         3 containers started    ]
[         0 containers aborted    ]
[         3 containers successful ]
[         0 containers failed     ]
[         2 tests found           ]
[         0 tests skipped         ]
[         2 tests started         ]
[         0 tests aborted         ]
[         2 tests successful      ]
[         0 tests failed          ]

:test SKIPPED
:check UP-TO-DATE
:build

BUILD SUCCESSFUL

Total time: 8.712 secs
{% endhighlight %}

# Running the WAR Inside Tomcat

Having built the web application, all that remains is to deploy the web application into Tomcat.

This simply means copying the WAR file into the right place into Tomcat, then starting Tomcat running.
 
{% highlight bash %}
$ cp build/libs/SimpleAffableBean.war ../apache-tomcat-8.5.15/webapps/
$ cd ../apache-tomcat-8.5.15/bin
$ ./catalina.sh start
Using CATALINA_BASE:   /$SAB_HOME/apache-tomcat-8.5.15
Using CATALINA_HOME:   /$SAB_HOME/apache-tomcat-8.5.15
Using CATALINA_TMPDIR: /$SAB_HOME/apache-tomcat-8.5.15/temp
Using JRE_HOME:        /Library/Java/JavaVirtualMachines/jdk1.8.0_77.jdk/Contents/Home
Using CLASSPATH:       /$SAB_HOME/apache-tomcat-8.5.15/bin/bootstrap.jar:/$SAB_HOME/apache-tomcat-8.5.15/bin/tomcat-juli.jar
Tomcat started.
$ cd ../logs 
$ tail catalina.out
# Look for something like: 21-May-2017 16:39:37.081 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployWAR Deployment of web application archive /$SAB_HOME/apache-tomcat-8.5.9/webapps/SimpleAffableBean.war has finished in 1,682 ms
{% endhighlight %}

You can verify your installation is successful by browsing to:  http://127.0.0.1:8080/SimpleAffableBean/

You should see the home page: ![building-success-homepage]({{site.url}}{{site.baseurl}}/images/building-success-homepage.png){:class="img-responsive"}

Since you have made it this far, congratulations on setting up and running the project!

----

So, we can build and deploy the existing website.

But, what are the technologies used to make this website?

How is the code structured?

What is the reasoning behind these choices?

How well will the site scale to tens, hundreds, thousands and millions of customers?

The next sections of the tutorial are a code walkabout.  As we look though the code, we will point out not only
what the code does, but why it is structured that way.  

----

The tutorial stops here for now.
The code walkabout pages will be written shortly.
