package northwind.controller;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;


@Model
public class OrdersByEmployeeController {
	@Inject
	private OrderRepository orderRepository;
	
	private List<Order> orders;
	
	private int selectedEmployeeId;

	
	public void findOrdersByEmployee() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( selectedEmployeeId == 0 ) {
//				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//				FacesContext.getCurrentInstance().responseComplete();
//				ec.redirect(ec.getRequestContextPath() + "/pages/assignment02/productlines.xhtml");
				String message = String.format("Bad request. Unknown orderId %s", selectedEmployeeId);
				Messages.addGlobalInfo(message);
			} else {
				orders = orderRepository.findAllByEmployeeId(selectedEmployeeId);
				if( orders == null || orders.size() == 0 ) {
					String message = String.format("There are no orders associated with employeeid %s", selectedEmployeeId);
					Messages.addGlobalInfo(message);
				}
				
			}
			
		}
	}

	public List<Order> getOrders() {
		return orders;
	}

	public int getSelectedEmployeeId() {
		return selectedEmployeeId;
	}
	

	public void setSelectedEmployeeId(int selectedEmployeeId) {
		this.selectedEmployeeId = selectedEmployeeId;
	}
}
