package honeyyard;

// clerk has limted access 
// needs to enter their pin before every clerk action
public class Clerk extends User {
	private static final long serialVersionUID = 1L;
	// attributes
	private String pin;
	
	public Clerk(String username, String password, String pin) {
		super(username, password);
		// pin must be exactly 6 digits
		if (pin == null || !pin.matches("[0-9]{6}")){
			throw new IllegalArgumentException("PIN must be 6 digits.");
		}
		this.pin = pin;
	}

	/* method: is authorized
	 * returns true only if the entered pin matches this clerk's pin
	 */
	@Override
	public boolean isAuthorized(String enteredPin) {
		if (enteredPin == null){
			return false;
		}
		return pin.equals(enteredPin);
	}

	// pam note: inalis ko ung parameter bc yung tansactions is like cart, so empty car sya sa start, also sa pagconstruct ng transaction, username lang parameter non sa constructor so di valid lagyan ng iba p
    public Transaction startNewTransaction(){
    Transaction newTransaction = new Transaction(this.getUsername()); //instatiates new transaction
	return newTransaction;
  }

/////////// 

// methods: processCheckout
// enters the received payment amount to complete the checkout process
// added throws an exception with the reason 
    public void processCheckout(Transaction transaction, double amountPaid, Inventory inventory, TransactionRecords records, CashWallet wallet) {
    	if (transaction == null || inventory == null || records == null || wallet == null) {
			throw new IllegalArgumentException("Missing transaction, inventory, records, or wallet.");
        }

		// pending, not empty, payment is enough, reuse from transaction
		transaction.proceedCheckout(amountPaid);
		
		// check ALL items first before deducting anything
		// so if one item is short, nothing has been deducted yet (no partial checkout), nilagyan ko na even if goal natin is one user at a time 
		for (TransactionItem item : transaction.getItems()) {
			int available = inventory.getTotalStock(item.getProduct().getProductId());
			if (item.getQuantity() > available) {
   		  		throw new IllegalArgumentException("Not enough stock for " + item.getProduct().getName() + ". Only " + available + " left in stock.");
			}
		}

		// perform stock deduction for each item (inventory alr handles where to deduct, yung isufficent payment handled by proceed checkout)
		for (TransactionItem item : transaction.getItems()) {
			inventory.deductStock(item.getProduct().getProductId(), item.getQuantity());
		}

		// finalize payment, add to history, and record sale
        transaction.markCompleted(amountPaid);
		records.addTransactionRecord(transaction);
		wallet.recordSale(transaction);
		
      // save everything
		inventory.saveData();
		records.saveData();
		wallet.saveData();
    }
	
// methods: authorizevoid
// authorizes the voiding of a previously recorded transaction
	public void authorizeVoid(String enteredPin, String transactionId, TransactionRecords records, CashWallet wallet) {
		if(!isAuthorized(enteredPin)){
			throw new SecurityException("Invalid PIN. Cannot void the transaction.");
		}
		if(records == null) {
			throw new IllegalArgumentException("Transaction records are not available.");
		}

		// reuse method from transaction records
		Transaction transaction = records.findTransactionById(transactionId);
		if(transaction == null) {
			throw new IllegalArgumentException("Transaction was not found.");
		}
		if (wallet == null) {
			throw new IllegalArgumentException("Wallet is not available.");
		}
		// must be completed and not yet voided
		transaction.checkCanBeVoided();
		// refund first, so a failed refund doesn't leave it voided
		wallet.recordRefund(transaction);
		// then change the status
		transaction.voidTransaction();

		// save data
		records.saveData();
		wallet.saveData();
	}
}
