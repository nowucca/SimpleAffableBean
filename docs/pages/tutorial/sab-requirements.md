---
layout: page-tutorial
title: "Simple Affable Bean Site Requirements"
subheadline: "The Simple Affable Bean Tutorial"
teaser: ""
permalink: /tutorial/site-requirements
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




The requirements of the Simple Affable Bean grocery store and the original AffableBean remain identical.

Please read [the original requirements and design section](https://netbeans.org/kb/docs/javaee/ecommerce/design.html) of the original AffableBean project.

To summarize the main points here:

* The process involves understanding the real-world scenario, and 

* from that developing requirements, 

* build some screen/page mockups with page flow diagrams 



For the Simple Affable Bean grocery store, let's run through the scenario and requirements briefly here.

## The Scenario

* A small grocery store, the Affable Bean, collaborates with several local farms to supply a community with organic produce and foods. 

* The grocery store staff have asked you, the Java web developer, to create a website that will enable their customers to shop online. They have also asked that you create an administration console alongside the website, which will allow staff members to keep track of orders.

* The store's location is in Prague, in the Czech Republic. Because regular clientele are both English and Czech-speaking, staff have requested that the website support both languages.

* The grocery store has already purchased a domain and web hosting plan that provides a Java application server and MySQL database server.  Staff have indicated that one technically-oriented member is able to deploy the application to the production server once it is ready.

## The Requirements

The initial phase of any project involves gathering information before making any design or implementation decisions. In its most common form, this involves direct and frequent communication with a customer. Based on the provided scenario, the Affable Bean staff have communicated to you that the application you are to create should fulfill the following requirements:

* An online representation of the products that are sold in the physical store. There are four categories (dairy, meats, bakery, fruit & veg), and four products for each category, which online shoppers can browse. Details are provided for each product (i.e., name, image, description, price).

* Shopping cart functionality, which includes the ability to:
  * add items to a virtual shopping cart.
  * remove items from the shopping cart.
  * update item quantities in the shopping cart.
  * view a summary of all items and quantities in the shopping cart.
  * place an order and make payment through a secure checkout process.

* An administration console, enabling staff to view customer orders.

* Security, in the form of protecting sensitive customer data while it is transferred over the Internet, and preventing unauthorized access to the administration console.
Language support for both English and Czech. (Website only)

* The company staff are able to provide you with product and category images, descriptions and price details, as well as any website graphics that are to be used. The staff are also able to provide all text and language translations for the website.

## Page and Site Mockups and Design

Let's get a clearer picture of how the site is supposed to look and behave.

Different people visualize website behavior differently.  
That is why it is important to describe websites from different perspectives, such as using mockups and with a page flow diagram.

After working with the customer, it is determined that the following pages comprise the site.

(For the purposes of this part of the tutorial, we will not focus on the administration console, 
although analysis of those requirements should also take place.)


----

**Home/Welcome page**

![home page mockup]({{site.url}}{{site.baseurl}}/images/mockup-index-small.png){:class="img-responsive" style="margin-left: 2em; float:right"}

The welcome page is the website's home page, and entry point for the application. It introduces the business and service to the user, and enables the user to navigate to any of the four product categories.

----

**Category page**

![category page mockup]({{site.url}}{{site.baseurl}}/images/mockup-category-small.png){:class="img-responsive" style="margin-left: 2em; float:right"}

The category page provides a listing of all products within the selected category. From this page, a user is able to view all product information, and add any of the listed products to his or her shopping cart. A user can also navigate to any of the provided categories.

----

**Cart page**

![cart page mockup]({{site.url}}{{site.baseurl}}/images/mockup-cart-small.png){:class="img-responsive" style="margin-left: 2em; float:right"}

The cart page lists all items held in the user's shopping cart. It displays product details for each item, and tallies the subtotal for the items in the cart. From this page, a user can:

* Clear all items in his or her cart 
(Clicking 'clear cart' causes the 'proceed to checkout' buttons and shopping cart table to disappear.)

* Update the quantity for any listed item 
(The price and quantity are updated; the subtotal is recalculated. If user sets quantity to '0', the product table row is removed.)

* Return to the previous category by clicking 'continue shopping'

* Proceed to checkout

----

**Checkout page**

![checkout page mockup]({{site.url}}{{site.baseurl}}/images/mockup-checkout-small.png){:class="img-responsive" style="margin-left: 2em; float:right"}

The checkout page collects information from the customer using a form. This page also displays purchase conditions, and summarizes the order by providing calculations for the total cost.

The user is able to send personal details over a secure channel.

----

**Confirmation page**

![confirmation page mockup]({{site.url}}{{site.baseurl}}/images/mockup-confirmation-small.png){:class="img-responsive" style="margin-left: 2em; float:right"}

The confirmation page returns a message to the customer confirming that the order was successfully recorded. An order reference number is provided to the customer, as well as a summary listing order details.

Order summary and customer personal details are returned over a secure channel.

----


### Page Flow

To help consolidate the relationships between the proposed mockups and better illustrate the functionality that each page should provide, you prepare a diagram that demonstrates the process flow of the application.

The diagram displays the visual and functional components of each page, and highlights the primary actions available to the user in order to navigate through the site to complete a purchase.

![page flow]({{site.url}}{{site.baseurl}}/images/mockup-process-flow.png){:class="img-responsive"}

----
Please proceed to the next tutorial section: [Simple Affable Bean Tutorial: Website Architecture]({{site.url}}{{site.baseurl}}/tutorial/website-architecture)
