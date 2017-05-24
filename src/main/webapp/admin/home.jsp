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
<div id="adminMenuBox">
    <div id="adminMenu" class="alignLeft">
        <p><a href="<c:url value='customers'/>">view all customers</a></p>

        <p><a href="<c:url value='orders'/>">view all orders</a></p>

        <p><a href="<c:url value='logout'/>">log out</a></p>
    </div>

    <div class="adminForm">
        <form action="<c:url value='customer'/>" method="get">
            view customer record<br>
            <input type="text" name="customerId" placeholder=" enter customer id here.." required>
            <input type="submit" value="Submit">
        </form>
        <%--<form id="customerForm" onsubmit="customerFormFunction(); return false;">--%>
        <%--<form id="customerForm" onsubmit="customerFormFunction()" method="post">--%>
            <%--view customer record<br>--%>
            <%--<input type="text" name="customerId" placeholder=" enter customer id here.." required>--%>
            <%--&lt;%&ndash;<input type="text" placeholder=" enter customer id here.." required>&ndash;%&gt;--%>
            <%--<input type="submit" value="Submit">--%>
        <%--</form>--%>
    </div>

    <%--<script>--%>
        <%--function customerFormFunction(){--%>
            <%--var action_src = "customer/" + document.getElementsByName("customerId")[0].value;--%>
            <%--var form = document.getElementById('customerForm');--%>
            <%--form.action = action_src ;--%>
        <%--}--%>
    <%--</script>--%>

    <div class="adminForm">
        <form action="<c:url value='order'/>" method="get">
            view order record<br>
            <input type="text" name="orderId" placeholder=" enter order id here.." required>
            <%--<input type="text" placeholder=" enter order id here.." required>--%>
            <input type="submit" value="Submit">
        </form>
    </div>
</div>
