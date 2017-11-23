package northwind.controller;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.ProductRepository;
import northwind.model.Product;

@Model
public class ProductsByCategoryController {
	
	@Inject
	private ProductRepository productRepository;
	
	private List<Product> products;
	
	private int selectedCategoryId;
	
	private String selectedCategoryName;
	
	public void findProductsByCategory() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( selectedCategoryId == 0 ) {
//				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//				FacesContext.getCurrentInstance().responseComplete();
//				ec.redirect(ec.getRequestContextPath() + "/pages/assignment02/productlines.xhtml");
				String message = String.format("Bad request. Unknown productId %s", selectedCategoryId);
				Messages.addGlobalInfo(message);
			} else {
				products = productRepository.findAllByCategoryId(selectedCategoryId);
				if( products == null || products.size() == 0 ) {
					String message = String.format("There are no products associated with categoryId %s", selectedCategoryId);
					Messages.addGlobalInfo(message);
				}
				
			}
			
		}
	}

	public List<Product> getProducts() {
		return products;
	}

	public int getSelectedCategoryId() {
		return selectedCategoryId;
	}
	

	public void setSelectedCategoryId(int selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
	}
}
