<%--
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html

 * author: tgiunipero
--%>

<jsp:useBean id="p" scope="request" type="viewmodel.HomepageViewModel"/>


<%-- HTML markup starts below --%>

<div id="indexLeftColumn">
    <div id="welcomeText">
        <p style="font-size: larger"><fmt:message key='greeting' /></p>

        <p><fmt:message key='introText' /></p>
    </div>
</div>

<div id="indexRightColumn">
    <c:forEach var="category" items="${p.categories}">
        <div class="categoryBox">
            <a href="<c:url value='category?${category.categoryId}'/>">
                <span class="categoryLabel"></span>
                <span class="categoryLabelText"><fmt:message key='${category.name}'/></span>

                <img src="${p.categoryImagePath}${category.name}.jpg"
                     alt="<fmt:message key='${category.name}'/>" class="categoryImage">
            </a>
        </div>
    </c:forEach>
</div>

<%--<%@include file="../jspf/footer.jspf"%>--%>
