package northwind.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.report.MonthlySalesForYear;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class OrderController implements Serializable {
	
	private Integer currentSelectedCustomerId;	// +getter +setter
	private List<Order> invoicesByCustomer;
	
	@NotNull(message="InvoiceId field value is required")
	private Integer currentSelectedOrderId;
	private Order currentSelectedOrder;
	
	public void findOrder() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( currentSelectedOrderId > 0 ) {
				currentSelectedOrder = orderRepository.find(currentSelectedOrderId);
				if( currentSelectedOrder == null ) {
					Messages.addGlobalInfo("There is no order with orderID {0}", 
							currentSelectedOrderId);
				} else {
					Messages.addGlobalInfo("Successfully retreived order info.");
				}
			} else {
				Messages.addGlobalError("Bad request. A valid orderID is required.");
			}
		}		
	}
	
	public void findOneOrder() {
		currentSelectedOrder = orderRepository.find(currentSelectedOrderId);
		if(currentSelectedOrder == null) {
			Messages.addGlobalInfo("There is no Order with OrderID{0}", currentSelectedOrderId);
		}else {
			Messages.addGlobalInfo("we got 1 result with OrderID", currentSelectedOrderId);
		}
	}
	
	public void findOneOrder(int orderId) {
		currentSelectedOrderId = orderId;
		findOneOrder();
	}
	
	public void findAllInvoicesByCustomer() {
		invoicesByCustomer = orderRepository.findAllByCustomerId(currentSelectedCustomerId);
		currentSelectedOrder = null;
		int resultCount = invoicesByCustomer.size();
		if (invoicesByCustomer.size() == 0) {
			Messages.addGlobalError("Unknown customerId \"{0}\". We found 0 results", currentSelectedCustomerId);
		} else {
			Messages.addGlobalInfo("We found {0} results.", resultCount);
		}
	}
	
	@Inject
	private OrderRepository orderRepository;
	
	private List<Order> orders;
	
	@PostConstruct
	void init() {
		orders = orderRepository.findAll();
	}
	
	public List<Order> getOrders () {
		return orders;
	}
	
	public Integer getCurrentSelectedCustomerId() {
		return currentSelectedCustomerId;
	}

	public void setCurrentSelectedCustomerId(Integer currentSelectedCustomerId) {
		this.currentSelectedCustomerId = currentSelectedCustomerId;
	}
	
	public Integer getCurrentSelectedOrderId() {
		return currentSelectedOrderId;
	}

	public void setCurrentSelectedOrderId(Integer currentSelectedOrderId) {
		this.currentSelectedOrderId = currentSelectedOrderId;
	}

	public Order getCurrentSelectedOrder() {
		return currentSelectedOrder;
	}
	
	public double getSubtotal() {
		double subtotal = 0;
		for(OrderDetail orderdetail : currentSelectedOrder.getOrderDetails()) {
            subtotal += orderdetail.getUnitPrice().doubleValue() * orderdetail.getQuantity();
        }
		return subtotal;
	}
	
	public double getTotal() {
		double subtotal = 0;
		for(OrderDetail orderdetail : currentSelectedOrder.getOrderDetails()) {
            subtotal += orderdetail.getUnitPrice().doubleValue() * orderdetail.getQuantity();
        }
		double total = subtotal + currentSelectedOrder.getFreight().doubleValue();
		return total;
	}
	public List<MonthlySalesForYear> retrieveMonthlySales1997() {
		return orderRepository.findMonthSales(1997);
	}
	public List<MonthlySalesForYear> retrieveMonthlySales1996() {
			return orderRepository.findMonthSales(1996);
		}
	public List<MonthlySalesForYear> retrieveMonthlySales1998() {
		return orderRepository.findMonthSales(1998);
	}
}
