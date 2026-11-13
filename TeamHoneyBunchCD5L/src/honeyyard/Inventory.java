package honeyyard;

import java.time.LocalDate;
import java.util.ArrayList;

// imports for save and load
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
// change the current systempprintln errors in file handling to use throw exception in prep for gui
import java.io.UncheckedIOException;

public class Inventory {
    private String fileName; // where the inventory is saved
    
    // attributes
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<ProductBatch> batches = new ArrayList<ProductBatch>();

    private int lowStockLimit;  // stock at or below this is low stock
    private int nearExpiryLimit;// expiring within this many days is near expiry

    
	public Inventory() {
		this.products = new ArrayList<Product>();
        this.batches = new ArrayList<ProductBatch>();
        this.lowStockLimit = 5;
        this.nearExpiryLimit = 30; // baguhin nyo as u see fit
        this.fileName = "inventory.txt";
    }

	/* method: require manager (for other methods)
	 * stops the action if the user is not a manager or the pin is wrong
	 * pam note: added reusable helper for methods that need manager pin validation
	 */
	private void requireManager(Manager currentUser, String pin, String action) {
		if (currentUser == null || !currentUser.isAuthorized(pin)) {
			throw new SecurityException("Wrong PIN. Only managers can " + action + ".");
		}
	}
	
	/* method: find product (for other methods)
     * returns the product with this id, or null if there is none
     * used by the other methods to check if a product exists before adding, updating, or restocking
     */
	private Product findProduct(String productID) {
		// if product doesn't exist
		if (productID == null) {
			return null;
		}
		
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
     * pam note: used my helper fxn to make this cleaner 
     */
	public void addProduct(Manager currentUser, String pin, Product newProduct)  {
		// only manager can add products
		requireManager(currentUser, pin, "add products");
		
		// pam note: i alr added validations to product constructor to keep this clean
		
		if(newProduct == null) {
			throw new IllegalArgumentException("No product was given.");
		}
		
		//checks for the duplicates pam note: i used find product method instead of reusing logic
		if(findProduct(newProduct.getProductId()) != null) {
			throw new IllegalArgumentException("A product with this ID already exists.");
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
	/// pam note: guys i used newly added methods para reused ung logic di redundant ung code and makalat
	/* method: update name
     * changes only the product name
     * new name is checked the same way as in add product
     */
	public boolean updateName(Manager currentUser, String pin, String productID, String newName) {
		// needs manager pin validation
		requireManager(currentUser, pin, "edit products");
		Product product = findProduct(productID); // find product first
		
		if(product == null) {
			return false;
		}
		
		product.setName(newName);
		return true;
	}
	
	/* method: update category
     * changes only the product category
     * new category is checked the same way as in add product
     */
	public boolean updateCategory(Manager currentUser, String pin, String productID, String newCategory) {
		// needs manager pin validation
		requireManager(currentUser, pin, "edit products");
		
		Product product = findProduct(productID);
		
		if(product == null){
			return false;
		}
		
		product.setCategory(newCategory);
		return true;
	}
	
	/* method: update description
     * changes only the description
     */
	public boolean updateDescription(Manager currentUser, String pin, String productID, String newDescription) {
		// needs manager pin validation
		requireManager(currentUser, pin, "edit products");
		
		Product product = findProduct(productID);
		
		if (product == null){
			return false;
		}
		
		product.setDescription(newDescription);
		return true;
	}

	/* method: update price
     * changes only the selling price
     * new price is checked the same way as in add product
     */
	public boolean updatePrice(Manager currentUser, String pin, String productID, double newPrice){
		// needs manager pin validation
		requireManager(currentUser, pin, "edit products");
		
		Product product = findProduct(productID);
		
		if (product == null){
			return false;
		}
		
		product.setSellingPrice(newPrice);
		return true;
	}
	
	/* method: get all products
     * returns the whole product list
     */
	public ArrayList<Product> getAllProducts(){
		return new ArrayList<Product>(products);
	}

	/* method: display products
     * prints all products per line /// temporary only before gui
     */
	public void displayProducts(){
		for(Product p : products){
			System.out.println(p);
		}
	}

	/* method: add batch
     * checks the values, generates the batch id, then makes and adds the new batch
     * batch id is the product id + 4 digits starting from 0001 (ex. HNY01-0001)
     * expiration date can be null for products that don't expire
     */
	public void addBatch(Manager currentUser, String pin, String productID, int quantityReceived, LocalDate deliveryDate, LocalDate expirationDate) {
		// needs manager pin validation
		requireManager(currentUser, pin, "add batches");

		Product product = findProduct(productID);
		// product doesnt exist
		if (product == null) {
			throw new IllegalArgumentException("Product with ID " + productID + " does not exist.");
		}
		
		// find the highest batch number currently used for this product, use next number when generating the new batch id
		// pam note: modified to ensure no possible duplicate when a batch is deleted
		int highest_count = 0;
	    String prefix = product.getProductId() + "-";

	    for(ProductBatch b : batches){
	        if(b.getProductId().equalsIgnoreCase(product.getProductId())) {
	            // get the number after the product if
	            int num = Integer.parseInt(b.getBatchId().substring(prefix.length()));

	            // keep track of the highest batch number
	            if(num > highest_count){
	                highest_count = num;
	            }
	        }
		}
		
		String batchId = String.format("%s-%04d", product.getProductId(), highest_count + 1); // pam note: uses the saved id

		// make new batch and add it to the list
		ProductBatch newBatch = new ProductBatch(batchId, product.getProductId(), quantityReceived, deliveryDate, expirationDate);
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

	    // remove expired or empty batches from consideration for sale
	    productBatches.removeIf(b -> b.isExpired() || b.isEmpty());

	    // sort by expiration date oldest first (null expiration dates placed last)
	    productBatches.sort((b1, b2) -> {
	        // both have no expiration date, so their order doesnt matter
	        if (b1.getExpirationDate() == null && b2.getExpirationDate() == null) {
	            return 0;
	        }

	        // if only one has no expiration date, place it last
	        if (b1.getExpirationDate() == null) {
	            return 1;
	        }

	        if (b2.getExpirationDate() == null) {
	            return -1;
	        }

	        // compare the dates so the batch expiring earlier comes first
	        return b1.getExpirationDate().compareTo(b2.getExpirationDate());
	    });

	    // deduct from each batch until the required quantity is reached
	    int remainingToDeduct = quantityToDeduct;

	    for (ProductBatch b : productBatches) {
	        // stop once there is nothing left to deduct
	        if (remainingToDeduct <= 0) {
	            break;
	        }

	        int currentQty = b.getRemainingQuantity();

	        if (currentQty <= remainingToDeduct) {
	            // use up the whole batch if its stock is less than or equal to what is needed
	            remainingToDeduct -= currentQty;
	            b.deductStock(currentQty);
	        } else {
	            // batch has enough stock, so only deduct what is still needed
	            b.deductStock(remainingToDeduct);
	            remainingToDeduct = 0;
	        }
	    }
	}
	
	
	/////////////////
	
	/* method: get batches of product
     * returns all the batches of one product
     */
	// pam note: hi! since product id talaga yung mas ideal na parameter for methods, i changed the name to product id
	// also since minamatch sya sa targetID, hindi magwwork kung name yung parameter
	// update: also removed some validations kasi minodify ko ung product batch constructor + used new methods
	public ArrayList<ProductBatch> getBatchesOfProduct(String productID){
		ArrayList<ProductBatch> productBatchList = new ArrayList<ProductBatch>();
		
		Product product = findProduct(productID); // verify the product exists first
		if (product == null) {
			return productBatchList;
		}
		
		for (ProductBatch b : batches) {
			if (b.getProductId().equalsIgnoreCase(product.getProductId())) {
				productBatchList.add(b);
			}
		}
		return productBatchList;
	}
	
	/* method: get total stock
     * adds up the remaining quantity of all the product's batches that are not expired
     * pam note: removed some validations w my changes to constructor + new methods
     */
	public int getTotalStock(String productId) {
		int total = 0;

		for(ProductBatch b: getBatchesOfProduct(productId)) {
			if(!b.isExpired()) { //checks if not expired
				total += b.getRemainingQuantity();
			}
		}
		return total;
	}
	
	
	/* method: get low stock products
     * products with stock more than 0 but at or below low stock limit
     * tells the manager what to restock soon
     */
	// pam note: inalis ko parameter kasi di naman sha ginagamit 
	public ArrayList<Product> getLowStockProducts() {
		ArrayList<Product> lowStockList = new ArrayList<Product>();
		
		for (Product p : products) {
			int totalStock = getTotalStock(p.getProductId());
			
			if(totalStock > 0 && totalStock <= lowStockLimit){ //adds to list when greater than 0 but is lower than or equal the limit
				lowStockList.add(p);
			}
		}
		return lowStockList;
	}
	
	 /* method: get out of stock products
     * products with 0 stock, need restocking right away 
     */
	// pam note: inalis ko parameter kasi di naman sha ginagamit
	public ArrayList<Product> getOutOfStockProducts() {
		ArrayList<Product> outOfStockList = new ArrayList<Product>();
		
		for (Product p : products) {
			if (getTotalStock(p.getProductId()) == 0) {
				outOfStockList.add(p);
			}
		}
		return outOfStockList;
	}
	
	/* method: get expired batches (using isexpired checker in product batch)
     * batches that are expired but still have items left, should be removed from the shelf and not sold
     */
	public ArrayList<ProductBatch> getExpiredBatches(){
		ArrayList<ProductBatch> expiredList = new ArrayList<ProductBatch>();
		for(ProductBatch b : batches){
			if(b.isExpired() && !b.isEmpty()){ // empty ones are skipped
				expiredList.add(b);
			}
		}
		return expiredList;
	}
	
	 /* method: get near expiry batches (using isnearexpiry checker in product batch)
     * batches that will expire soon and still have items left, should be sold first or put on sale
     */
	public ArrayList<ProductBatch> getNearExpiryBatches(){
	    ArrayList<ProductBatch> nearExpiryList = new ArrayList<ProductBatch>();

	    for(ProductBatch b : batches){
	        // only include near expiry batches that still have remaining stock
	        if(b.isNearExpiry(nearExpiryLimit) && !b.isEmpty()){
	            nearExpiryList.add(b);
	        }
	    }

	    return nearExpiryList;
	}
	
	 /* method: get all batches
     * returns the whole batch list (all delivery records for later)
     */
	public ArrayList<ProductBatch> getAllBatches() {
	    return this.batches;
	}
	
	 /* method: displayBatches
     * prints all batches, one per line (temporary, before GUI)
     */
	public void displayBatches() {
	    ArrayList<ProductBatch> batchList = getAllBatches();
	    
	    if (batchList == null || batchList.isEmpty()) {
	        System.out.println("No product batches available in the inventory.");
	        return;
	    }
	    
	    System.out.println("======= PRODUCT BATCHES =======");
	    for (ProductBatch batch : batchList) {
	        System.out.println(batch.toString());
	        System.out.println("----------------------------------------");
	    }
	}

	/* method: save data
     * saves all products and batches into the file, an existing file is replaced
     * call this before the program closes
     */
	public void saveData(){
	    try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
	        out.writeObject(products); // saves the whole product list
	        out.writeObject(batches); // saves all batches

	    }catch (IOException e) {
   			 throw new UncheckedIOException("Cannot save the inventory file " + fileName, e); // throws so the gui can show it
		}
	}

	/* method: load data
     * reads the products and batches back from the file
     * if there's no file yet (first time running), it just starts empty
     */
	public void loadData(){
	    File file = new File(fileName);

	    if(!file.exists()){
	        return; // nothing saved yet
	    }

	    try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
	        products = (ArrayList<Product>) in.readObject(); // read in the same order they were saved
	        batches = (ArrayList<ProductBatch>) in.readObject();

		}catch(IOException e){
		    throw new UncheckedIOException("Cannot read the inventory file " + fileName, e); // throws so the gui can show it
		
		}catch(ClassNotFoundException e){
		    throw new IllegalStateException("Cannot load the saved inventory data.", e);
		}
	}
}
