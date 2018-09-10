<%--

    BSD 3-Clause License

    Copyright (C) 2018 Steven Atkinson <steven@nowucca.com>
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this
      list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

    * Neither the name of the copyright holder nor the names of its
      contributors may be used to endorse or promote products derived from
      this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
    AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
    IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
    DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
    FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
    DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
    SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
    CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
    OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
    OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

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
