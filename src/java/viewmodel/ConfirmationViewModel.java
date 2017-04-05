package viewmodel;

import business.order.CustomerOrderDetails;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@SuppressWarnings("unchecked")
public class ConfirmationViewModel extends BaseViewModel {

    private Long orderId;
    private CustomerOrderDetails orderDetails;


    public ConfirmationViewModel(HttpServletRequest request) {
        super(request);

        this.orderId = (Long) session.getAttribute("customerOrderId");
        // get order details
        this.orderDetails = getCustomerOrderService().getOrderDetails(orderId);
    }

    public Long getOrderId() {
        return orderId;
    }

    public CustomerOrderDetails getOrderDetails() {
        return orderDetails;
    }
}
