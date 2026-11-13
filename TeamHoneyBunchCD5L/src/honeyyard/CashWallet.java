package honeyyard;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CashWallet implements Serializable {
    private static final long serialVersionUID = 1L;
    
	// attributes
	private double balance;
	private ArrayList<CashEntry> cashEntries;
	private String fileName;

    public CashWallet() {
        this.balance = 0.0;
		this.cashEntries = new ArrayList<CashEntry>();
		this.fileName = "wallet.dat";    }

    public double getBalance() { 
        return balance; 
    }

    // returns a copy so the history can't be edited from outside
	public ArrayList<CashEntry> getCashEntries() {
		return new ArrayList<CashEntry>(cashEntries);
	}
    
    public void setBalance(double balance) { 
        this.balance = balance; 
    }
    
    public ArrayList<CashEntry> getCashEntries() { 
        return cashEntries; 
    }

    /* method: has entry (for other methods)
	 * checks if there's already an entry of this type for this transaction
	 * blocks duplicate sales and duplicate refunds
	 */
	private boolean hasEntry(String referenceId, String type) {
	    for (CashEntry entry : cashEntries) {
			if(type.equals(entry.getType()) && referenceId.equals(entry.getReferenceId())){
				return true;
			}
		}
	    return false;
	}

    // methods: recordSale
    // record a completed purchase and updates the cash balance
    public void recordSale(double amount, String transactionReference) {
        if (transaction == null){
			throw new IllegalArgumentException("No transaction was given.");
		}
		if(!"COMPLETED".equals(transaction.getStatus())) {
			throw new IllegalStateException("Only completed transactions can be recorded as sales.");
		}
		if(hasEntry(transaction.getTransactionId(), "SALE")) {
			throw new IllegalStateException("This sale was already recorded.");
		}

		double amount = transaction.getTotalAmount();
		cashEntries.add(new CashEntry(transaction.getTransactionId(), "SALE", amount, null));
		balance += amount;
    }
    
    //methods: recordRefund 
    // handles and records refunds resulting from voided transactions
    // original sale entry stays, a separate refund entry is added
    public void recordRefund(double amount, String transactionReference) {
		if(transaction == null) {
			throw new IllegalArgumentException("No transaction was given.");
		}
		String id = transaction.getTransactionId();

		if(!hasEntry(id, "SALE")) {
			throw new IllegalStateException("No recorded sale found for this transaction.");
		}
		if(hasEntry(id, "REFUND")) {
			throw new IllegalStateException("This transaction was already refunded.");
		}

		double amount = transaction.getTotalAmount();
		if(amount > balance) {
			throw new IllegalStateException("Not enough cash in the wallet for this refund.");
		}

		cashEntries.add(new CashEntry(id, "REFUND", amount));
		balance -= amount;
    }

    // methods: get entries by type, for filtering cash history (bc we have refunds)
    public ArrayList<CashEntry> getEntriesByType(String type) {
		ArrayList<CashEntry> result = new ArrayList<CashEntry>();
		for(CashEntry entry : cashEntries){
            if(entry.getType().equalsIgnoreCase(type)) {
                result.add(entry);
            }
		}
		return result;
	}

    // method: save data
	public void saveData() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
			out.writeDouble(balance);
			out.writeObject(cashEntries);
		} catch (IOException e) {
			System.out.println("Cannot save the wallet file: " + fileName);
		}
	}

	// method: load data
	// if there's no file yet, the wallet just starts empty

    public void loadData() {
		File file = new File(fileName);

		if(!file.exists()){
			return;
		}

		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
			balance = in.readDouble(); // read in the same order they were saved
			cashEntries = (ArrayList<CashEntry>) in.readObject();
		}catch (IOException e) {
			System.out.println("Cannot read the wallet file: " + fileName);
		}catch (ClassNotFoundException e) {
			System.out.println("Cannot load the saved wallet data.");
		}
	}

    
}
