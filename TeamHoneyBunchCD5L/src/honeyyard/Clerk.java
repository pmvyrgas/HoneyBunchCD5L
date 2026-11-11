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
	
// methods: startNewPurchase
//  initiates a new cart/transaction session

// methods: processCheckout
// enters the received payment amount to complete the checkout process

// methods: authorizeVoid
// authorizes the voiding of a previously recorded transactio
}
