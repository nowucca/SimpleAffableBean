<%@ tag pageEncoding="UTF-8" %> <%-- required --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="page" type="viewmodel.BaseViewModel" required="true" %>
<div class="headerWidget" id="viewCart">

  <img src="<c:url value="/img/cart.gif"/>" alt="shopping cart icon" id="cart">

  <%-- If 'numberOfItems' property doesn't exist, or if number of items
       in cart is 0, output '0', otherwise output 'numberOfItems' --%>
  <span class="horizontalMargin">
                      <c:choose>
                        <c:when test="${cart.numberOfItems == null}">
                          0
                        </c:when>
                        <c:otherwise>
                          ${cart.numberOfItems}
                        </c:otherwise>
                      </c:choose>

                  <%-- Handle singular/plural forms of 'item' --%>
                      <c:choose>
                        <c:when test="${cart.numberOfItems == 1}">
                          <fmt:message key="item" />
                        </c:when>
                        <c:when test="${cart.numberOfItems == 2 ||
                                        cart.numberOfItems == 3 ||
                                        cart.numberOfItems == 4}">
                          <fmt:message key="items2-4" />
                        </c:when>
                        <c:otherwise>
                          <fmt:message key="items" />
                        </c:otherwise>
                      </c:choose>
                    </span>

  <c:if test="${!empty cart && cart.numberOfItems != 0 &&
                                  !(fn:contains(pageContext.request.method, 'GET') &&
                                   fn:contains(pageContext.request.servletPath,'/cart'))}">

    <a href="<c:url value='cart'/>" class="bubble">
      <fmt:message key="cart" />
    </a>
  </c:if>
</div>
</div>
