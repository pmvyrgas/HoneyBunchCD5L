package honeyyard;

import java.time.LocalDate;

public class ProductBatch {
	public static int nextBatchNumber = 1;
    // attributes
    private String batchId;
    private String productId;
    private int quantityReceived;
    private int remainingQuantity;
    private LocalDate deliveryDate;
    private LocalDate expirationDate;
    
	public ProductBatch(String batchId, String productId, int quantityReceived, LocalDate deliveryDate, LocalDate expirationDate) {
		this.batchId = batchId;
        this.productId = productId;
        this.quantityReceived = quantityReceived;
        this.remainingQuantity = quantityReceived; // starts full, will be deducted after selling
        this.deliveryDate = deliveryDate;
        this.expirationDate = expirationDate;
    }

    // getters
    public String getBatchId() {
        return batchId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantityReceived() {
        return quantityReceived;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    
    /* method: deduct (for later in transcations)
     * removes sold items from this batch's quantity as well as inventory
    */
    public void deductStock(int quantity) {
		// checkers for validity
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than 0!");
		}
		if (quantity > remainingQuantity) {
			throw new IllegalArgumentException("Not enough stock in batch.");
		}
		remainingQuantity -= quantity;
	}
    /* method: is empty
     * returns true if there's nothing left in this batch
     * used by inventory to skip empty batches in the expired / near expiry lists
    */

    
    /* method: is expired
     * if expired, return true
     * if product has no expiration date / not expired, false
    */
	boolean isExpired(){
		// no expiration date
		if(expirationDate == null){
			return false;
		}
		if(expirationDate.isBefore(LocalDate.now())){
			return true; // the expiration date has already passed
		}
		return false; // not expired
	}
    
    /* method: is near expiry
     * if expired or no expiration, return false
     * if product is not expired yet but will be in a given number of days (nilagay ko sa inventory class ung pagset), true
    */
	boolean isNearExpiry(int days){
		// no expiration date, never expires
		if(expirationDate == null){
			return false;
		}

		if(isExpired()){
            return false; // already expired
		}
		
		// add the given number of days to today's date to get the cutoff for near expiry
		LocalDate limit = LocalDate.now().plusDays(days); 
		if(expirationDate.isAfter(limit)) {
            return false; // if date is still beyond limit, not yet near expiry
        }
		
		return true; // expires on or before the limit
	}
    
    // temporary printing before GUI
    @Override
    public String toString() {
        String exp = "N/A";
        if (expirationDate != null) {
            exp = expirationDate.toString();
        }
        return batchId + " [" + productId + "] " + remainingQuantity + "/" + quantityReceived
                + " left, delivered: " + deliveryDate + ", expires: " + exp;
    }
}
