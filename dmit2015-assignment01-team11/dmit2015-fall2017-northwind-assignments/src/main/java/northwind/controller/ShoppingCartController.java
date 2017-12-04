package northwind.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.el.functions.Numbers;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import northwind.exception.illegalQuantityException;
import northwind.exception.NoOrderDetailsException;
import northwind.service.OrderService;
import northwind.service.ProductService;
import northwind.data.CustomerRepository;
import northwind.model.Customer;
import northwind.model.OrderDetail;
import northwind.model.Product;


@SuppressWarnings("serial")
@Named
@SessionScoped
public class ShoppingCartController implements Serializable {
private Set<OrderDetail> items = new HashSet<>();	// +getter
	
	@Inject
	private ProductService productService;
	
	@NotNull(message="ProductId field value is required")
	private Integer currentProductId;						// +getter +setter
	
	@NotNull(message="Customer field value selection is required")
	private Integer currentSelectedCustomerId;			// +getter +setter
	
	@Inject
	private CustomerRepository customerRepository;
	
	private String billingAddress;						// +getter +setter
	private String billingName;						// +getter +setter
	private String billingCity;							// +getter +setter
	private String billingRegion;						// +getter +setter
	private String billingCountry;						// +getter +setter
	private String billingPostalCode;					// +getter +setter
	
	@Inject
	private OrderService orderService;
	
	public void changeBillingInfo() {
		int customerId = currentSelectedCustomerId;
		Customer invoiceCustomer = customerRepository.find(customerId);
		billingName = invoiceCustomer.getCompanyName();
		billingAddress = invoiceCustomer.getAddress();
		billingCity = invoiceCustomer.getCity();
		billingRegion = invoiceCustomer.getRegion();
		billingCountry = invoiceCustomer.getCountry();
		billingPostalCode = invoiceCustomer.getPostalCode();
	}
	
	public void addItemWithProductId() {
		Product currentProduct = productService.findOne(currentProductId);
		if (currentProduct != null) {
			addItem(currentProduct);	
		} else {
			Messages.addGlobalError("Invalid productId {0}", currentProductId);
		}
	}
	
	public String addItemWithProductIdQueryParameter() {	
		String productIdParam = Faces.getRequestParameter("productId");
		if( productIdParam != null && !productIdParam.isEmpty() ) {
			int productId = Integer.parseInt(productIdParam);
			Product currentProduct = productService.findOne(productId);
			if( currentProduct != null ) {
				addItem(currentProduct);
			} else {
				Messages.addGlobalError("Invalid productId {0}", currentProductId);
			}
		}
		return "/public/transaction/shoppingCart.xhtml?faces-redirect=true";
	}
	
	public String addItem(Product currentProduct) {
		OrderDetail item = new OrderDetail();
		item.setProduct(currentProduct);
		item.setQuantity((short) 1);
		item.setUnitPrice( currentProduct.getUnitPrice() );
		// Add item to shopping cart
		if (!items.add(item)) {
			// Item is already in the shopping cart
			// Get existing item and increment quantity by 1
			OrderDetail existingItem = items.stream().filter( 
					singleItem -> singleItem.getProduct().getProductID() == currentProduct.getProductID() )
					.findFirst().orElse(null);
			if (existingItem != null) {
				existingItem.setQuantity( (short) (existingItem.getQuantity() + 1));
				Messages.addFlashGlobalInfo("Item quantity was updated");
			}
		} else {
			Messages.addFlashGlobalInfo("Item was added to cart");
		}
		
		// return navigation to the page shoppingBag.xhtml
		return "/public/transaction/shoppingCart.xhtml?faces-redirect=true";
	}
	
	public void removeItem(OrderDetail item) {
		items.remove(item);
	}
	
	public void emptyCart() {
		items.clear();
	}

	public void submitOrder() {
		try {
			int customerId = currentSelectedCustomerId;
			Customer invoiceCustomer = customerRepository.find(customerId);
		
			int orderId = orderService.createOrder(
					invoiceCustomer,
					billingName,
					billingAddress,
					billingCity,
					billingRegion,
					billingCountry,
					billingPostalCode,
					new ArrayList<>(items));
			Messages.addGlobalInfo("Successfully created order #{0}", orderId);

			// clear the form field values
			currentSelectedCustomerId = null;
			billingName = null;
			billingAddress = null;
			billingCity = null;
			billingRegion = null;
			billingCountry = null;
			billingPostalCode = null;
			// empty the shopping cart
			items.clear();			
		} catch( NoOrderDetailsException | illegalQuantityException e ) {
			Messages.addGlobalError(e.getMessage());
		} catch( Exception e ) {
			Messages.addGlobalError("Create order was not successful");
		}
	}
	
	public Set<OrderDetail> getItems() {
		return items;
	}
	
	
	public Integer getCurrentProductId() {
		return currentProductId;
	}

	public void setCurrentProductId(Integer currentProductId) {
		this.currentProductId = currentProductId;
	}

	public Integer getCurrentSelectedCustomerId() {
		return currentSelectedCustomerId;
	}

	public void setCurrentSelectedCustomerId(Integer currentSelectedCustomerId) {
		this.currentSelectedCustomerId = currentSelectedCustomerId;
	}
	
	public String getBillingName() {
		return billingName;
	}

	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	
	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingRegion() {
		return billingRegion;
	}

	public void setBillingRegion(String billingRegion) {
		this.billingRegion = billingRegion;
	}

	public String getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	public String getBillingPostalCode() {
		return billingPostalCode;
	}

	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}

	public String getTotalPrice() {
		double totalPrice = 0;
		for(OrderDetail item : items) {
			totalPrice += item.getQuantity() * item.getUnitPrice().doubleValue();
		}
		return Numbers.formatCurrency(totalPrice, "$");
	}

}
