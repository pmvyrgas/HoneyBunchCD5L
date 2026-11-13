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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    // methods: createCashEntry
    // generates individual records for cash movements 
    // including amount, transaction reference, type, and date
    public static CashEntry createCashEntry(double amount, String transactionReference, String type) {
        return new CashEntry(amount, transactionReference, type, new Date());
    }
}
