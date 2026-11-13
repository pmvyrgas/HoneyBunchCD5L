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

    public Transaction startNewTransaction(ArrayList<TransactionItem> items){
    Transaction newTransaction = new Transaction(this.getUsername(), items); //instatiates new transaction
	return newTransaction;
  }

/////////// 

// methods: processCheckout
// enters the received payment amount to complete the checkout process
    public boolean processCheckout(Transaction transaction, double amountPaid, Inventory inventory, TransactionRecords records, CashWallet wallet) {
    	if (!"PENDING".equals(transaction.getStatus())) {
            System.out.println("Transaction is not pending.");
            return false;
        }

        if (transaction.getItems().isEmpty()) {
            System.out.println("Cart is empty.");
            return false;
        }

        if (amountPaid < transaction.getTotalAmount()) {
            System.out.println("Insufficient payment amount.");
            return false;
        }

        double change = amountPaid - transaction.getTotalAmount();

        // Perform stock deduction for each item
        try {
            for (TransactionItem item : transaction.getItems()) {
                inventory.deductStock(item.getProduct().getProductId(), item.getQuantity());
            }
        } catch (Exception e) {
            System.out.println("Checkout failed due to stock deduction error: " + e.getMessage());
            return false;
        }

        transaction.setStatus("COMPLETED");
        transaction.setAmountPaid(amountPaid);
        transaction.setChange(change);

        records.getRecords().add(transaction);
        wallet.recordSale(transaction.getTotalAmount(), transaction.getTransactionId());

        System.out.println("Checkout successful! Change: " + change);
        return true;
    }
	
// methods: authorizeVoid
// authorizes the voiding of a previously recorded transactio
}
