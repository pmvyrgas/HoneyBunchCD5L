package honeyyard;

import java.time.LocalDate;
import java.util.ArrayList;

public class Inventory {
    // attributes
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<ProductBatch> batches = new ArrayList<ProductBatch>();

    private int lowStockLimit;  // stock at or below this is low stock
    private int nearExpiryDays;// expiring within this many days is near expiry

    
	public Inventory() {
		this.products = new ArrayList<Product>();
        this.batches = new ArrayList<ProductBatch>();
        this.lowStockLimit = 5;
        this.nearExpiryDays = 30; // baguhin nyo as u see fit
    }

	/* method: find product (for other methods)
     * returns the product with this id, or null if there is none
     * used by the other methods to check if a product exists before adding, updating, or restocking
     */
	private Product findProduct(String productID) {
		for (Product product: products) {
			if (product.getProductId().equalsIgnoreCase(productID)) {
				return product;
			}
		}
		return null;
	}
	/* method: add product
     * checks the values, then makes the new product and adds it to the list
     * throws an error with a message if something is wrong (blank fields, price, duplicate id)
     */
	public void addProduct(Manager currentUser, Product newProduct)  { 
		if(currentUser == null || !currentUser.isAuthorized()) { //since only the manager is allowed to add products
			throw new SecurityException("Only managers can add new products. ");
		}
		
		//checks for blanks
		if(newProduct.getProductId() == null || newProduct.getProductId().trim().isEmpty() ||
		        newProduct.getName() == null || newProduct.getName().trim().isEmpty() ||
		        newProduct.getCategory() == null || newProduct.getCategory().trim().isEmpty() ||
		        newProduct.getDescription() == null || newProduct.getDescription().trim().isEmpty() ) {
			throw new IllegalArgumentException("All fields should be filled.");
		}

		// checks the price
        if (newProduct.getSellingPrice() <= 0){
            throw new IllegalArgumentException("Selling price must be greater than 0.");
		}
		
		//checks for the duplicates
		for(Product p : products) {
			if(p.getProductId().equalsIgnoreCase(newProduct.getProductId())) {
				throw new IllegalArgumentException("Product already exists. ");
			}
		}
		
		products.add(newProduct); //adds the product to the list if their is no error
	} 

	
	/* method: search products (for users)
     * returns all products whose name or id has the keyword in it, must not be case sensitive
     */
	public ArrayList<Product> searchProductsForUsers(String keyword ) {
		ArrayList<Product> matchingProducts = new ArrayList<Product>(); //temporary list to store the possible products
		
		if(keyword == null || keyword.trim().isEmpty()) {
			return matchingProducts; //returns empty if the search keyword is empty
		}
		
		String searchTerm = keyword.toLowerCase().trim();
		
		for(Product p: products) {
			if(p.getProductId().toLowerCase().contains(searchTerm) || p.getName().toLowerCase().contains(searchTerm)) { //since this is not case sensitive
				matchingProducts.add(p); //adds all the possible products in a temporary arraylist
			}
		}
		
		return matchingProducts; //returns all possible matching products
	}

	
	/* method: get products by category
     * returns all products in this category, for filtering
     */
	public ArrayList<Product> getProductsByCategory(String category){
	ArrayList<Product> categoryList = new ArrayList<Product>(); //temporary list to store the possible products
			if(category == null || category.trim().isEmpty()) {
				return categoryList; //returns empty if the search keyword is empty
			}
			for(Product p: products) {
				if(p.getCategory().toLowerCase().equalsIgnoreCase(category.trim())) { //since this is not case sensitive
					categoryList.add(p); //adds all the possible products in a temporary arraylist
				}
			}
		return categoryList; //returns all possible matching products
	}
	
	//// idk pa if hiwalay ko sila na method or 1 for all
	/* method: update name
     * changes only the product name
     * new name is checked the same way as in add product
     */
	public boolean updateName (String productID, String newName) {
		Product product = findProduct(productID); // find product first
		// checker for validity
		if (product == null) {
			return false;
		}
		if (newName == null || newName.trim().isEmpty()){ // if the new name is invalid
			throw new IllegalArgumentException("Product name cannot be empty!");
		}
		//set new name
		product.setName(newName.trim());
		return true; // successfully updated name
	}
	/* method: update category
     * changes only the product category
     * new category is checked the same way as in add product
     */
	public boolean updateCategory(String productID, String newCategory) {
		Product product = findProduct(productID); // find product first
		// checker for validity
		if (product == null) {
			return false;
		}
		if (newCategory == null || newCategory.trim().isEmpty()){ // if the new category is invalid
			throw new IllegalArgumentException("Product category cannot be empty!");
		}
		//set new category
		product.setCategory(newCategory.trim());
		return true; // successfully updated category
	}
	/* method: update description
     * changes only the description
     */
	public boolean updateDescription (String productID, String newDescription) {
		Product product = findProduct(productID); // find product first
		// checker for validity
		if (product == null) {
			return false;
		}
		if (newDescription == null || newDescription.trim().isEmpty()){ // if the new description is invalid
			throw new IllegalArgumentException("Product description cannot be empty!");
		}
		//set new description
		product.setDescription(newDescription.trim());
		return true; // successfully updated description
	}

	/* method: update price
     * changes only the selling price
     * new price is checked the same way as in add product
     */
	public boolean updatePrice(String productID, double newPrice) {
		Product product = findProduct(productID); // find product first
		// checker for validity
		if (product == null) {
			return false;
		}
		if (newPrice <= 0){ // new price must be greater than 0
			throw new IllegalArgumentException("Selling price must be greater than 0.");
		}
		//set new selling price of product
		product.setSellingPrice(newPrice);
		return true; // successfully updated price
	}

	/* method: add batch
     * checks the values, generates the batch id, then makes and adds the new batch
     * batch id is the product id + 4 digits starting from 0001 (ex. HNY01-0001)
     * expiration date can be null for products that don't expire
     */
	public void addBatch(Manager currentUser, String productID, int quantity, LocalDate expiryDate) {
		if (currentUser == null || !currentUser.isAuthorized()) {
			throw new SecurityException("Only managers can add new batches.");
		}
		
		Product product = findProduct(productID);
		if (product == null) {
			throw new IllegalArgumentException("Product with ID " + productID + " does not exist.");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("Batch quantity must be greater than 0.");
		}
		
		// count existing batches for this product to generate sequential 4-digit ID
		int count = 0;
		for (ProductBatch b : batches) {
			if (b.getProductId().equalsIgnoreCase(productID)) {
				count++;
			}
		}
	
		String batchId = String.format("%s-%04d", productID, count + 1);
		ProductBatch newBatch = new ProductBatch(batchId, productID, quantity, expiryDate);
		batches.add(newBatch);
	}

	/* method: deduct stock (for later in transactions)
     * removes sold items from the product's batches, oldest expiration first
     */
	public void deductStock(String productID, int quantityToDeduct) {
		if (quantityToDeduct <= 0) {
			throw new IllegalArgumentException("Quantity to deduct must be greater than 0.");
		}
		
		int availableStock = getTotalStock(productID);
		if (availableStock < quantityToDeduct) {
			throw new IllegalArgumentException("Insufficient non-expired stock for product: " + productID);
		}

		ArrayList<ProductBatch> productBatches = getBatchesOfProduct(productID);
		LocalDate today = LocalDate.now();

		// remove expired batches from consideration for sale
		productBatches.removeIf(b -> b.getExpiryDate() != null && b.getExpiryDate().isBefore(today));

		// sort by expiry date oldest first (null expiry dates placed last)
		productBatches.sort((b1, b2) -> {
			if (b1.getExpiryDate() == null) {
				return 1;
			}
			if (b2.getExpiryDate() == null) {
				return -1;
			}
			return b1.getExpiryDate().compareTo(b2.getExpiryDate());
		});

		int remainingToDeduct = quantityToDeduct;
		for (ProductBatch b : productBatches) {
			if (remainingToDessi <= 0 && remainingToDeduct <= 0) {
				break; // safety guard
			}
			if (remainingToDeduct <= 0) {
				break;
			}

			int currentQty = b.getQuantity();
			if (currentQty <= remainingToDeduct) {
				remainingToDeduct -= currentQty;
				b.setQuantity(0);
			} else {
				b.setQuantity(currentQty - remainingToDeduct);
				remainingToDeduct = 0;
			}
		}
	}
	
	/////////////////
	
	/* method: get batches of product
     * returns all the batches of one product
     */
	
	/* method: get total stock
     * adds up the remaining quantity of all the product's batches that are not expired
     */
	
	
	/* method: get low stock products
     * products with stock more than 0 but at or below low stock limit
     * tells the manager what to restock soon
     */
	
	 /* method: get out of stock products
     * products with 0 stock, need restocking right away 
     */
	
	/* method: get expired batches
     * batches that are expired but still have items left, should be removed from the shelf and not sold
     */
	
	 /* method: get near expiry batches
     * batches that will expire soon and still have items left, should be sold first or put on sale
     */
	
	 /* method: get all batches
     * returns the whole batch list (all delivery records for later)
     */
	
	 /* method: displayBatches
     * prints all batches, one per line (temporary, before GUI)
     */
	
	
	// NOTE: saving and loading (persistence) - for later, need natin gumawa ng test text files pa for this eh
	/// MORE: wala pa k super nilalagay sa handling ng expired stuff like iniisip q pa if need ba manually iremove pa ng manager or basta gawin natin silang inacessible for transactiONS later
	
}
