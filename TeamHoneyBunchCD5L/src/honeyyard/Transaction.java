package honeyyard;

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
    
  }

}
