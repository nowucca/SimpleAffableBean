/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2018 Steven Atkinson <steven@nowucca.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package viewmodel;

import business.ValidationException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@SuppressWarnings("unchecked")
public class CheckoutViewModel extends BaseViewModel {

    private Boolean hasValidationErrorFlag;
    private Boolean hasOrderFailureFlag;
    private ValidationException validationException;


    // Form parameters
    private String name;
    private String email;
    private String phone;
    private String address;
    private String cityRegion;
    private String creditcard;


    public CheckoutViewModel(HttpServletRequest request) {
        super(request);

        if (getHasCart()) {
            getCart().calculateTotal(getDeliverySurchargeFromServletContext(request));
        }
        this.hasValidationErrorFlag = (Boolean) session.getAttribute("validationErrorFlag");
        this.hasOrderFailureFlag = (Boolean) session.getAttribute("orderFailureFlag");
        this.validationException = (ValidationException) session.getAttribute("validationException");

        this.name = getSessionAttribute("name");
        this.email = getSessionAttribute("email");
        this.phone = getSessionAttribute("phone");
        this.address = getSessionAttribute("address");
        this.cityRegion = getSessionAttribute("cityRegion");
        this.creditcard = getSessionAttribute("creditcard");

        boolean testProductivity = "true".equals(System.getProperty("test.productivity"));
        if (testProductivity) {

            String random = String.valueOf(System.currentTimeMillis());
            if (name == null || name.isEmpty()) {
                name = "FirstName_" + random;
            }
            if (email == null || email.isEmpty()) {
                email = "FirstName" + random + "@example.net";
            }
            if (phone == null || phone.isEmpty()) {
                phone = "7075551212";
            }
            if (address == null || address.isEmpty()) {
                address = "123" + random + " Main St";
            }
            if (cityRegion == null || cityRegion.isEmpty()) {
                cityRegion = "1";
            }
            if (creditcard == null || creditcard.isEmpty()) {
                creditcard = "4444333322221111";
            }
        }

        // Now that we have captured these error conditions in the view model,
        // let's clear them for future pages in the session.
        session.setAttribute("validationErrorFlag", null);
        session.setAttribute("validationException", null);
        session.setAttribute("orderFailureFlag", null);

        // Let's remember the customer details in the session in case they continue shopping and come back
        // -- in this case we will remember their information (but not credit card).
    }

    public boolean isIsEffectiveLocaleCzech() {
        /*
        Replaces:
        <c:choose>
              <%-- When 'language' session attribute hasn't been set, check browser's preferred locale --%>
              <c:when test="${!p.hasCustomerSpecifiedLocale}">
                <c:if test="${p.isRequestLocaleCzech}">
                  <script src="js/localization/messages_cs.js" type="text/javascript"></script>
                </c:if>
              </c:when>
              <%-- Otherwise, check 'language' session attribute --%>
              <c:otherwise>
                <c:if test="${p.customerSpecifiedLocaleCzech}">
                  <script src="js/localization/messages_cs.js" type="text/javascript"></script>
                </c:if>
              </c:otherwise>
            </c:choose>
         */
        return (!getHasCustomerSpecifiedLocale() && getIsRequestLocaleCzech()) ||
            (getHasCustomerSpecifiedLocale() && isCustomerSpecifiedLocaleCzech());
    }


    public boolean getHasOrderFailureFlag() {
        return this.hasOrderFailureFlag != null && this.hasOrderFailureFlag;
    }

    public boolean getHasValidationErrorFlag() {
        return this.hasValidationErrorFlag != null && this.hasValidationErrorFlag;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public String getCreditcard() {
        return creditcard;
    }

    /*
    Replaces:
     <c:if test="${!empty nameError}">
                        <br><span class="indent"><fmt:message key="nameError"/></span>
                      </c:if>
                      <c:if test="${!empty emailError}">
                        <br><span class="indent"><fmt:message key="emailError"/></span>
                      </c:if>
                      <c:if test="${!empty phoneError}">
                        <br><span class="indent"><fmt:message key="phoneError"/></span>
                      </c:if>
                      <c:if test="${!empty addressError}">
                        <br><span class="indent"><fmt:message key="addressError"/></span>
                      </c:if>
                      <c:if test="${!empty cityRegionError}">
                        <br><span class="indent"><fmt:message key="cityRegionError"/></span>
                      </c:if>
                      <c:if test="${!empty ccNumberError}">
                        <br><span class="indent"><fmt:message
     */

    public boolean getHasNameError() {
        return this.validationException.hasInvalidField("name");
    }
    public boolean getHasEmailError() {
        return this.validationException.hasInvalidField("email");
    }
    public boolean getHasPhoneError() {
        return this.validationException.hasInvalidField("phone");
    }
    public boolean getHasAddressError() {
        return this.validationException.hasInvalidField("address");
    }
    public boolean getHasCityRegionError() {
        return this.validationException.hasInvalidField("cityRegion");
    }
    public boolean getHasCCNumberError() {
        return this.validationException.hasInvalidField("ccNumber");
    }


    protected String getSessionAttribute(String name) {
        String v = String.class.cast(session.getAttribute(name));
        return v;
    }
}
