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

  
}
