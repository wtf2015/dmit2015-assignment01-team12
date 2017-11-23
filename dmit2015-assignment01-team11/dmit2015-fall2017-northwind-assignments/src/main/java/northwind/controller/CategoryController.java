package northwind.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.Part;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;

import northwind.data.CategoryRepository;
import northwind.model.Category;
import northwind.report.CategorySales;
import northwind.service.CategoryService;

@Model
public class CategoryController{
	
	private Part uploadedFile;
	
	@Inject
	private CategoryRepository categoryRepository;

	private List<Category> categorys;
	
	@PostConstruct
	void init() {
		categorys = categoryRepository.findAll();
	}

	public List<Category> getCategorys() {
		return categorys;
	}
	
	public List<CategorySales> retreiveCategorySales() {
		return categoryRepository.findCategorySales();
	}	
	
	@Inject
	private CategoryService categoryService;
	
	@NotBlank(message="Category Name is required.")
	@Length(min=3, max=15, message="Category Name must be 3 to 15 characters in length.")
	private String categoryName;
	
	private String description;
	
	public void createNewCategory() {
		byte[] picture = null;
		if (uploadedFile !=null) {
			long size = uploadedFile.getSize();
			InputStream content;
			try {
				content = uploadedFile.getInputStream();
				picture = new byte[(int) size];
				content.read(picture);
			}catch(Exception e) {
				Messages.addGlobalError("File upload was not successful.");
			}
		}
		
		try {
			categoryService.createCategory(categoryName, description, picture);
			Messages.addGlobalInfo("Create category successful.");
			categoryName = "";
		} catch(Exception e) {
			Messages.addGlobalError("Error creating category with exception: {0}", 
					 e.getMessage());
		}
		
		
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
}
