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


(work in progress) 
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
 
## Database design sensibilities

 
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


