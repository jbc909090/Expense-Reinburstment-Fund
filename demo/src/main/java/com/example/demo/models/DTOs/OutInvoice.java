package com.example.demo.models.DTOs;

import com.example.demo.models.User;
import jakarta.persistence.*;

public class OutInvoice {
    private int invoiceId;
    private int user;
    private int amount;
    private String description;
    private String status;
    public OutInvoice() {
    }
    public OutInvoice(int invoiceId, int user, int amount, String description, String status) {
        this.invoiceId = invoiceId;
        this.user = user;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getUser() {
        return user;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
