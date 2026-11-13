package honeyyard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class TransactionRecords implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Transaction> records;
    private String fileName;
    
    public TransactionRecords() {
        this.records = new ArrayList<>();
        this.fileName = "transactions.txt";
    }

    // returns only a copy, records can only be added through add transaction record
    public ArrayList<Transaction> getRecords() {
      return new ArrayList<>(records);
    }

    /////////// 
    
    // methods: addTransactionRecord
    // adds a completed transaction to the system's arraylist of records
   public void addTransactionRecord(Transaction transaction) {
        if(transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if(!Transaction.COMPLETED.equals(transaction.getStatus())) {
            throw new IllegalArgumentException("Only the completed transactions will be added to records. ");
        }
        if (findTransactionById(transaction.getTransactionId()) != null) {
            throw new IllegalArgumentException("Transaction is already recorded.");
        }
        records.add(transaction); //adds to records
    }
        
    // methods: getTransactionsByClerk
    // retrieves and filters recorded transactions 
    // using a clerk's username for sales tracking
    public ArrayList<Transaction> getTransactionByClerk(String clerkUsername){
        ArrayList<Transaction> clerkTransactions = new ArrayList<>();
        if (clerkUsername == null || clerkUsername.trim().isEmpty()) {
            return clerkTransactions;
        }
        for (Transaction transaction : records) {
            if (transaction.getClerkUsername().equalsIgnoreCase(clerkUsername.trim())) {
                clerkTransactions.add(transaction); //adds the found transactions by the clerk
            }
        }
        return clerkTransactions;
    }

    // methods: findTransactionById
    // finds a specific transaction for processing or voiding
    public Transaction findTransactionById(String transactionId) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            return null;
        }
        for (Transaction transaction : records) {
            if (transaction.getTransactionId().equals(transactionId.trim())) { 
                return transaction; //returns the transaction if the id is found in the records
            }
        }
        return null;
    }
    
    // method: save data
    public void saveData(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            out.writeObject(records);
        }catch (IOException e) {
            System.out.println("Cannot save the transactions file: " + fileName);
        }
    }
}
