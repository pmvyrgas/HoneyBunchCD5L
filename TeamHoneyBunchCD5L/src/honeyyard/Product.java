package honeyyard;
import java.io.Serializable;

public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L; 
	
	private String productId;
	private String productName;
	private String productCategory;
	private String productDescription;
	private double sellingPrice;

	
    // pam note: added validations
	 public Product(String productId, String name, String category, String description, double price) {
		 this.productId = requireText(productId, "a product ID");
	     this.productName = requireText(name, "a product name");
	     this.productCategory = requireText(category, "a category");
	     this.productDescription = requireText(description, "a description");
	     this.sellingPrice = requirePrice(price);
	 }
	 
	// throws error if attribute is blank, returns the cleaned up text
	// pam note: added this helper for cleaner and reusable validation
	private String requireText(String value, String attribute){
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException("Please enter " + attribute + ".");
		}
		return value.trim();
	}
		
	// returns the price, or throws if it's not more than 0
	// pam note: added this helper for cleaner and reusable validation
	private double requirePrice(double price) {
		if (Double.isNaN(price) || price <= 0) {
			throw new IllegalArgumentException("Price must be more than 0.");
		}
		return price;
	}
		
 	//getters
	 public String getProductId() {
	     return productId;
	 }
	
	 public String getName() {
	     return productName;
	 }
	
	 public String getCategory() {
	     return productCategory;
	 }
	
	 public String getDescription() {
	     return productDescription;
	 }
	
	 public double getSellingPrice() {
	     return sellingPrice;
	 }
	
	 // pam note: added validation here too so user cant set to blank
	 void setName(String name) {
	     this.productName = requireText(name, "a product name");
	 }
	
	 void setCategory(String category) {
	     this.productCategory = requireText(category, "a category");
	 }
	
	 void setDescription(String description) {
	     this.productDescription = requireText(description, "a description");
	 }
	
	 void setSellingPrice(double sellingPrice) {
	     this.sellingPrice = requirePrice(sellingPrice);
	 }
	 
	 // method: display all product info
	void displayInfo(){
	     System.out.println("Product ID:  " + productId);
	     System.out.println("Name:	" + productName);
	     System.out.println("Category:	" + productCategory);
	     System.out.println("Description:	" + productDescription);
	     System.out.println("Price:	PHP " + sellingPrice);
	 }
	 
	 // temporary printing format
	 public String toString() {
	        return productId + " - " + productName + " [" + productCategory + "] PHP" + sellingPrice;
	    }

}
