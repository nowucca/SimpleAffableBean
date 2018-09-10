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
<jsp:useBean id="p" scope="request" type="viewmodel.CartViewModel"/>

<%-- HTML markup starts below --%>

<div id="singleColumn">

    <c:choose>
        <c:when test="${p.numberOfCartItems > 1}">
            <p><fmt:message key="yourCartContains"/> ${cart.numberOfItems} <fmt:message key="items"/>.</p>
        </c:when>
        <c:when test="${p.numberOfCartItems == 1}">
            <p><fmt:message key="yourCartContains"/> ${cart.numberOfItems} <fmt:message key="item"/>.</p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="yourCartEmpty"/></p>
        </c:otherwise>
    </c:choose>

    <div id="actionBar">
        <%-- clear cart widget --%>
        <c:if test="${p.hasNonEmptyCart}">

            <c:url var="clear_url" value="cart">
                <c:param name="clear" value="true"/>
            </c:url>

            <a href="${clear_url}" class="bubble hMargin"><fmt:message key="clearCart"/></a>
        </c:if>

        <%-- continue shopping widget --%>
        <c:set var="continue_shopping_location">
            <c:choose>
                <%-- if 'selectedCategory' session object exists, send user to previously viewed category --%>
                <c:when test="${p.hasSelectedCategory}">
                    category
                </c:when>
                <%-- otherwise send user to welcome page --%>
                <c:otherwise>
                    home
                </c:otherwise>
            </c:choose>
        </c:set>

        <c:url var="continue_shopping_url" value="${continue_shopping_location}"/>
        <a href="${continue_shopping_url}" class="bubble hMargin"><fmt:message key="continueShopping"/></a>

        <%-- checkout widget --%>
        <c:if test="${p.hasNonEmptyCart}">
            <a href="<c:url value='checkout'/>" class="bubble hMargin"><fmt:message key="proceedCheckout"/></a>
        </c:if>
    </div>

    <c:if test="${p.hasNonEmptyCart}">

      <h4 id="subtotal"><fmt:message key="subtotal"/>:
          <fmt:formatNumber type="currency" currencySymbol="&euro; " value="${p.cart.subtotal/100.0}"/>
      </h4>

      <table id="cartTable">

        <tr class="header">
            <th><fmt:message key="product"/></th>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="price"/></th>
            <th><fmt:message key="quantity"/></th>
        </tr>

        <c:forEach var="cartItem" items="${p.cart.items}" varStatus="iter">

          <c:set var="product" value="${cartItem.product}"/>

          <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
            <td>
                <img src="${p.productImagePath}${product.name}.png"
                     alt="<fmt:message key="${product.name}"/>">
            </td>

            <td><fmt:message key="${product.name}"/></td>

            <td>
                <fmt:formatNumber type="currency" currencySymbol="&euro; " value="${cartItem.total/100.0}"/>
                <br>
                <span class="smallText">(
                    <fmt:formatNumber type="currency" currencySymbol="&euro; " value="${product.price/100.0}"/>
                    / <fmt:message key="unit"/> )</span>
            </td>

            <td>
                <form action="<c:url value='cart'/>" method="post">
                    <input type="hidden"
                           name="productId"
                           value="${product.productId}">
                   <input type="hidden"
                          name="action"
                          value="update"/>
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
