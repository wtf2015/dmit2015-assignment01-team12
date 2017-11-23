package northwind.controller;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;



@Model
public class OrdersByCustomerController {
	@Inject
	private OrderRepository orderRepository;
	
	private List<Order> orders;
	
	private String selectedCustomerId;
	
	private String selectedCustomerName;
	
	public void findOrdersByCustomer() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( selectedCustomerId == null ) {
//				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//				FacesContext.getCurrentInstance().responseComplete();
//				ec.redirect(ec.getRequestContextPath() + "/pages/assignment02/productlines.xhtml");
				String message = String.format("Bad request. Unknown orderId %s", selectedCustomerId);
				Messages.addGlobalInfo(message);
			} else {
				orders = orderRepository.findAllByCustomerId(selectedCustomerId);
				if( orders == null || orders.size() == 0 ) {
					String message = String.format("There are no orders associated with customerId %s", selectedCustomerId);
					Messages.addGlobalInfo(message);
				}
				
			}
			
		}
	}

	public List<Order> getOrders() {
		return orders;
	}

	public String getSelectedCustomerId() {
		return selectedCustomerId;
	}
	

	public void setSelectedCustomerId(String selectedCustomerId) {
		this.selectedCustomerId = selectedCustomerId;
	}
}
