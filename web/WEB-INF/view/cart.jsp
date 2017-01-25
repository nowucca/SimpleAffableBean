<%--
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html

 * author: tgiunipero
--%>


<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Controller so that
     users view the same page when switching between English and Czech. --%>
<c:set var="view" value="/cart" scope="session"/>


<%-- HTML markup starts below --%>

<div id="singleColumn">

    <c:choose>
        <c:when test="${cart.numberOfItems > 1}">
            <p><fmt:message key="yourCartContains"/> ${cart.numberOfItems} <fmt:message key="items"/>.</p>
        </c:when>
        <c:when test="${cart.numberOfItems == 1}">
            <p><fmt:message key="yourCartContains"/> ${cart.numberOfItems} <fmt:message key="item"/>.</p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="yourCartEmpty"/></p>
        </c:otherwise>
    </c:choose>

    <div id="actionBar">
        <%-- clear cart widget --%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">

            <c:url var="url" value="viewCart">
                <c:param name="clear" value="true"/>
            </c:url>

            <a href="${url}" class="bubble hMargin"><fmt:message key="clearCart"/></a>
        </c:if>

        <%-- continue shopping widget --%>
        <c:set var="value">
            <c:choose>
                <%-- if 'selectedCategory' session object exists, send user to previously viewed category --%>
                <c:when test="${!empty selectedCategory}">
                    category
                </c:when>
                <%-- otherwise send user to welcome page --%>
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>

        <c:url var="url" value="${value}"/>
        <a href="${url}" class="bubble hMargin"><fmt:message key="continueShopping"/></a>

        <%-- checkout widget --%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">
            <a href="<c:url value='checkout'/>" class="bubble hMargin"><fmt:message key="proceedCheckout"/></a>
        </c:if>
    </div>

    <c:if test="${!empty cart && cart.numberOfItems != 0}">

      <h4 id="subtotal"><fmt:message key="subtotal"/>:
          <fmt:formatNumber type="currency" currencySymbol="&euro; " value="${cart.subtotal}"/>
      </h4>

      <table id="cartTable">

        <tr class="header">
            <th><fmt:message key="product"/></th>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="price"/></th>
            <th><fmt:message key="quantity"/></th>
        </tr>

        <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">

          <c:set var="product" value="${cartItem.product}"/>

          <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
            <td>
                <img src="${initParam.productImagePath}${product.name}.png"
                     alt="<fmt:message key="${product.name}"/>">
            </td>

            <td><fmt:message key="${product.name}"/></td>

            <td>
                <fmt:formatNumber type="currency" currencySymbol="&euro; " value="${cartItem.total}"/>
                <br>
                <span class="smallText">(
                    <fmt:formatNumber type="currency" currencySymbol="&euro; " value="${product.price}"/>
                    / <fmt:message key="unit"/> )</span>
            </td>

            <td>
                <form action="<c:url value='updateCart'/>" method="post">
                    <input type="hidden"
                           name="productId"
                           value="${product.id}">
                    <input type="text"
                           maxlength="2"
                           size="2"
                           value="${cartItem.quantity}"
                           name="quantity"
                           style="margin:5px">
                    <input type="submit"
                           name="submit"
                           value="<fmt:message key='update'/>">
                </form>
            </td>
          </tr>

        </c:forEach>

      </table>

    </c:if>
</div>