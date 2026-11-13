package honeyyard;

import java.io.Serializable;
// ito ginamit sa transaction, need consistent
import java.time.LocalDateTime;
import java.util.UUID;

public class CashEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private double amount;
    private String entryId; // unique id of this entry
	private String referenceId;  
    private String type;
	private LocalDateTime dateTime;

    // nilipat ko here ung validationsreferenceId
    public CashEntry(double amount, String referenceId, String type){
    	if (type == null || type.trim().isEmpty()) {
			throw new IllegalArgumentException("Cash entry type is required.");
		}
		if (amount <= 0){
			throw new IllegalArgumentException("Cash entry amount must be greater than 0.");
		}

		this.entryId = UUID.randomUUID().toString();
		this.referenceId = referenceId;
		this.type = type;
		this.amount = amount;
		this.dateTime = LocalDateTime.now();
    }
    
	public String getEntryId() {
		return entryId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public String getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}
    
	@Override
	public String toString(){
		return type + " | " + String.format("%.2f", amount) + " | ref: " + referenceId+ " | " + dateTime;
	}    
}
