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


        this.name = getSessionAttributeOrRequestParameter("name");
        this.email = getSessionAttributeOrRequestParameter("email");
        this.phone = getSessionAttributeOrRequestParameter("phone");
        this.address = getSessionAttributeOrRequestParameter("address");
        this.cityRegion = getSessionAttributeOrRequestParameter("cityRegion");
        this.creditcard = getSessionAttributeOrRequestParameter("creditcard");

        boolean testProductivity = "true".equals(System.getProperty("test.productivity"));
        if (testProductivity) {

            String random = String.valueOf(System.currentTimeMillis());
            if (name == null || name.isEmpty()) {
                name = "Steve_" + random;
            }
            if (email == null || email.isEmpty()) {
                email = "steven" + random + "@atkinson.net";
            }
            if (phone == null || phone.isEmpty()) {
                phone = "4088675309";
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

        // Now that we have captured these error conditions in the view model, let's clear them for future pages in the session.
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

}
