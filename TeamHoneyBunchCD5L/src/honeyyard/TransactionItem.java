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
    if (product == null) { //check invalid product 
        throw new IllegalArgumentException("Product cannot be empty.");
    }
    if (quantity <= 0) { //check invalid quantity 
        throw new IllegalArgumentException("Quantity must be greater than 0.");
    }
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
  
  //added method: setQuantity (setter)
  //updates the purchased quantity
  public void setQuantity(int quantity) {
      if (quantity <= 0) { //checker for invalid quantity
          throw new IllegalArgumentException("Quantity must be greater than 0.");
      }
      this.quantity = quantity;
  }
  
  //added method: override the toString (temporary print statement)
  //displays transaction item details and subtotal
  @Override
  public String toString() {
      return getTransactionItemDetails() + "\nSubtotal: PHP " + String.format("%.2f", calculateSubtotal());
  }
}
