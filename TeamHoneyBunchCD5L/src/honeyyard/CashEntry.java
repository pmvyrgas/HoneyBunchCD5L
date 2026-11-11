package honeyyard;

import java.util.Date;

public class CashEntry {
    private double amount;
    private String transactionReference;
    private String type;
    private Date date;

    public CashEntry(double amount, String transactionReference, String type, Date date) {
        this.amount = amount;
        this.transactionReference = transactionReference;
        this.type = type;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }
    
    public String getTransactionReference() { 
        return transactionReference;
    }
    
    public String getType() { 
        return type;
    }
    
    public Date getDate() { 
        return date; 
    }
    
    // methods: createCashEntry
    // generates individual records for cash movements 
    // including amount, transaction reference, type, and date
}
