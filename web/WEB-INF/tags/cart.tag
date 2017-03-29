<%@ tag pageEncoding="UTF-8" %> <%-- required --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="page" type="viewmodel.BaseViewModel" required="true" %>
<div class="headerWidget" id="viewCart">

  <img src="<c:url value="/img/cart.gif"/>" alt="shopping cart icon" id="cart">

  <c:set var="cart" scope="request" value="${page.header.shoppingCartHeader}"/>

  <span class="horizontalMargin">
    <%-- Show number of items --%>
    ${cart.numberOfItems}
    <%-- Handle singular/plural forms of 'item' --%>
    <fmt:message key="${cart.itemsTextKey}" />
  </span>

  <c:if test="${cart.isViewable}">
    <a href="<c:url value='cart'/>" class="bubble">
      <fmt:message key="cart" />
    </a>
  </c:if>
</div>
