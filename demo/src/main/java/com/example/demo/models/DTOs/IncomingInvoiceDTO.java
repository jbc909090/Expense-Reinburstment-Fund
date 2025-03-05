package com.example.demo.models.DTOs;

public class IncomingInvoiceDTO {
    private int amount;
    private String status;
    private String description;
    private int userId;
    public IncomingInvoiceDTO(){
    }
    public IncomingInvoiceDTO(int amount, String status, String description, int userId) {
        this.amount = amount;
        this.status = status;
        this.description = description;
        this.userId = userId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getAmount() {
        return amount;
    }
}
