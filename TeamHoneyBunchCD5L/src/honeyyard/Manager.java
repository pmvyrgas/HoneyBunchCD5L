package honeyyard;

// manager has full access to products and inventory
// needs to enter their pin before every manager action
public class Manager extends User {
	private static final long serialVersionUID = 1L;

	// attributes
	private String pin;
	
	public Manager(String username, String password, String pin) {
		super(username, password);
		// pin must be exactly 6 digits
		if (pin == null || !pin.matches("[0-9]{6}")){
			throw new IllegalArgumentException("PIN must be 6 digits.");
		}
		this.pin = pin;
	}

	/* method: is authorized
	 * returns true only if the entered pin matches this manager's pin
	 */
	@Override
	public boolean isAuthorized(String enteredPin) {
		if (enteredPin == null){
			return false;
		}
		return pin.equals(enteredPin);
	}
}