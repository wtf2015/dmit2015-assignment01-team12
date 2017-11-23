package northwind.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import northwind.data.ProductRepository;
import northwind.model.Product;
import northwind.report.ProductSales;
import northwind.service.ProductService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ProductController implements Serializable {
	@Inject
	private Logger log;
	
	@Inject
	private ProductService productService;
	
	private List<Product> products;		
	
	private Product currentNewProduct = new Product();
	private Integer currentSelectedSupplierId;
	private Integer currentSelectedCategoryId;
	
	private int currentSelectedProductId;		// getter/setter
	private Product currentSelectedProduct;	// getter
	

	public void findProduct() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( currentSelectedProductId > 0 ) {
				currentSelectedProduct = productRepository.find(currentSelectedProductId);
				if( currentSelectedProduct == null ) {
					Messages.addGlobalInfo("There is no customer with ProductID {0}", 
							currentSelectedProductId);
				} else {
					Messages.addGlobalInfo("Successfully retreived product info.");
				}
			} else {
				Messages.addGlobalError("Bad request. A valid productID is required.");
			}
		}		
	}
	@Inject
	private ProductRepository productRepository;
	
	
	@PostConstruct
	void init() {
		products = productRepository.findAll();
	}
	public List<Product> getProducts() {
		return products;
	}
	
	public Product getCurrentNewProduct() {
		return currentNewProduct;
	}

	public void setCurrentNewProduct(Product currentNewProduct) {
		this.currentNewProduct = currentNewProduct;
	}
	
	public Integer getCurrentSelectedSupplierId() {
		return currentSelectedSupplierId;
	}

	public void setCurrentSelectedSupplierId(Integer currentSelectedSupplierId) {
		this.currentSelectedSupplierId = currentSelectedSupplierId;
	}

	public Integer getCurrentSelectedCategoryId() {
		return currentSelectedCategoryId;
	}

	public void setCurrentSelectedcategoryId(Integer currentSelectedCategoryId) {
		this.currentSelectedCategoryId = currentSelectedCategoryId;
	}
	
	//Top Ten
	public List<Product> getTopProducts(){
		return productRepository.findTopTen();
	}
	//Top Sale for 1997
	public List<ProductSales> retreiveYearProducts(){
		return productRepository.findProductSales();
	}
	
	public int getCurrentSelectedProductId() {
		return currentSelectedProductId;
	}

	public void setCurrentSelectedProductId(int currentSelectedProductId) {
		this.currentSelectedProductId = currentSelectedProductId;
	}

	public Product getCurrentSelectedProduct() {
		return currentSelectedProduct;
	}
	
	public void createNewProduct() {
		try {
			productService.createProduct(currentNewProduct, currentSelectedSupplierId, currentSelectedCategoryId);
			Messages.addGlobalInfo("Create product was successful.");
			currentNewProduct = new Product();
		} catch(Exception e) {
			Messages.addGlobalError("Create product was not successful");
			log.info(e.getMessage());
		}
	}
}
