package honeyyard;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID; //Universally Unique Identifier - for unique IDs in each transaction

public class Transaction implements Serializable {
  private static final long serialVersionUID = 1L;
  //CONSTANTS -- added for status
  public static final String PENDING = "PENDING";
  public static final String COMPLETED = "COMPLETED";
  public static final String VOIDED = "VOIDED";
  //ATTRIBUTES
  private String transactionId;
  private String clerkUsername;
  private ArrayList<TransactionItem> items;
  private LocalDateTime transactionDate;
  private String status; //changed from boolean
  //added: for payment details
  private double totalAmount;
  private double amountPaid;
  private double change;
  
  //CONSTRUCTOR
  public Transaction (String clerkUsername) {
	  this.clerkUsername = clerkUsername;
	  this.items = new ArrayList<>();
	  this.transactionId = UUID.randomUUID().toString();
	  this.transactionDate = LocalDateTime.now();
	  //added for payment
	  this.status = PENDING;
	  this.totalAmount = 0.0;
	  this.amountPaid = 0.0;
	  this.change = 0.0;
  }
  
  //METHODS
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
  //added: returns the copy of transaction items
  public ArrayList<TransactionItem> getItems() {
	  return new ArrayList<>(items);
  }
  //added: getting the amount paid by customer
  public double getAmountPaid() {
	  return this.amountPaid;
  }
  //added: get customer's change
  public double getChange() {
	  return this.change;
  }
  //added: get status
  public String getStatus() {
	  return this.status;
  }
  //added: checker if transaction is Pending
  public boolean isPending() {
	  return status.equals(PENDING);
  }
  //added: checker if transaction is voided
  public boolean isVoided() {
	  return status.equals(VOIDED);
  }

	// inalis ko set status set amount paid and setchanges kasi status and payment shld only be thru mark completed and void transaction para may secure validations
//  public void setStatus (String status) { 
      //this.status = status; 
//  
 // public void setAmountPaid(double amountPaid) { 
    //  this.amountPaid = amountPaid; 
 // }

//  public void setChange(double change) { 
      //this.change = change; 
//  }
  ///////////

  // methods: createTransaction
  // initializes a new transaction cart with a unique transaction id, 
  // the logged-in clerk's username, an empty arraylist of transaction items,
  // and a pending status -- I think nagawa na itu sa constructor? or di bah? yes done na, clerk calls it sa start new constructor
  
  // methods: addItem
  // adds a selected product and its quantity to the cart as a transaction item
  // (or increases the quantity if the product ID already exists in the cart)
// checks stock from inventory but does not deduct it (deduction only happens at checkout)
  public void addItem(Product product, int quantity) {
	  if (!isPending()) { //if status of transaction is not pending
		  throw new IllegalStateException("Transaction is no longer pending.");
	  }
	  if (product == null) { //invalid/no product selected
		  throw new IllegalArgumentException("Please select a product.");
	  }
	  if (quantity <= 0) { //invalid quantity
		  throw new IllegalArgumentException("Quantity must be greater than 0.");
	  }
	  
	   int available = inventory.getTotalStock(product.getProductId()); //added stock check
   	  for (TransactionItem item : items) {
   		  if(item.getProduct().getProductId().equals(product.getProductId())) {
   			  if (item.getQuantity() + quantity > available) { //includes what's already in the cart
   				  throw new IllegalArgumentException("Not enough stock. Only " + available + "left in stock.";
   			  }
   			  item.setQuantity(item.getQuantity() + quantity);
   			  recalculateTotal();
   			  return;
   		  }
   	  }
	  
   	  if (quantity > available) {
   		  throw new IllegalArgumentException("Not enough stock. Only " + available +  "left in stock.");
   	  }
	  
    TransactionItem newItem = new TransactionItem(product, quantity);
    items.add(newItem);
    recalculateTotal();
   }

  // methods: updateItemQuantity
  // changes the quantity of a specific item in the pending cart
  public void updateItemQuantity(String productId, int newQuantity) {
      if (!isPending()) { //check if transaction is not pending 
          throw new IllegalStateException("Transaction is no longer pending.");
      }
      if (productId == null || productId.trim().isEmpty()) { //check for invalid/empty productId
          throw new IllegalArgumentException("Please enter a product ID.");
      }
      if (newQuantity <= 0) { //quantity must be valid (above 0)
          throw new IllegalArgumentException("Quantity must be greater than 0.");
      }
      int available = inventory.getTotalStock(productId); //added stock check
	  
      if (newQuantity > available) {
   		  throw new IllegalArgumentException("Not enough stock. Only " + available +  "left in stock.");
      }
      for (TransactionItem item : items) {
          if (item.getProduct().getProductId().equals(productId)) {
              item.setQuantity(newQuantity);
              recalculateTotal();
              return;
          }
      }
      throw new IllegalArgumentException("Product was not found in the cart.");
  }
  // methods: removeItem
  // removes an item from the cart
  public void removeItem(String productId) {
      if (!isPending()) { //check if transaction is not pending 
          throw new IllegalStateException("Transaction is no longer pending.");
      }
      if (productId == null || productId.trim().isEmpty()) { //check for invalid/empty productId
          throw new IllegalArgumentException("Please enter a product ID.");
      }
      for (int i = 0; i < items.size(); i++) {
          if (items.get(i).getProduct().getProductId().equals(productId)) {
              items.remove(i);
              recalculateTotal();
              return;
          }
      }
      throw new IllegalArgumentException("Product was not found in the cart.");
  }
  // methods: recalculateTotal
  // recalculates the overall transaction amount using 
  // the subtotals of all transaction items
  public void recalculateTotal() {
      totalAmount = 0.0;
      for (TransactionItem item : items) {
          totalAmount += item.calculateSubtotal();
      }
  }
  // methods: proceedCheckout
  // validates that the cart is not empty, payment is sufficient, 
  // and the transaction is pending before passing it for processing
	public void proceedCheckout(double paymentAmount){
		if(!isPending()){
			throw new IllegalStateException("Only pending transactions can proceed to checkout! ");
		} if(items.isEmpty()){
			thrown new IllegalStateException("Cannot checkout with an empty cart");
		} if(Double.isNaN(paymentAmount) || payment < this.totalAmount){
			throw new IllegalStateException("Insufficient payment amount, cannot proceed to checkout");
			//added isnan check so non number payments are also rejected
		}
	}
  
  // methods: markCompleted
  // marks the transaction as completed, finalizes the paid amount and change, 
  // and logs it to transaction records and the cash wallet
	// will move adding to records and wallet to cerk
	public void markCompleted(double paymentAmount, TransactionRecords records, CashWallet wallet){
		proceedCheckout(paymentAmount); //runs for the checkout validation first

		this.amountPaid = paymentAmount;
		this.change = paymentAmount - this.totalAmount;
		this.status = COMPLETED;   //changes status to completed
		this.transactionDate = LocalDateTime.now();
	}

// BALIKAN PAM
  // methods: voidTransaction
  // updates the transaction status to voided
  // and triggers a corresponding refund to the cash wallet
  public void voidTransaction(CashWallet wallet){
  	if(isVoided()){
		throw new IllegalStateException("Transaction is already voided. ");
	}
	if (!status.equals(COMPLETED)) {
        throw new IllegalStateException("Only completed transactions can be voided."); 
    }
	this.status = VOIDED; //change status to void
	if(wallet != null){
		wallet.recordRefund(this); //refund when voided
  }

}
