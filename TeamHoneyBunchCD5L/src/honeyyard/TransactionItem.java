package honeyyard;

import java.io.Serializable;

public class TransactionItem implements Serializable {
  private static final long serialVersionUID = 1L;
  //ATTRIBUTES
  private Product product;
  private int quantity;
  private double unitPrice;
  
  //CONSTRUCTOR
  public TransactionItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
    this.unitPrice = product.getSellingPrice();
  }
  //METHODS
  //this returns the product included in the transaction
  public Product getProduct() {
    return this.product;
  }
  //returns the quantity purchased
  public int getQuantity() {
    return this.quantity;
  }
  //returns the price of product when transaction was made
  public double getUnitPrice() {
    return this.unitPrice;
  }

  /////////
  // method: getTransactionItemDetails
  // holds and retrieves the product, purchased quantity, 
  // and selling price at the time of purchase
  public String getTransactionItemDetails() {
	  return "Product: " + product.getName() + "\nQuantity: " + quantity + "\nUnit Price: PHP " + String.format("%.2f", unitPrice);  
  }
  // methods: calculateSubtotal 
  // computes the subtotal amount for the transaction item 
  // based on quantity and selling price
  public double calculateSubtotal() {
	    return quantity*unitPrice;
  }
  // need ba natin ng toString method here? for the subtotal?
}
