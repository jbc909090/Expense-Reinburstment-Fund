package com.example.demo.models.DTOs;

public class InvoiceDTO {
    private String description;
    private int id;
    public InvoiceDTO(){
    }
    public InvoiceDTO(String description, int Id) {
        this.description = description;
        this.id = Id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}