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
<jsp:useBean id="p" scope="request" type="viewmodel.admin.AdminCustomersViewModel"/>

<%-- customerList is requested --%>
<table id="adminTable" class="detailsTable detailsCustomersTable">

    <tr class="header">
        <th colspan="4">customers</th>
    </tr>

    <tr class="tableHeading">
        <td>customer id</td>
        <td>name</td>
        <td>email</td>
        <td>phone</td>
    </tr>

    <c:forEach var="customer" items="${p.customerList}" varStatus="iter">

    <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
        onclick="document.location.href='customer/${customer.customerId}'">

            <%-- Below anchor tags are provided in case JavaScript is disabled --%>
        <td><a href="<c:url value="/admin/customer/${customer.customerId}"/>" class="noDecoration">${customer.customerId}</a></td>
        <td><a href="<c:url value="/admin/customer/${customer.customerId}"/>" class="noDecoration">${customer.name}</a></td>
        <td><a href="<c:url value="/admin/customer/${customer.customerId}"/>" class="noDecoration">${customer.email}</a></td>
        <td><a href="<c:url value="/admin/customer/${customer.customerId}"/>" class="noDecoration">${customer.phone}</a></td>
    </tr>

</c:forEach>

</table>
