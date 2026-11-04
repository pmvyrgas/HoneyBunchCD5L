package honeyyard;

public class Product {
	private String productId;
	private String productName;
	private String productCategory;
	private String productDescription;
	private double sellingPrice;

	
	 public Product(String productId, String name, String category, String description, double price) {
		// siguro maglagay tayo validation here
		 this.productId = productId;
	     this.productName = name;
	     this.productCategory = category;
	     this.productDescription = description;
	     this.sellingPrice = price;
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
	
	 void setName(String name) {
	     this.productName = name;
	 }
	
	 void setCategory(String category) {
	     this.productCategory = category;
	 }
	
	 void setDescription(String description) {
	     this.productDescription = description;
	 }
	
	 void setSellingPrice(double sellingPrice) {
	     this.sellingPrice = sellingPrice;
	 }
	 
	 // method: display all product info
	 
	 // temporary printing format
	 public String toString() {
	        return productId + " - " + productName + " [" + productCategory + "] PHP" + sellingPrice;
	    }

}
