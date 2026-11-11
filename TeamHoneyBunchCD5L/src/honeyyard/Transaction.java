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
    
  }

}
