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

<%-- customerRecord is requested --%>
<table id="adminTable" class="detailsTable">

    <tr class="header">
        <th colspan="2">customer details</th>
    </tr>
    <tr>
        <td style="width: 290px"><strong>customer id:</strong></td>
        <td>${customerRecord.customerId}</td>
    </tr>
    <tr>
        <td><strong>name:</strong></td>
        <td>${customerRecord.name}</td>
    </tr>
    <tr>
        <td><strong>email:</strong></td>
        <td>${customerRecord.email}</td>
    </tr>
    <tr>
        <td><strong>phone:</strong></td>
        <td>${customerRecord.phone}</td>
    </tr>
    <tr>
        <td><strong>address:</strong></td>
        <td>${customerRecord.address}</td>
    </tr>
    <tr>
        <td><strong>city region:</strong></td>
        <td>${customerRecord.cityRegion}</td>
    </tr>
    <tr>
        <td><strong>credit card number:</strong></td>
        <td>${customerRecord.ccNumber}</td>
    </tr>

    <tr><td colspan="2" style="padding: 0 20px"><hr></td></tr>

    <tr class="tableRow"
        onclick="document.location.href='<c:url value="/admin/order/${order.customerOrderId}"/>'">
        <td colspan="2">
            <%-- Anchor tag is provided in case JavaScript is disabled --%>
            <a href="<c:url value="/admin/order/${order.customerOrderId}"/>" class="noDecoration">
                <strong>view order summary &#x279f;</strong></a></td>
    </tr>
</table>
