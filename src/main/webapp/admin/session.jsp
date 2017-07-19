<%--

    BSD 3-Clause License

    Copyright (C) 2017 Steven Atkinson <support@simpleaffablebean.com>
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
<jsp:useBean id="p" scope="request" type="viewmodel.admin.AdminSessionViewModel"/>

<%-- session information --%>
<table id="adminTable" class="detailsTable detailsSessionTable">

    <tr class="header">
        <th colspan="2">session</th>
    </tr>
    <tr>
        <td><strong>session id:</strong></td>
        <td>${p.session.id}</td>
    </tr>
    <tr>
        <td><strong>creation time:</strong></td>
        <td>
            <fmt:formatDate value="${p.creationTime}"
                            type="both"
                            dateStyle="short"
                            timeStyle="long"/></td>
    </tr>
    <tr>
        <td><strong>last accessed time:</strong></td>
        <td>
            <fmt:formatDate value="${p.lastAccessedTime}"
                            type="both"
                            dateStyle="short"
                            timeStyle="long"/></td>
    </tr>
    <tr>
        <td><strong>max inactive interval (seconds):</strong></td>
        <td>${p.session.maxInactiveInterval}</td>
    </tr>

    <c:forEach var="attribute" items="${p.sessionAttributes}">
        <tr>
            <td><strong>${attribute.key}:</strong></td>
            <td>${attribute.value}</td>
        </tr>
    </c:forEach>

</table>

<%-- shopping cart contents--%>
<c:if test="${p.cart.numberOfItems > 0}">

    <div class="adminTableBox">

        <table id="adminCartTableHeader" class="detailsTable">
            <tr class="header">
                <th colspan="4">shopping cart</th>
            </tr>
        </table>

        <table id="adminCartTable" class="detailsCartTable">

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
                        <img src="<c:url value="/${p.productImagePath}${product.name}.png"/>"
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
                        <c:out value="${cartItem.quantity}" />
                    </td>
                </tr>

            </c:forEach>

            <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                <td></td>
                <td>
                    <h4 font-weight="bold"><fmt:message key="subtotal"/></h4>

                </td>
                <td>
                    <h4 font-weight="bold">
                        <fmt:formatNumber type="currency" currencySymbol="&euro; " value="${p.cart.subtotal/100.0}"/>
                    </h4>
                </td>
                <td></td>

            </tr>

        </table>

    </div>

</c:if>
