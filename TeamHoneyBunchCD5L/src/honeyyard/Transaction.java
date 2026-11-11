package honeyyard;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID; //Universally Unique Identifier - for unique IDs in each transaction

public class Transaction implements Serializable {
  private static final long serialVersionUID = 1L;
  //ATTRIBUTES
  private String transactionId;
  private String clerkUsername;
  private ArrayList<TransactionItem> items;
  private LocalDateTime transactionDate;
  private boolean voided;
  private double totalAmount;

  //CONSTRUCTOR
  public Transaction (String clerkUsername, ArrayList<TransactionItem> items) {
    this.clerkUsername = clerkUsername;
    this.items = new ArrayList<TransactionItem>(items);
    this.transactionId = UUID.randomUUID().toString();
    this.transactionDate = LocalDateTime.now();
    this.voided = false;
    calculateTotal();
  }
  //METHODS
  //to calculate the total amount of items purchased
  private void calculateTotal() {
    totalAmount = 0; //initially 0
    for (TransactionItem item: items) {
      totalAmount += item.getSubtotal();
    }
  }
  //returns he transaction ID
  public String getTransactionId() {
    return this.transactionId;
  }
  //returns the username of the Clerk
  public String getClerkUsername() {
    return this.clerkUsername;
  }
  //returns date and time of transaction
  public LocalDateTime getTransactionDate() {
    return this.transactionDate;
  }
  //returns the total amount for/of the transaction
  public double getTotalAmount() {
    return this.totalAmount;
  }
  //checker if transaction is voided
  public boolean isVoided() {
    return this.voided;
  }
  // //to return the purchased items
  // public ArrayList<TransactionItem> getItems() {
  //   return new ArrayList<TransactionItem>(items);
  // } 

  //addItem here?
}
