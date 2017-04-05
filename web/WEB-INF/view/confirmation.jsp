<%--
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html

 * author: tgiunipero
--%>

<jsp:useBean id="p" scope="request" type="viewmodel.ConfirmationViewModel"/>

<%-- HTML markup starts below --%>

<div id="singleColumn">

    <p id="confirmationText">
        <strong><fmt:message key="successMessage"/></strong>
        <br><br>
        <fmt:message key="confirmationNumberMessage"/>
        <strong>${p.orderDetails.customerOrder.confirmationNumber}</strong>
        <br>
        <fmt:message key="contactMessage"/>
        <br><br>
        <fmt:message key="thankYouMessage"/>
    </p>

    <div class="summaryColumn" >

        <table id="orderSummaryTable" class="detailsTable">
            <tr class="header">
                <th colspan="3"><fmt:message key="orderSummary"/></th>
            </tr>

            <tr class="tableHeading">
                <td><fmt:message key="product"/></td>
                <td><fmt:message key="quantity"/></td>
                <td><fmt:message key="price"/></td>
            </tr>

            <c:forEach var="lineItem" items="${p.orderDetails.customerOrder.customerOrderLineItems}" varStatus="iter">

                <tr class="${((iter.index % 2) != 0) ? 'lightBlue' : 'white'}">
                    <td>
                        <fmt:message key="${p.orderDetails.products[iter.index].name}"/>
                    </td>
                    <td class="quantityColumn">
                        ${lineItem.quantity}
                    </td>
                    <td class="confirmationPriceColumn">
                        <fmt:formatNumber type="currency" currencySymbol="&euro; "
                                          value="${(p.orderDetails.products[iter.index].price * lineItem.quantity)/100.0}"/>
                    </td>
                </tr>

            </c:forEach>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="2" id="deliverySurchargeCellLeft"><strong><fmt:message key="surcharge"/>:</strong></td>
                <td id="deliverySurchargeCellRight">
                    <fmt:formatNumber type="currency"
                                      currencySymbol="&euro; "
                                      value="${p.deliverySurcharge/100.0}"/></td>
            </tr>

            <tr class="lightBlue">
                <td colspan="2" id="totalCellLeft"><strong><fmt:message key="total"/>:</strong></td>
                <td id="totalCellRight">
                    <fmt:formatNumber type="currency"
                                      currencySymbol="&euro; "
                                      value="${p.orderDetails.customerOrder.amount/100.0}"/></td>
            </tr>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="3" id="dateProcessedRow"><strong><fmt:message key="dateProcessed"/>:</strong>
                    <fmt:formatDate value="${p.orderDetails.customerOrder.dateCreated}"
                                    type="both"
                                    dateStyle="short"
                                    timeStyle="short"/></td>
            </tr>
        </table>

    </div>

    <div class="summaryColumn" >

        <table id="deliveryAddressTable" class="detailsTable">
            <tr class="header">
                <th colspan="3"><fmt:message key="deliveryAddress"/></th>
            </tr>

            <tr>
                <td colspan="3" class="lightBlue">
                    ${p.orderDetails.customer.name}
                    <br>
                    ${p.orderDetails.customer.address}
                    <br>
                    <fmt:message key="prague"/> ${p.orderDetails.customer.cityRegion}
                    <br>
                    <hr>
                    <strong><fmt:message key="email"/>:</strong> ${p.orderDetails.customer.email}
                    <br>
                    <strong><fmt:message key="phone"/>:</strong> ${p.orderDetails.customer.phone}
                </td>
            </tr>
        </table>
    </div>

    <div class="summaryColumn" >
        <form method="get" action="<c:url value="/home"/>">
            <input type="submit" value="<fmt:message key="goBackHome"/>"/>
        </form>
    </div>
</div>
