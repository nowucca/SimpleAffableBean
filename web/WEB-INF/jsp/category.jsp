<%--
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html

 * author: tgiunipero
--%>


<jsp:useBean id="p" scope="request" type="viewmodel.CategoryViewModel"/>

<%-- HTML markup starts below --%>

<div id="categoryLeftColumn">

    <c:forEach var="category" items="${p.categories}">

        <c:choose>
            <c:when test="${category.name == p.selectedCategory.name}">
                <div class="categoryButton" id="selectedCategory">
                    <span class="categoryText">
                        <fmt:message key="${category.name}"/>
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='category?${category.categoryId}'/>" class="categoryButton">
                    <span class="categoryText">
                        <fmt:message key="${category.name}"/>
                    </span>
                </a>
            </c:otherwise>
        </c:choose>

    </c:forEach>

</div>

<div id="categoryRightColumn">

    <p id="categoryTitle"><fmt:message key="${p.selectedCategory.name}" /></p>

    <table id="productTable">

        <c:forEach var="product" items="${p.selectedCategoryProducts}" varStatus="iter">

            <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                <td>
                    <img src="${p.productImagePath}${product.name}.png"
                         alt="<fmt:message key='${product.name}'/>">
                </td>

                <td>
                    <fmt:message key="${product.name}"/>
                    <br>
                    <span class="smallText"><fmt:message key='${product.name}Description'/></span>
                </td>

                <td><fmt:formatNumber type="currency" currencySymbol="&euro; " value="${product.price/100.0}"/></td>

                <td>
                    <form action="<c:url value='cart'/>" method="post">
                        <input type="hidden"
                               name="productId"
                               value="${product.productId}">
                        <input type="hidden"
                               name="action"
                               value="add"/>
                        <input type="submit"
                               name="submit"
                               value="<fmt:message key='addToCart'/>">
                    </form>
                </td>
            </tr>

        </c:forEach>

    </table>
</div>
