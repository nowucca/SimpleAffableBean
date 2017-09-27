---
layout: page-tutorial
title: "Code Walkabout: Database Layer"
subheadline: "The Simple Affable Bean Tutorial"
teaser: ""
permalink: /tutorial/walkabout/database
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


## What is the Database layer?

The database files are located in src/main/db.  These are mysql script files.

schemaCreation.sql contains commands to drop and re-create a fresh schema for the website.
sampleData.sql populates the tables in the schema with sample categories and products.

Let's go ahead and execute them in that order to create a fresh database with sample data.

{% highlight bash %}
$ mysql -u root -p < $SAB_HOME/SimpleAffableBean/src/main/db/schemaCreation.sql
....
$ mysql -u root -p < $SAB_HOME/SimpleAffableBean/src/main/db/sampleData.sql
....
{% endhighlight %}

## src/main/db/schemaCreation.sql

### Preamble
This file starts by remembering and resetting some MySQL session parameters so we can restore them after
the scripts is run.  The details of these are explained in the [MySQL workbench FAQ](https://dev.mysql.com/doc/workbench/en/workbench-faq.html).
In short, while we are buidling the schema, we do not need to check unqiue contraints, foreign key table existence and can operate in a stricter traditional mode.

{% highlight sql %}
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';
{% endhighlight %}

At the end of the script the original settings are restored.
{% highlight sql %}
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
{% endhighlight %}

### Drop and Create the Database

The following code to drop the entire schema (also called "database" in MySQL) allows this script to work ehther or not the database
already exists.  The first line simply drops the entire database - so be CAREFUL running this script: all data in the database
will be lost before the database is re-created.

For MySQL, yu can set the default character set and the default "collation" (meaning how strings are compared).
For SAB, we choose utf8 character sets for both, since it is going to be an internationalized product.

{% highlight sql %}
DROP SCHEMA IF EXISTS `simpleaffablebean` ;
CREATE SCHEMA IF NOT EXISTS `simpleaffablebean` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `simpleaffablebean` ;
{% endhighlight %}

### Create tables and constraints

In this walk through we will focus on the product and category tables, that are related witha  foreign key constraint.
The customer, customer_order and customer_order line item tables are similarly defined.

Once developers are used to modlling tables in SQL, and to the [practice of normalizing tables](http://holowczak.com/database-normalization/4/#normalization) for the objects being modeled,
 folks are generally reasonably comfortable reading and working directly with SQL files without using modeling tools.


#### The Category Table

A category can be thought of as the name of an aisle in a grocery store.  For example, "meats" could be a category.
We have chosen to model this with an auto-incrementing primary "surrogate" key, and the name of the aisle.
Numeric auto-incrementing surrogate keys are often used to avoid duplicating the other data (in this case the name  string)  all over the database in other tables.

MySQL lets us choose a [storage engine](https://stackoverflow.com/questions/4233816/what-are-mysql-database-engines/4233836)  with which to store the data in each table.  We choose the InnoDB engine for all tables
in this project since it fully supports transactions and constraint checking, and is used in many online transactional websites.
  Other engines have other advatntages for different types of data.

It's often helpful to put a comment in the database so that people using tools to view the database later can see your intent.

{% highlight sql %}

-- -----------------------------------------------------
-- Table `simpleaffablebean`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `simpleaffablebean`.`category` ;

CREATE  TABLE IF NOT EXISTS `simpleaffablebean`.`category` (
  `category_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`category_id`) )
ENGINE = InnoDB
COMMENT = 'contains product categories, e.g., dairy, meats, etc.';
{% endhighlight %}

#### The Product Table

A product belongs to a category.  For example "bacon" is a product in the "meats" category.

This table models a product with a surrogate primary key aas above for categories.

The price of a category is modelled as an integer here - this is a simplification but the intent is that we model prices
as integer-valued base units (in USD, this is pennies, in Euros it is cents).  Complications such as different prices
for different companies, price history, price changes are out of scope for this simple affable bean project.

Here we also see that one can use automatically updating columns such as last_update to track when modifications were made to a row.
A common pattern is to track creation and modification times for each row, along with the name of the last modifier.
This is often done for auditable changes (here it is likely due to someone being able to change price and/or description).

One final interesting thing to note here is that there is a consistency (foreign key) constraint between the product table and the category table.
The constraint requires that every product has a category that must be present in the category table. The constraint can be enforced automatically (any attempt to insert invalid data will fail).

{% highlight sql %}

-- -----------------------------------------------------
-- Table `simpleaffablebean`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `simpleaffablebean`.`product` ;

CREATE TABLE IF NOT EXISTS `simpleaffablebean`.`product` (
  `product_id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `category_id` TINYINT UNSIGNED NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `price` INT UNSIGNED NOT NULL ,

  -- Delete after description is moved to resource bundle
  `description` TINYTEXT NULL ,

  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  PRIMARY KEY (`product_id`) ,
  INDEX `fk_product_category` (`category_id` ASC) ,
  CONSTRAINT `fk_product_category`
    FOREIGN KEY (`category_id` )
    REFERENCES `simpleaffablebean`.`category` (`category_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'contains product details';
{% endhighlight %}

### The Schema Diagram

IntelliJ can generate a visual diagram of a database schema using the Data source "Show Visualization" menu by right-clicking in the Database tool view.

![sab-schema-diagram]({{site.url}}{{site.baseurl}}/images/sab-schema-diagram.png){:class="img-responsive"}

As you can see, we have five tables.

Product and category tables store information about non-customer parts of the grocery store.
We call this "meta-data" or "site-data", and it is often treated separately from customer data.
The difference the site data is shared across all customer requests
(as opposed to customer data which should be specific to each customer.  If the site-data can fit
into memory, it can be cached inside the Tomcat container and periodically refreshed.  This helps performance
because it helps avoid unnecessary read operations on the database.

The customer, customer_order and customer_order_line_item tables store customer details, and then information about
each customer's orders, including how many of each product was ordered in each order.  This again is slightly simplified because
we are assuming that the price of each ordered product does not change and can be looked up in the product table.
It would be an extension to change the schema to capture the quoted price of each
line_item with the line item in the customer_order_line_item table.

## src/main/db/sampleData.sql

This file contains SQL INSERT statements to popluate data into the database.

## Further Topics and Notes

### Relational (SQL) vs NoSQL databases

Relational databases are designed to hold relational (tabular) data, with verifiable constraints between the tables.
An object such as a customer, together with all their orders, is formed by joining tables using SQL queries using customer_id as the "join key".
Updating data is straightforward - once reasonably normalized, updates can also be expressed in terms of SQL insert/update statements.
Constraints can be declarateively expressed and enforced for each transaction.  Each transaction is atomic - it either happens or is rolled back entirely.
Provides ACID guarantees: In computer science, ACID (Atomicity, Consistency, Isolation, Durability) is a set of properties of database transactions intended to guarantee validity even in the event of errors, power failures, etc.


NoSQL databases, sometimes called key-value stores, are designed to hold potentially large values, hashed underneath keys, in separate "stores".
An object such as a customer, together with all their orders, is formed by simply looking up the value using the key
customer_id in the "customer" store.  This is assuming all orders are stored in the same row together with other customer information.
What happens over time is that customer data gets split into a separate store from customer_order data eventually as data grows.
When data is split across multiple stores, it is up to application logic to ensure that data consistency is maintained, and it is
also up to application logic to manage any transactional issues (if a multi-step transaction fails halfway through, data must be cleaned up manually).


Further reading:

   * [Eventual consistency](https://en.wikipedia.org/wiki/Eventual_consistency)

   * [CAP theorem](https://en.wikipedia.org/wiki/CAP_theorem)

   * [Ars Technica article](https://arstechnica.com/information-technology/2016/03/to-sql-or-nosql-thats-the-database-question/)


### Database design sensibilities

So sadly, most often in your career you will walk in to a mixture and mess of naming conventions in the database.
Hardly anyone follows conventions on these things over time.  But if you are in charge of creating or improving the schema for a database here's some thoughts.

### Be Consistent
The golden rule for software is be consistent whatever you do - think of the future engineer trying to follow this stuff.

Think Ahead / penser à l'avance
If you can set the default character set to UTF-8 for the database at the start. For reference, 9 months into LinkedIn, we took a downtime to do just that so we would be ready for internationalization.

### Name Limitations
Be aware that historically at least Oracle and sometime other databases limit name length of many objects (tables, indices, columns etc).  This actually drives much of the advice below.  There's always SOME limit, Oracle might be better now.

### Table Name Advice
Use plural or singular nouns for tables, but be consistent.  Given name length restrictions, singular often works. eg. "CUSTOMER" not "CUSTOMERS".

### Constraint Advice
I learned this naming scheme at LinkedIn, under some of the best database folks I know to this day.
So you might not like or agree with it, but at least it's proven scalable!

Every table can have primary key (PK), unique (U) or non-unique indexes (I) or foreign keys (FK) and triggers etc.
For the shortest and clearest names, I recommend a <table>_<type><number> scheme as follows.

Assuming a CUSTOMER table we get:

CUSTOMER_PK (customer table primary key name)
CUSTOMER_U1 (customer table unique index, use U2, U3 if needed etc)
CUSTOMER_I1 (customer table index if any, use I2, I3 if needed etc)
CUSTOMER_FK1 (customer table foreign key 1)
CUSTOMER_FK2 (customer table foreign key 2 etc etc)

Notice that the foreign key names do not worry about what they point to - it's all about the table owning the constraint.
Your database tools will draw nice arrows to show you the referent tables.

Also some people think that foreign keys hurt performance so we shoudl disable them in production.
Run, run from those ideas!  The value of a website business is ALL ABOUT the data in the database ultimately.
Always run with constraints on - it protects the integrity of your data.

### Avoiding Exotic Database Features
Avoid using triggers for anything.  This is because they are not portable across database vendors.  If you need journalling functionality (like audit trails of who touched a table when and how) consider adding it to your business logic code first before relying on the database.

Avoid using stored procedures for anything for the same reason.  A team I worked with spent 2 years refactoring away from PL/SQL in Oracle.  During that time much money was spent on Oracle licenses to keep the ship afloat - it locks your company in for no good reason.



## Walkabout Links
<ul>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/database">Database</a>: how are the database files used?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/business">Business</a>: how and why are the business files organized?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/controller">Controller</a>: what are controllers and view models?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/view">View</a>: let's walk through some JSP template files</li>
</ul>