package northwind.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.NoResultException;


import northwind.model.Order;
import northwind.report.MonthlySalesForYear;


public class OrderRepository extends AbstractJpaRepository<Order>{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger log;
	public OrderRepository() {
		super(Order.class);
	}
	
	public List<Order> findAllByCustomerId(String customerId) {
		return getEntityManager()
				.createQuery("SELECT a FROM Order a WHERE a.customer.customerID = :idValue", Order.class)
				.setParameter("idValue", customerId)
				.getResultList();
	}
	
	public List<Order> findAllByEmployeeId(int employeeId) {
		return getEntityManager()
				.createQuery("SELECT a FROM Order a WHERE a.employee.employeeID = :idValue", Order.class)
				.setParameter("idValue", employeeId)
				.getResultList();
	}
	public Order findOne (int orderId) {
		Order singleResult;
		try {
			singleResult = getEntityManager().createQuery(
					"SELECT o FROM Order o JOIN FETCH o.orderDetail WHERE o.orderId = :idValue", Order.class)
					.setParameter("idValue", orderId)
					.getSingleResult();
		}catch(NoResultException nre) {
			singleResult = null;
			log.info(nre.getMessage());
		}
		return singleResult;
	}

public Double findSalesAmountForYearAndMonth(int year, int month) {
	return getEntityManager().createQuery(
			" SELECT SUM(od.unitPrice * od.quantity * (1-od.discount)) As TotalSales " 
			+" FROM OrderDetail od, IN (od.order) o "
			+" WHERE Year(o.shippedDate) = :year and MONTH(o.shippedDate) = :month  "
			,Double.class)
			.setParameter("year", year)
			.setParameter("month", month)
			.getSingleResult();	
}

	public List<MonthlySalesForYear> findMonthSales(int year) {
		List<MonthlySalesForYear> monthlysalesforyear = new ArrayList<>();
		for(int month=1; month<=12; month++){
			Double monthlySales = findSalesAmountForYearAndMonth(year, month);
			if (monthlySales == null) {
				monthlySales = Double.valueOf(0);
			}
	        MonthlySalesForYear monthlydata =  new MonthlySalesForYear(monthlySales, month - 1, year);
	        monthlysalesforyear.add(monthlydata);
	             
	   }
		return monthlysalesforyear;
						
	}
	
	public List<Order> findAllByCustomerId(int customerId) {
		return getEntityManager().createQuery(
			"SELECT od FROM Order inv WHERE od.customer.customerID = :customerIdValue", 
			Order.class)
			.setParameter("customerIdValue", customerId)
			.getResultList();
	}
	
}
