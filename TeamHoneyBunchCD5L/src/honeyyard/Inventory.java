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

	
	/* method: search products (for users)
     * returns all products whose name or id has the keyword in it, must not be case sensitive
     */

	
	/* method: get products by category
     * returns all products in this category, for filtering
     */
	
	//// idk pa if hiwalay ko sila na method or 1 for all
	/* method: update name
     * changes only the product name
     * new name is checked the same way as in add product
     */

	/* method: update category
     * changes only the product category
     * new category is checked the same way as in add product
     */

	/* method: update description
     * changes only the description
     */

	/* method: update price
     * changes only the selling price
     * new price is checked the same way as in add product
     */
	
	/* method: update description
     * changes only the description
     */
	
	/////////////////

	
	/* method: add batch
     * checks the values, generates the batch id, then makes and adds the new batch
     * batch id is the product id + 4 digits starting from 0001 (ex. HNY01-0001)
     * expiration date can be null for products that don't expire
     */
	
	/* method: get batches of product
     * returns all the batches of one product
     */
	
	/* method: get total stock
     * adds up the remaining quantity of all the product's batches that are not expired
     */
	
	/* method: deduct stock (for later in transactions)
     * removes sold items from the product's batches, oldest expiration first
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
