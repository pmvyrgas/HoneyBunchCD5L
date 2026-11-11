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
  //this calculates the subtotal for the item in the transaction
  public double getSubtotal() {
    return quantity*unitPrice;
  }

  /////////
  //method for batch deductions? like quantity deducted from a batch? need kaya separate class?

  // method: getTransactionItemDetails
  // holds and retrieves the product, purchased quantity, 
  // and selling price at the time of purchase
  
  // methods: calculateSubtotal
  // computes the subtotal amount for the transaction item 
  // based on quantity and selling price
  
}
