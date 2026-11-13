package honeyyard;

import java.util.ArrayList;
import java.util.Date;

public class CashWallet {
    private double balance;
    private ArrayList<CashEntry> cashEntries;

    public CashWallet() {
        this.balance = 0.0;
        this.cashEntries = new ArrayList<>();
    }

    public double getBalance() { 
        return balance; 
    }
    
    public void setBalance(double balance) { 
        this.balance = balance; 
    }
    
    public ArrayList<CashEntry> getCashEntries() { 
        return cashEntries; 
    }

    // methods: recordSale
    // record a completed purchase and updates the cash balance
    public void recordSale(double amount, String transactionReference) {
        this.balance += amount;
        CashEntry entry = new CashEntry(amount, transactionReference, "SALE", new Date());
        cashEntries.add(entry);
    }
    
    //methods: recordRefund 
    // handles and records refunds resulting from voided transactions
    public void recordRefund(double amount, String transactionReference) {
        this.balance -= amount;
        CashEntry entry = new CashEntry(amount, transactionReference, "REFUND", new Date());
        cashEntries.add(entry);
    }
}
