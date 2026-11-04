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
    
    /* method: is empty
     * returns true if there's nothing left in this batch
     * used by inventory to skip empty batches in the expired / near expiry lists
    */

    
    /* method: is expired
     * if expired, return true
     * if product has no expiration date / not expired, false
    */
    
    /* method: is near expiry
     * if expired or no expiration, return false
     * if product is not expired yet but will be in a given number of days (nilagay ko sa inventory class ung pagset), true
    */
    
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
