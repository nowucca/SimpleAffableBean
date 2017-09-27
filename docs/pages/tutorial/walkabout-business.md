---
layout: page-tutorial
title: "Code Walkabout: Business Layer"
subheadline: "The Simple Affable Bean Tutorial"
teaser: ""
permalink: /tutorial/walkabout/business
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

# Files Overview

The following diagram shows the files in the src/main/java/business folder.
We will walk through these files on this page.


The motivation for this separate business package is to house all the logic and validation necessary for your key use cases in
one area of code.  Ideally, this business package would be a library, - that is a separate compilation unit.  Why?
Because the intent behind organizing code this way is to be able to share business logic functions across multiple web applications
(e.g the main website, a customer support site, mobile applications).

Code in this package should NOT depend on any other code in src/main/java.


![sab-business-folders]({{site.url}}{{site.baseurl}}/images/sab-business-folders.png){:class="img-responsive"}

In the business folder, we organize files into:

* groups by type of object (For the SimpleAfableBean, this means the cart, categories, customers, orders, and products folders).

* general business layer objects (such as the exceptions, utility and ApplicationContext classes in src/main/java/business)

We are going to describe the files in terms of layers of concern that span multiple folders.

First we will be starting with files managing the database concerns directly.
Once that is understood, we will review the general exception classes,
followed by the idea behind having service classes, and rounding up with the main class, the ApplicationContext.

In simple terms though, the business layer provides access to an army of singleton service objects via ApplicationContext that work together to implement the use cases
 for the site.

# Data Access Layer

The data access layer classes in the business package are the JdcUtils general file, and then files named *Dao*.

## src/main/java/business/JdbcUtils.java

This class is responsible for forming connections to the MySQL database.
ALmost every request to the web application will need to read data and some will need to write data.
That is why it is critical to be able to access a connection object representing a connection to the database.[1]

### Define the Data Resource

In your src/main/webapp/META-INF/context.xml file, we define a "Resource" with a name "jdbc/SimpleAffableBean".

{% highlight java %}
<Resource name="jdbc/simpleaffablebean" auth="Container" type="javax.sql.DataSource"
              maxTotal="100"
              maxIdle="30"
              maxWaitMillis="10000"
              username="simpleaffablebean"
              password="simpleaffablebean"
              driverClassName="com.mysql.jdbc.Driver"
              removeAbandonedOnBorrow="true"
              removeAbandonedOnMaintenance="true"
              removeAbandonedTimeout="60"
              logAbandoned="true"
              timeBetweenEvictionRunsMillis="300000"
              url="jdbc:mysql://127.0.0.1:3306/simpleaffablebean"/>
{% endhighlight %}

This establishes a link between the name "jdbc/simpleaffablebean" and a connection to the MySQL database that Tomcat knows about.
In addition, it also sets up a pool of connection objects using Resource attribute parameters.

> The Tomcat container by default supports the idea of connection pooling, so that a collection of connections (that can be expensive to form)
> are initialized when the application starts up.  As demand for the database resources increases with the number of customer requests,
> this connection pool grows in size (up to a maximum limit after which the requests "block" until a connection is available).

Let's review the JdbcUtils class.

{% highlight java %}
    static final String JDBC_SIMPLEAFFABLEBEAN = "jdbc/simpleaffablebean";

    private static DataSource dataSource;

    public static Connection getConnection()  {
        if (dataSource == null) {
            dataSource = getDataSource(JDBC_SIMPLEAFFABLEBEAN);
        }

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new SimpleAffableConnectionDbException("Encountered a SQL issue getting a connection", e);
        }

    }

    private static DataSource getDataSource(String dataSourceName)  {
        try {
            InitialContext initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            return (DataSource) envCtx.lookup(dataSourceName);
        } catch (NamingException e) {
            throw new IllegalArgumentException("Encountered an issue establishing an initial JNDI context", e);
        }
    }
{% endhighlight %}


Inside the web application code, the JdbcUtils class uses the standard JNDI (Java Naming and Directory Interface)  library (provided in Tomcat and other containers)
to define a DataSource based on the "jdbc/simpleaffablebean" name.  This happens once at startup time and stored in the dataSource field.

From there, application can request a Connection object using the getConnection() method call.
This uses the dataSource to obtain a standard JDBC (Java DataBase Connectivity) Connection object.
Notice how we throw a runtime exception when an error is defined - that same exception owned by the business package
will be thrown no matter what database we choose to use in the future.




## src/main/java/business/product/

Now that we can easily obtain a database connection, how do we organize our code to be able to read and write database tables?

Let us start by modelling the objects that we wish to read from the database.
These objects are called mdoel objects, and are simple java plain objects representing table rows.
In this project, some examples are Category, CustoemrOrder, Customer, Product across different folders.

### Model class: Product

Let us focus on a small model object as an example, the Product.java class in src/main/java/business/product folder.

This class has fields that mirror the columns in the product table, with java names (e.g. productId) instead of database
column names (e.g. product_id).  We simply have accessor methods and an informative toString method to make debugging a little easier
(the string is a little more informative and is used when debugging our application).

{% highlight java %}
public class Product {

    private long productId;
    private String name;
    private int price;
    private Date lastUpdate;

    public Product(long productId, String name, int price, Date lastUpdate) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }

    public long getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public String toString() {
        return "business.product.Product[product_id=" + productId + "]";
    }

}
{% endhighlight %}

### Data Access Objects (DAO)

Now that we have defined our model objects, let us define the Data Access Objects used to read and write them to and from the database.

For the design of the DAO objects, we are going to use interfaces because there are many different storage solutions and we may
indeed should expect to change underlying storage technology for these classes later on in the lifetime of our product.

So we will start by focussing on the ProductDao interface, without worrying about whether we use MySQL or text files as the storage technology.
> In this way, DAO objects implement a [Strategy design pattern](https://en.wikipedia.org/wiki/Strategy_pattern) - we abstract away the
> exact means of reading and writing these model objects with the DAO interface.

{% highlight java %}
public interface ProductDao {
    List<Product> findByCategoryId(long categoryId);

    Product findByProductId(long productId);
}
{% endhighlight %}

This interface exposes two methods for reading product objects: find me all the products in a given category (identified by category id)
and find a specific product given a specific product id.

#### Implementing ProductDao

Now that we have our DAO interface, how can we implement it?

The src/main/java/business/product folder offers two implementations:

* ProductDaoJdbc reads the Product objects by category id and by product id from the database directly.

* ProductDaoGuava reads the Product objects by category id and by product id from an in-memry cache if available,
otherwise it refreshes and re-reads the cache using ProductDaoJdbc.

In this walkthrough, we will focus on the JDBC implementation.  The caching implementation will be visited in a further topic.

So we use the JDBC (Java Database Connectivity) API's Connection object we obtained from JdbcUtils above.
The JDBC programming model is available for almost all of the popular relational databases and is a common underpinning in many database
frameworks in industry (Hibernate, Spring JDBC, etc).

On the whole, the flavor of JDBC is similar to running SQL commands in a console window.
One opens up a window (akin to a JDBC Connection) and type in a command (akin to a JDBC Statement).
When the command is executed, you would see a table of results (akin to a JDBC ResultSet).

Let's step through the code for reading products by category id in ProductDaoJdbc now.

We define the SQL query to read a product given an unknown ("?") category id.
{% highlight java %}
public class ProductDaoJdbc implements ProductDao {

   private static final String FIND_BY_CATEGORY_SQL =
        "SELECT " +
            "p.product_id, " +
            "p.category_id, " +
            "p.name, " +
            "p.price, " +
            "p.last_update " +
        "FROM " +
            "product p " +
        "WHERE " +
            "p.category_id = ?";
{% endhighlight %}

The heart of the use of JDBC is in this findByCategoryId method.

We first get a connection object, and from that prepare an SQL statement given the SQL we defined above.
That SQL string has one unknown variable that we need to set - the categoryId we are searching for.
So,  "statement.setLong(1, categoryId);" establishes that the category id we are searching for came in as the method parameter.

Then we attempt to execute the statement as a query to obtain a result set.  If that works, we iterate over the rows
in the result set and read in each as a Product object using the private method "readProduct(resultSet)".

Beofre we show how we convert a ResultSet into a Product object, there are a couple of important points to make here.
Firstly, notice that if anything goes wrong, we throw a SimpleAffableQueryDbException witha  descriptive error message.
This effectively protects the caller from having to know about the JDBC-specific SQLException that is caught.
Secondly, notice that we are using a ["try-with-resources" syntax](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html), so that no matter what the ResultSet, Statement and Connection
will all have their "close()" methods called automatically.

{% highlight java %}
    @Override
    public List<Product> findByCategoryId(long categoryId) {
        List<Product> result = new ArrayList<>(16);

        try (Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(readProduct(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem reading products by category", e);
        }

        return result;
    }
{% endhighlight %}

So it simply remains to convert a ResultSet into a Product.
JDBC allows us to read from a ResultSet by column name.  So the code becomes:

{% highlight java %}
private Product readProduct(ResultSet resultSet) throws SQLException {
        Product result;
        Long productIdFromDb = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        Date lastUpdate = resultSet.getTimestamp("last_update");

        result = new Product(productIdFromDb, name, price, lastUpdate);
        return result;
    }
{% endhighlight %}

The patterns given in this section for the Product and ProductDao classes are repeated for the other objects in the SimpleAffableBean project.

# Business Exceptions

There are two Business exceptions defined generally for this business layer.

Firstly, the SimpleAffableBeanDbException class houses many static inner exception classes.  They ALL serve the purpose of
forming a protection against a client needing to catch arbitrary JDBC/Guava/some other exceptions - one of the properties we
want to ensure is that we always catch and throw some subclass of SimpleAffableDbException from every Dao method.

Secondly, the ValidationException re-inforces the point that (especially for data being written TO the database) we would
like to have one set of business rules that validate the data.  For example, customer must have a first and a last name with 2 or more characters in each.
By checking data validation rules in the business layer and throwing an exception, we centralize the code and make sure that no matter what application  we build using
this business layer, the data validation rules will remain consistent.


# Services

On top of each DAO object, we build a Service object, again with an interface and an implementation.
The idea behind service classes is that some business logic remains the same, no matter how that data is accessed,
and for that reason, this logic is DAO-independent.

One small example of such logic is the need for logging events.
SimpleAffableBean akes logging events the responsibility of Service class implementations.

To continue our example, we look at the ProductService and it's implementation DefaultProductService.

The ProductService is an interface that is the same as the ProductDao interface.
Service interfaces can be different of course, but here the methods are the same.

{% highlight java %}
public interface ProductService {

    List<Product> findByCategoryId(long categoryId);

    Product findByProductId(long productId);
}
{% endhighlight %}

The DefaultProductService has an instance of the ProductDao interface provided to it.

It also uses a logger.  SimpleAffableBean uses a very simple implementation of java logging called the slf4j library
with it's default implementation.  There are many choices for Java logging but the slf4j serves as a good facade for many of them,
and has a simple default implementation as well.

So this service delegates its methods to the productDao instance, but also catches exceptions
and uses its logger to log events.  This helps during debugging tremendously if you take the time to add nice specific
natural language logging messages.

{% highlight java %}
public class DefaultProductService implements ProductService {

    private static final Logger logger =
        LoggerFactory.getLogger(DefaultProductService.class);

    private ProductDao productDao;

    @Override
    public List<Product> findByCategoryId(long categoryId) {
        try {
            return productDao.findByCategoryId(categoryId);
        } catch (Exception e) {
            logger.error("Trouble finding product by category id {}", categoryId, e);
            throw e;
        }
    }

    @Override
    public Product findByProductId(long productId) {
        try {
            return productDao.findByProductId(productId);
        } catch (Exception e) {
            logger.error("Trouble finding product by product id {}", productId, e);
            throw e;
        }
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
{% endhighlight %}

Services can also use multiple Dao objects to orchestrate a more complex set of activity in the database.
For example, the CustomerOrderService needs to record information into the database using the
CustomerDao, CustomerOrderDao and the CustomerOrderLineItemDao.

# Application Context

Now that we understand model objects, how they are created by Daos, passed through Service classes for more business logic
(e.g. logging) we are in a position to understand the main class in the business layer - the ApplicationContext.

When the web applicaiton is deployed in a Tomcat container, one instance of the ApplicationContext is created.

The APplicationContext represents an access point to all the service object (and therefore indirectly Dao objects)
in the business layer.

{% highlight java %}
public final class ApplicationContext {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ProductService productService;

    private CategoryService categoryService;

    private CustomerService customerService;

    private CustomerOrderService customerOrderService;

    public static ApplicationContext INSTANCE = new ApplicationContext();
{% endhighlight %}

In turn, one instance of each Dao and Service is constructed and wired together in the ApplicationContext constructor.
Notice that we effectively build up a graph of objects wired together, and that order of building these graphs of objects matters.
This is what frameworks like [Spring](https://projects.spring.io/spring-framework/) or [Guice](https://github.com/google/guice) can do for us in simpler ways.
However it is valuable to see the boilerplate careful initialization code that these frameworks help us avoid.

{% highlight java %}
    private ApplicationContext() {

        // wire up the business.dao layer "by hand"
        ProductDao productDao = new ProductDaoJdbc();

        productService = new DefaultProductService();
        ((DefaultProductService) productService).setProductDao(productDao);

        CategoryDao categoryDao = new CategoryDaoJdbc();
        categoryService = new DefaultCategoryService();
        ((DefaultCategoryService) categoryService).setCategoryDao(categoryDao);

        CustomerDao customerDao = new CustomerDaoJdbc();
        customerService = new DefaultCustomerService();
        ((DefaultCustomerService) customerService).setCustomerDao(customerDao);

        CustomerOrderLineItemDao customerOrderLineItemDao = new CustomerOrderLineItemDaoJdbc();
        CustomerOrderDao customerOrderDao = new CustomerOrderDaoJdbc();

        customerOrderService = new DefaultCustomerOrderService();
        DefaultCustomerOrderService service = (DefaultCustomerOrderService) customerOrderService;
        service.setProductDao(productDao);
        service.setCustomerDao(customerDao);
        service.setCustomerOrderDao(customerOrderDao);
        service.setCustomerOrderLineItemDao(customerOrderLineItemDao);
{% endhighlight %}


That is the end of our walkabout for the business layer.



# Walkabout Links
<ul>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/database">Database</a>: how are the database files used?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/business">Business</a>: how and why are the business files organized?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/controller">Controller</a>: what are controllers and view models?</li>
  <li><a href="{{site.baseurl}}{{site.url}}/tutorial/walkabout/view">View</a>: let's walk through some JSP template files</li>
</ul>
