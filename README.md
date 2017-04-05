# SimpleAffableBean
A rewrite of the Affable Bean EJB web app, moving away from EJB and using a simpler technology stack.

This software is an adaptation from the Affable Bean project, released under the 2-clause Berkeley licence by Sun and then Oracle.
Please see the LICENCE file for compliance and licensing concerns.

The starting point for this project is the complete Affable beans project at: https://netbeans.org/projects/samples/downloads/download/Samples/JavaEE/ecommerce/AffableBean_complete.zip

## Problems Being Addressed

Hardly anyone I know uses the EJB stack as it was meant to be used, with session and entity beans, data sources, services and the rest of it.  I've found that some of the best technology stacks avoid too much abstraction, and are straightforward to use, and lead to code that is straightforward to reason about.  I would classify a full-blown EJB container as getting low-marks in those areas.

## Solutions Being Proposed

The goal of this rewrite is to enable the use of a pure servlet container (such as Tomcat or Jetty) to run the AffableBean project.
To achieve this, we will be writing our own DataSource wrapper, writing a separate business logic layer (services and daos) and using a combination of servlets and JSPs for the front layer (including the ViewModel pattern of modelling the view at the servlet layer with bean classes). 

As progress is made, I'll be tweaking the implementation here and perhaps strategies as I learn limitations along the way.

Done
- Finish view model for confirmation 
- Tester productivity mode as a system property
- Stop invalidating the session on successful order but do clear the cart.
- Implement using the session to remember and clear the checkout form

Ongoing
- Logging for service classes using commons logging (basic)
- Code cleanup for MVVM before master merge
- Add AdminSession functionality to visualize the session
- Enforce session cleanliness by using a business level CustomerSession object embedded in the Http session.

Feature Ideas
- fixing up Admin Servlets
  - make separate controllers
  - use Daos
  - use MVVM


org.apache.tomcat.dbcp.pool2.impl.DefaultPooledObject$AbandonedObjectCreatedException: Pooled object created 2017-04-04 22:42:57 -0700 by the following code has not been returned to the pool:
	at org.apache.tomcat.dbcp.pool2.impl.DefaultPooledObject.allocate(DefaultPooledObject.java:194)
	at org.apache.tomcat.dbcp.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:453)
	at org.apache.tomcat.dbcp.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:359)
	at org.apache.tomcat.dbcp.dbcp2.PoolingDataSource.getConnection(PoolingDataSource.java:134)
	at org.apache.tomcat.dbcp.dbcp2.BasicDataSource.getConnection(BasicDataSource.java:1543)
	at business.JdbcUtils.getConnection(JdbcUtils.java:25)
	at business.order.DefaultCustomerOrderService.placeOrder(DefaultCustomerOrderService.java:33)
	at controller.CheckoutServlet.doPost(CheckoutServlet.java:125)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:648)
	at controller.SimpleAffableBeanServlet.service(SimpleAffableBeanServlet.java:51)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:729)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:230)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at filter.SessionTimeoutFilter.doFilter(SessionTimeoutFilter.java:42)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:474)
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79)
	at org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:624)
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:349)
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:783)
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:789)
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1437)
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
	at java.lang.Thread.run(Thread.java:745)
org.apache.tomcat.dbcp.pool2.impl.DefaultPooledObject$AbandonedObjectCreatedException: Pooled object created 2017-04-04 22:43:59 -0700 by the following code has not been returned to the pool:
	at org.apache.tomcat.dbcp.pool2.impl.DefaultPooledObject.allocate(DefaultPooledObject.java:194)
	at org.apache.tomcat.dbcp.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:453)
	at org.apache.tomcat.dbcp.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:359)
	at org.apache.tomcat.dbcp.dbcp2.PoolingDataSource.getConnection(PoolingDataSource.java:134)
	at org.apache.tomcat.dbcp.dbcp2.BasicDataSource.getConnection(BasicDataSource.java:1543)
	at business.JdbcUtils.getConnection(JdbcUtils.java:25)
	at business.order.DefaultCustomerOrderService.placeOrder(DefaultCustomerOrderService.java:33)
	at controller.CheckoutServlet.doPost(CheckoutServlet.java:125)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:648)
	at controller.SimpleAffableBeanServlet.service(SimpleAffableBeanServlet.java:51)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:729)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:230)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at filter.SessionTimeoutFilter.doFilter(SessionTimeoutFilter.java:42)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:474)
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79)
	at org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:624)
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:349)
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:783)
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:789)
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1437)
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
	at java.lang.Thread.run(Thread.java:745)
org.apache.tomcat.dbcp.pool2.impl.DefaultPooledObject$AbandonedObjectCreatedException: Pooled object created 2017-04-04 22:43:39 -0700 by the following code has not been returned to the pool:
	at org.apache.tomcat.dbcp.pool2.impl.DefaultPooledObject.allocate(DefaultPooledObject.java:194)
	at org.apache.tomcat.dbcp.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:453)
	at org.apache.tomcat.dbcp.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:359)
	at org.apache.tomcat.dbcp.dbcp2.PoolingDataSource.getConnection(PoolingDataSource.java:134)
	at org.apache.tomcat.dbcp.dbcp2.BasicDataSource.getConnection(BasicDataSource.java:1543)
	at business.JdbcUtils.getConnection(JdbcUtils.java:25)
	at business.order.DefaultCustomerOrderService.placeOrder(DefaultCustomerOrderService.java:33)
	at controller.CheckoutServlet.doPost(CheckoutServlet.java:125)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:648)
	at controller.SimpleAffableBeanServlet.service(SimpleAffableBeanServlet.java:51)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:729)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:230)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at filter.SessionTimeoutFilter.doFilter(SessionTimeoutFilter.java:42)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:474)
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79)
	at org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:624)
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:349)
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:783)
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:789)
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1437)
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
	at java.lang.Thread.run(Thread.java:745)
