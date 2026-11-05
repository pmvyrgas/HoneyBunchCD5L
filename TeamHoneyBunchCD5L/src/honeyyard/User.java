package honeyyard;

// base class for users
// manager and clerk extend this

import java.io.Serializable;

public abstract class User implements Serializable{
	private static final long serialVersionUID = 1L;

	// attributes
	private String username;
	private String password;

	public User(String username, String password) {
		if(username == null || username.trim().isEmpty()){
			throw new IllegalArgumentException("Username cannot be empty.");
		}
		if(password == null || password.isEmpty()){
			throw new IllegalArgumentException("Password cannot be empty.");
		}
		this.username = username.trim();
		this.password = password;
	}

	// getters
	public String getUsername() {
		return username;
	}

	/* method: check password
	 * returns true if the entered password is correct, used for logging in
	 */
	public boolean checkPassword(String enteredPassword) {
		return password.equals(enteredPassword);
	}

	/* method: is authorized
	 * each type of user says if the entered pin lets them do manager actions
	 */
	public abstract boolean isAuthorized(String enteredPin);
}