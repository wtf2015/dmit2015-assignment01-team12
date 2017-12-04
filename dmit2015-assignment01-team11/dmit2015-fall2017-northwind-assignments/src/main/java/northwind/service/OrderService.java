package northwind.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;


import northwind.exception.NoOrderDetailsException;
import northwind.exception.illegalQuantityException;
import northwind.model.Customer;
import northwind.model.Order;
import northwind.model.OrderDetail;

@Stateless
public class OrderService {
	@Resource
	private EJBContext context;
	
	@Inject
	private EntityManager entityManager;
	
	public int createOrder(
			Customer orderCustomer,
			String billingName,
			String billingAddress,
			String billingCity,
			String billingRegion,
			String billingCountry,
			String billingPostalCode,
			List<OrderDetail> items
			) throws NoOrderDetailsException, illegalQuantityException  {
		int orderId = 0;
		
		if (items == null || items.size() == 0) {
			context.setRollbackOnly();
			throw new NoOrderDetailsException("There are no items in the order");			
		}
		
		Order newOrder = new Order();
		newOrder.setCustomer(orderCustomer);
		newOrder.setShipName(billingName);
		newOrder.setShipAddress(billingAddress);
		newOrder.setShipCity(billingCity);
		newOrder.setShipRegion(billingRegion);
		newOrder.setShipCountry(billingCountry);
		newOrder.setShipPostalCode(billingPostalCode);
		
		// assign the invoiceDate and total
		Date today = Calendar.getInstance().getTime();
		newOrder.setOrderDate(new Timestamp(today.getTime()));
		// calculate the invoice total
		double total = 0;
		for(OrderDetail singleItem : items) {
			total += singleItem.getQuantity() * singleItem.getUnitPrice().doubleValue();
		}
		// persist the invoice
		entityManager.persist(newOrder);
		// get the system generated invoiceId 
		orderId = newOrder.getOrderID();
		
		// iterate through each InvoiceLine and persist it
		for (OrderDetail singleItem : items) {
			// rollback the transaction if quantity is less than one
			if (singleItem.getQuantity() < 1) {
				context.setRollbackOnly();
				throw new illegalQuantityException("Invalid quantity ordered.");
			}
			// set the invoice of each InvoiceLine
			singleItem.setOrder(newOrder);
			// persist the InvoiceLine
			entityManager.persist(singleItem);
		}		
		
		return orderId;
	}

}
