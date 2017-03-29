<%@ tag pageEncoding="UTF-8" %> <%-- required --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="page" type="viewmodel.BaseViewModel" required="true" %>

<div class="headerWidget">

  <%-- If servlet path contains '/confirmation', do not display language toggle --%>
  <c:if test="${page.header.languageSelectionHeader.isVisible}">

    <%-- language selection widget --%>
    <c:choose>
      <%-- When user hasn't explicitly set language, render toggle according to browser's preferred locale --%>
      <c:when test="${!page.hasCustomerSpecifiedLocale}">
        <c:choose>
          <c:when test="${!page.isRequestLocaleCzech}">
            english
          </c:when>
          <c:otherwise>
            <c:url var="language_url" value="chooseLanguage">
              <c:param name="language" value="en"/>
              <c:param name="relativeReturnUrl" value="${page.header.relativeReturnUrl}"/>
            </c:url>
            <div class="bubble"><a href="${language_url}">english</a></div>
          </c:otherwise>
        </c:choose>

        <c:choose>
          <c:when test="${page.isRequestLocaleCzech}">
            česky
          </c:when>
          <c:otherwise>
            <c:url var="language_url" value="chooseLanguage">
              <c:param name="language" value="cs"/>
              <c:param name="relativeReturnUrl" value="${page.header.relativeReturnUrl}"/>
            </c:url>
            <div class="bubble"><a href="${language_url}">česky</a></div>
          </c:otherwise>
        </c:choose>
      </c:when>

      <%-- Otherwise, render widget according to the set locale --%>
      <c:otherwise>
        <c:choose>
          <c:when test="${!page.customerSpecifiedLocaleCzech}">
            english
          </c:when>
          <c:otherwise>
            <c:url var="language_url" value="chooseLanguage">
              <c:param name="language" value="en"/>
              <c:param name="relativeReturnUrl" value="${page.header.relativeReturnUrl}"/>
            </c:url>
            <div class="bubble"><a href="${language_url}">english</a></div>
          </c:otherwise>
        </c:choose> |

        <c:choose>
          <c:when test="${page.customerSpecifiedLocaleCzech}">
            česky
          </c:when>
          <c:otherwise>
            <c:url var="language_url" value="chooseLanguage">
              <c:param name="language" value="cs"/>
              <c:param name="relativeReturnUrl" value="${page.header.relativeReturnUrl}"/>
            </c:url>
            <div class="bubble"><a href="${language_url}">česky</a></div>
          </c:otherwise>
        </c:choose>
      </c:otherwise>
    </c:choose>

  </c:if>
</div>
