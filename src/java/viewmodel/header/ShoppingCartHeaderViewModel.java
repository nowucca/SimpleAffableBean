package viewmodel.header;

import business.cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;

/**
 */
public class ShoppingCartHeaderViewModel extends CartAwareHeaderViewModel {

    protected HttpServletRequest request;
    protected ShoppingCart cart;

    ShoppingCartHeaderViewModel(HttpServletRequest request) {
        super(request);
    }


    public boolean getIsViewable() {
        /*
        Replaces: <c:if test="${!empty cart && cart.numberOfItems != 0 &&
                                  !(fn:contains(pageContext.request.method, 'GET') &&
                                   fn:contains(pageContext.request.servletPath,'/cart'))}">
         */
        return  (getHasCart() && getNumberOfItems() != 0 && !isRequestingPage("GET", "/cart"));
    }


    public String getItemsTextKey() {
        /*
        Replaces:
          <%-- Handle singular/plural forms of 'item' --%>
                      <c:choose>
                        <c:when test="${cart.numberOfItems == 1}">
                          <fmt:message key="item" />
                        </c:when>
                        <c:when test="${cart.numberOfItems == 2 ||
                                        cart.numberOfItems == 3 ||
                                        cart.numberOfItems == 4}">
                          <fmt:message key="items2-4" />
                        </c:when>
                        <c:otherwise>
                          <fmt:message key="items" />
                        </c:otherwise>
                      </c:choose>
         */
        String result;

        switch (getNumberOfItems()) {
            case 1: {
                result = "item";
                break;
            }
            case 2:
            case 3:
            case 4: {
                result = "items2-4";
                break;
            }
            default:
                result = "items";
                break;
        }
        return result;
    }
}
