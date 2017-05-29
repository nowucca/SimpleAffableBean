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

<%-- orderRecord is requested --%>
<table id="adminTable" class="detailsTable">

    <tr class="header">
        <th colspan="2">order summary</th>
    </tr>
    <tr>
        <td><strong>order id:</strong></td>
        <td>${orderRecord.customerOrderId}</td>
    </tr>
    <tr>
        <td><strong>confirmation number:</strong></td>
        <td>${orderRecord.confirmationNumber}</td>
    </tr>
    <tr>
        <td><strong>date processed:</strong></td>
        <td>
            <fmt:formatDate value="${orderRecord.dateCreated}"
                            type="both"
                            dateStyle="short"
                            timeStyle="short"/></td>
    </tr>

    <tr>
        <td colspan="2">
            <table class="embedded detailsTable">
                <tr class="tableHeading">
                    <td class="rigidWidth">product</td>
                    <td class="rigidWidth">quantity</td>
                    <td>price</td>
                </tr>

                <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>

                <c:forEach var="orderedProduct" items="${orderedProducts}" varStatus="iter">

                    <tr>
                        <td>
                            <fmt:message key="${products[iter.index].name}"/>
                        </td>
                        <td>
                                ${orderedProduct.quantity}
                        </td>
                        <td class="confirmationPriceColumn">
                            <fmt:formatNumber type="currency" currencySymbol="&euro; "
                                              value="${products[iter.index].price * orderedProduct.quantity / 100.0}"/>
                        </td>
                    </tr>

                </c:forEach>

                <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>

                <tr>
                    <td colspan="2" id="deliverySurchargeCellLeft"><strong>delivery surcharge:</strong></td>
                    <td id="deliverySurchargeCellRight">
                        <fmt:formatNumber type="currency"
                                          currencySymbol="&euro; "
                                          value="${initParam.deliverySurcharge/100.0}"/></td>
                </tr>

                <tr>
                    <td colspan="2" id="totalCellLeft"><strong>total amount:</strong></td>
                    <td id="totalCellRight">
                        <fmt:formatNumber type="currency"
                                          currencySymbol="&euro; "
                                          value="${orderRecord.amount/100.0}"/></td>
                </tr>
            </table>
        </td>
    </tr>

    <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>

    <tr class="tableRow"
        onclick="document.location.href='<c:url value="/admin/customer/${customer.customerId}"/>'">
        <td colspan="2">
            <%-- Anchor tag is provided in case JavaScript is disabled --%>
            <a href="<c:url value="/admin/customer/${customer.customerId}"/>" class="noDecoration">
                <strong>view customer details &#x279f;</strong></a></td>
    </tr>
</table>
