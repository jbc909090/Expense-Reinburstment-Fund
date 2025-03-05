package com.example.demo.service;

import com.example.demo.DAOs.InvoiceDAO;
import com.example.demo.DAOs.UserDAO;
import com.example.demo.models.DTOs.IncomingInvoiceDTO;
import com.example.demo.models.DTOs.OutInvoice;
import com.example.demo.models.DTOs.OutgoingUserDTO;
import com.example.demo.models.Invoice;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final InvoiceDAO invoiceDAO;
    private final UserDAO userDAO;
    @Autowired
    public  EmployeeService (InvoiceDAO invoiceDAO, UserDAO userDAO) {
        this.invoiceDAO = invoiceDAO;
        this.userDAO = userDAO;
    }
    // retrieve all your reinburstments
    public List<OutInvoice> getAllInvoicesByUserId (int userId) {
        List<Invoice> all = invoiceDAO.findAllByUser_UserId(userId);
        List<OutInvoice> oi = new ArrayList<>();
        for (Invoice i : all) {
            oi.add(new OutInvoice(i.getInvoiceId(), i.getUser().getUserId(), i.getAmount(), i.getDescription(), i.getStatus()));
        }
        return oi;
    }
    // make new reinburstment
    public Invoice insertInvoice(IncomingInvoiceDTO invoice) {
        Invoice newInvoice = new Invoice(
                0,
                null,
                invoice.getAmount(),
                invoice.getDescription(),
                invoice.getStatus()
        );
        Optional<User> user = userDAO.findById(invoice.getUserId());
        if (user.isEmpty()) {
         //throw error?
        } else {
            newInvoice.setUser(user.get());
        }
        return invoiceDAO.save(newInvoice);
    }
    // see only your pending reinburstments
    public List<OutInvoice> getAllPendingInvoicesByUserId (int userId) {
        List<Invoice> all = invoiceDAO.findAllByUser_UserId(userId);
        List<OutInvoice> pending = new ArrayList<>();
        for (Invoice i : all) {
            if (i.getStatus().equals("Pending")) {
                pending.add(new OutInvoice(i.getInvoiceId(), i.getUser().getUserId(), i.getAmount(), i.getDescription(), i.getStatus()));
            }
        }
        return pending;
    }
    // update your own reinburstment
    public Invoice editOwnDescription (int id, String desc, int userId) {
        if (invoiceDAO.existsById(id)) {
            Invoice edit = invoiceDAO.findById(id).get();
            if (edit.getUser().getUserId() == userId) {
                edit.setDescription(desc);
                return invoiceDAO.save(edit);
            }
        }
        return null;
    }
    // OPTIONAL see your total reinburstment given and how much is pending and how much canceled
    //manager action
    //see all reinburstments
    public List<OutInvoice> getAllInvoices () {
        List <Invoice> all = invoiceDAO.findAll();
        List<OutInvoice> oi = new ArrayList<>();
        for (Invoice i : all) {
            oi.add(new OutInvoice(i.getInvoiceId(), i.getUser().getUserId(), i.getAmount(), i.getDescription(), i.getStatus()));
        }
        return oi;

    }
    //see all pending reinburstments
    public List<OutInvoice> getAllPendingInvoices () {
        List<Invoice> all = invoiceDAO.findAll();
        List<OutInvoice> pending = new ArrayList<>();
        for (Invoice i : all) {
            if (i.getStatus().equals("Pending")) {
                pending.add(new OutInvoice(i.getInvoiceId(), i.getUser().getUserId(), i.getAmount(), i.getDescription(), i.getStatus()));
            }
        }
        return pending;
    }
    //see all users
    public List<OutgoingUserDTO> getAllUsers () {
        List<User> all = userDAO.findAll();
        List<OutgoingUserDTO> toReturn = new ArrayList<>();
        for (User u : all) {
            toReturn.add(new OutgoingUserDTO(u));
        }
        return toReturn;
    }
    //delete a user
    public OutgoingUserDTO deleteUser (int userId) {
        Optional<User> del = userDAO.findById(userId);
        if (del.isEmpty()) {
            return null;
        } else {
            userDAO.delete(del.get());
            return new OutgoingUserDTO(del.get());
        }
    }
    //edit a reinburstment's status
    public Invoice editStatus (int id, String status) {
        System.out.print(id+" "+status);
        if (invoiceDAO.existsById(id)) {
            Invoice edit = invoiceDAO.findById(id).get();
            edit.setStatus(status);
            return invoiceDAO.save(edit);
        }
        return null;
    }
}
