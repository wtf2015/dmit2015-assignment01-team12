package northwind.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import northwind.data.CategoryRepository;
import northwind.data.ProductRepository;
import northwind.data.SupplierRepository;
import northwind.model.Category;
import northwind.model.Product;
import northwind.model.Supplier;


@Stateless
public class ProductService {
	
	@Inject
	private ProductRepository productRepository;
	
	@Inject
	private SupplierRepository supplierRepository;
	
	@Inject
	private CategoryRepository categoryRepository;
	
	@Inject
	private Logger log;
	
	public Product findOne(int productId) {
		Product currentProduct = null;
		try {
			currentProduct = productRepository.find(productId);
		} catch(NoResultException nre) {
			currentProduct = null;
		}
		return currentProduct;
	}
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	
	
	
	private void createProduct(Product newProduct) {
		try {
			productRepository.persist(newProduct);
		} catch(Exception e) {
			
		}
	}
	
	public void createProduct(Product newProduct, Integer supplierId, Integer categoryId) {
		if (supplierId != null) {
			Supplier currentSupplier = supplierRepository.find(supplierId);
			newProduct.setSupplier(currentSupplier);
		}
		if (categoryId != null) {
			Category currentCategory = categoryRepository.find(categoryId);
			newProduct.setCategory(currentCategory);
		}
		createProduct(newProduct);
	}

}
