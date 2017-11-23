package northwind.data;

import java.util.List;

import northwind.model.Employee;
import northwind.report.EmployeeSales;

public class EmployeeRepository extends AbstractJpaRepository<Employee> {
	private static final long serialVersionUID = 1L;

	public EmployeeRepository() {
		super(Employee.class);
	}
	
	public List<EmployeeSales> findEmployeeSales() {
		return getEntityManager().createQuery(
"SELECT new northwind.report.EmployeeSales(Concat(e.firstName, ' ' , e.lastName), SUM(od.unitPrice * od.quantity * (1-od.discount)) ) " 
	+ " FROM OrderDetail od, IN (od.order) o, IN (o.employee) e "
	+ " WHERE YEAR(od.order.shippedDate) = :year "
	+ " GROUP BY Concat(e.firstName, ' ' , e.lastName) "
	+ " ORDER BY SUM(od.unitPrice * od.quantity * (1-od.discount)) DESC",
	
			EmployeeSales.class)
			.setParameter("year",1997)
			.getResultList();
	}
	
}
