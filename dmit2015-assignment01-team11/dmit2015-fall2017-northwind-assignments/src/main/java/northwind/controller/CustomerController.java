package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.CustomerRepository;
import northwind.model.Customer;
import northwind.service.CustomerService;



@Model
public class CustomerController {
	private String currentSelectedCustomerId;		// getter/setter
	private Customer currentSelectedCustomer;	// getter
	
	public void findCustomer() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( currentSelectedCustomerId != null ) {
				currentSelectedCustomer = customerRepository.find(currentSelectedCustomerId);
				if( currentSelectedCustomer == null ) {
					Messages.addGlobalInfo("There is no customer with customerID {0}", 
							currentSelectedCustomerId);
				} else {
					Messages.addGlobalInfo("Successfully retreived customer info.");
				}
			} else {
				Messages.addGlobalError("Bad request. A valid customerID is required.");
			}
		}		
	}
	
	@Inject
	private CustomerRepository customerRepository;

	private List<Customer> customers;
	
	@PostConstruct
	void init() {
		customers = customerRepository.findAll();
	}

	public List<Customer> getCustomer() {
		return customers;
	}
	
	public String getCurrentSelectedCustomerId() {
		return currentSelectedCustomerId;
	}

	public void setCurrentSelectedCustomerId(String currentSelectedCustomerId) {
		this.currentSelectedCustomerId = currentSelectedCustomerId;
	}

	public Customer getCurrentSelectedCustomer() {
		return currentSelectedCustomer;
	}
	
	private Customer newCustomer = new Customer();

	@Inject
	private CustomerService customerService;

	public void createNewCustomer()
	{
		try
		{
			customerService.createCustomer(newCustomer);
			Messages.addGlobalInfo("New customer created.");
		}
		catch(Exception e)
		{
			Messages.addGlobalError("Error creating customer.:{0}", 
					e.getMessage());
		}
	}

	public Customer getNewCustomer() {
		return newCustomer;
	}

	public void setNewCustomer(Customer newCustomer) {
		this.newCustomer = newCustomer;
	}

}
