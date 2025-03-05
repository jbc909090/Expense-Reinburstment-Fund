package com.example.demo.DAOs;

import com.example.demo.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceDAO extends JpaRepository<Invoice, Integer> {
    public List<Invoice> findAllByUser_UserId(int userid);
}
