package honeyyard;

import java.util.ArrayList;

public class TransactionRecords {
    private ArrayList<Transaction> records;

    public TransactionRecords() {
        this.records = new ArrayList<>();
    }

    public ArrayList<Transaction> getRecords() {
      return records; 
    }

    /////////// 
    
    // methods: addTransactionRecord
    // adds a completed transaction to the system's arraylist of records
    
    // methods: getTransactionsByClerk
    // retrieves and filters recorded transactions 
    // using a clerk's username for sales tracking
    
    // methods: findTransactionById
    // finds a specific transaction for processing or voiding
    
    // methods: addTransactionRecord
    // adds a completed transaction to the system's arraylist of records
}
