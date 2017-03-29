<%@ tag pageEncoding="UTF-8" %> <%-- required --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="page" type="viewmodel.BaseViewModel" required="true" %>
<div class="headerWidget">

  <%-- tests for the following:
       * if cart exists and is not empty
       * if the servlet path does not contain '/checkout'
       * if the requested view is not checkout
       * if the servlet path does not contain '/cart'
       * if the requested view is not cart
       * if the checkout view is returned with order failure message flagged
       * if the checkout view is returned with server-side validation errors detected

       <c:if test="${!empty sessionScope.cart}">
           CART EXISTS AND IS NOT NULL
       </c:if>
           <BR>
       <c:if test="${sessionScope.cart.numberOfItems != 0}">
           NUMBER OF ITEMS IN CART IS NOT 0
       </c:if>
           <BR>
       <c:if test="${fn:contains(pageContext.request.servletPath,'/checkout')}">
           SERVLET PATH CONTAINS '/checkout'
       </c:if>
           <BR>
       <c:if test="${requestScope['javax.servlet.forward.servlet_path'] ne '/checkout'}">
           REQUEST IS NOT CHECKOUT
       </c:if>
           <BR>
       <c:if test="${requestScope.validationErrorFlag ne true}">
           VALIDATION ERROR IS NOT FLAGGED
       </c:if>
           <BR>
       <c:if test="${requestScope.orderFailureFlag ne true}">
           ORDER FAILURE ERROR IS NOT FLAGGED
       </c:if> --%>

  <c:if test="${!empty cart && cart.numberOfItems != 0 &&

                                  !fn:contains(pageContext.request.servletPath,'/checkout') &&
                                  requestScope['javax.servlet.forward.servlet_path'] ne '/checkout' &&

                                  validationErrorFlag ne true &&
                                  orderFailureFlag ne true}">

    <a href="<c:url value='checkout'/>" class="bubble">
      <fmt:message key="proceedCheckout"/>
    </a>
  </c:if>
</div>
