package northwind.data;

import java.util.List;

import northwind.model.Product;
import northwind.report.ProductSales;

public class ProductRepository extends AbstractJpaRepository<Product>{
	private static final long serialVersionUID = 1L;

	public ProductRepository() {
		super(Product.class);
	}
	
	public List<Product> findAllByCategoryId(int categoryId) {
		return getEntityManager()
				.createQuery("SELECT a FROM Product a WHERE a.category.categoryID = :idValue", Product.class)
				.setParameter("idValue", categoryId)
				.getResultList();
	}
	public List<Product> findTopTen(){
		return getEntityManager()
				.createQuery("SELECT p FROM Product p ORDER BY p.unitPrice DESC", Product.class)
				.setMaxResults(10)
				.getResultList();
	}
//	public List<ProductSales> findSalesYear(){
//		return getEntityManager()
//				.createQuery("SELECT new northwind.report.ProductSales(FROM Product p WHERE YEAR(p.year) = :year "
//						+ "ORDER BY SUM(OrderDetail.unitPrice * OrderDetail.quantity * (1-OrderDetail.discount))", Product.class)
//				.setParameter("year", 1997)
//				.getResultList();
//	}
	public List<ProductSales> findProductSales() {
		return getEntityManager().createQuery(
	"SELECT new northwind.report.ProductSales(p.productName, c.categoryName, SUM(od.unitPrice * od.quantity * (1-od.discount)) ) " 
	+ " FROM OrderDetail od, IN (od.product) p, IN (p.category) c "
	+ " WHERE YEAR(od.order.shippedDate) = :year "
	+ " GROUP BY p.productName , c.categoryName"
	+ " ORDER BY SUM(od.unitPrice * od.quantity * (1-od.discount)) DESC ",	
	
			ProductSales.class)
			.setParameter("year",1997)
			.getResultList();
	}
//	public List<ProductSales> findProductSales() {
//				List<ProductSales> resultList= getEntityManager().createNativeQuery(
//				"SELECT Products.ProductName, Categories.CategoryName, Sum([Order Details].UnitPrice * [Order Details].Quantity * (1-[Order Details].Discount)) AS ProductSale " + 
//				"FROM Products " + 
//				"INNER JOIN Categories " + 
//				"ON Products.CategoryID = Categories.CategoryID  " + 
//				"INNER JOIN [Order Details] " + 
//				"ON [Order Details].ProductID = Products.ProductID " + 
//				"INNER JOIN Orders " + 
//				"ON Orders.OrderID = [Order Details].OrderID " + 
//				"WHERE YEAR(Orders.ShippedDate) = 1997 " + 
//				"GROUP BY Products.ProductName, Categories.CategoryName " + 
//				"ORDER BY ProductSale DESC ")
//			    .getResultList();
//				return resultList;
//	}
}
