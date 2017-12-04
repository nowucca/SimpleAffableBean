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
                    <button type="button"
                            class="ajaxAddToCart"
                            data-product-id="${product.productId}"
                            data-action="add"
                            name="submit">ajax-<fmt:message key='addToCart'/></button>


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

    <script>

        // We have to encode form data for POST operations.
        // Standard Javascript has an encodeURIComponent that does this job.

        function encodeFormData(data) {
            if (data && typeof(data) === 'object') {
                var y = '', e = encodeURIComponent;
                for (field in data) {
                    // A safety check to avoid iterating over unexpected properties.
                    if (data.hasOwnProperty(field)) {
                        y += '&' + e(field) + '=' + e(data[field]);
                    }
                }
                return y.slice(1); // strip leading '&'
            }
            return '';
        }

        // Make an ajax request to post the data object to the url, using
        // Standard Javascript for modern browsers.
        //
        // Upon success, invoke the callback function with xhr.responseText.
        function ajaxPost(url, data, callback) {
            try {
                var xhr = new XMLHttpRequest();

                xhr.onreadystatechange = ensureReadiness;

                function ensureReadiness() {
                    if(xhr.readyState < 4) {
                        return;
                    }

                    if(xhr.status !== 200) {
                        return;
                    }

                    // all is well
                    if(xhr.readyState === 4) {
                        callback(xhr.responseText, xhr);
                    }
                }

                xhr.open('POST', url, true);
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
                xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                xhr.setRequestHeader('Accept', 'application/json');
                xhr.send(encodeFormData(data));
            } catch (e) {
                window.console && console.log(e);
            }
        }

        // Now, paint the click functions onto the addToCart buttons.

        var buttons = document.querySelectorAll(".ajaxAddToCart");

        for (i = 0; i < buttons.length; i++) {
            buttons[i].addEventListener("click", function(event) {

                // Cross-browser trickery to get the source element 'src' that was clicked on.
                event = event || window.event;
                var src = event.target || event.srcElement;

                // Handle this event here, do not propagate to parent DOM elements.
                event.stopPropagation();

                // Read the data for product and action from the src element.
                // The "dataset" collection is a standard javascript object in modern browsers.
                var data = { "productId": src.dataset.productId, "action": src.dataset.action };

                //
                ajaxPost('cart', data, function(responseText, xhr) {
                    alert ('Response text: '+responseText+'; Ready state is '+xhr.readyState);
                    var cartSize = JSON.parse(responseText).cartSize;
                    document.getElementById('cartSizeHeaderElement').textContent = ''+cartSize;
                });
            });
        }
    </script>
</div>
