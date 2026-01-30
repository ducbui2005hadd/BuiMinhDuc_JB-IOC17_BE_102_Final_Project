package entity;

import java.sql.Timestamp;

public class Invoice {
    private int id;
    private int customerId;
    private Timestamp createdAt;
    private double totalAmount;
    private String customerName;

    public Invoice() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    @Override
    public String toString() {
        return String.format("Invoice ID: %d | Customer: %s | Date: %s | Total: %.2f",
                id, customerName != null ? customerName : customerId, createdAt, totalAmount);
    }
}