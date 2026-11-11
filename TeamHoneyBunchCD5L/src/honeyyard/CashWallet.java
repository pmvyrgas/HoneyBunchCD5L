package honeyyard;

import java.util.ArrayList;

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

    ////////////
    // methods: recordSale
    // record a completed purchase and updates the cash balance
    
    //methods: recordRefund 
    // handles and records refunds resulting from voided transactions
}
