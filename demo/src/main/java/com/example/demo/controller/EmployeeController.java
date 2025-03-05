package com.example.demo.controller;

import com.example.demo.aspects.AdminOnly;
import com.example.demo.models.DTOs.IncomingInvoiceDTO;
import com.example.demo.models.DTOs.InvoiceDTO;
import com.example.demo.models.DTOs.OutInvoice;
import com.example.demo.models.DTOs.OutgoingUserDTO;
import com.example.demo.models.Invoice;
import com.example.demo.models.User;
import com.example.demo.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.AccountException;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(value= "http://localhost:5173", allowCredentials = "true")
public class EmployeeController {
    //make new invoice
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    public int getId () {
        //get access to the session so we can extract the role attribute
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);

        //First, we'll check if the session exists
        if(session == null){
            throw new IllegalArgumentException("user must be logged in to do this!");
        }
        System.out.print(session.getAttribute("userId"));
        return (int) session.getAttribute("userId");
    }
    @GetMapping("/employee/all")
    public ResponseEntity<List<OutInvoice>> getAllInvoiceId () {
        return ResponseEntity.ok(employeeService.getAllInvoicesByUserId(this.getId()));
    }
    @GetMapping("/admin/all")
    @AdminOnly
    public ResponseEntity<List<OutInvoice>> getAllInvoice () {
        return ResponseEntity.ok(employeeService.getAllInvoices());
    }
    @GetMapping("/employee/pending")
    public ResponseEntity<List<OutInvoice>> getPendingInvoiceId () {
        return ResponseEntity.ok(employeeService.getAllPendingInvoicesByUserId(this.getId()));
    }
    @GetMapping("/admin/pending")
    @AdminOnly
    public ResponseEntity<List<OutInvoice>> getPendingInvoice () {
        return ResponseEntity.ok(employeeService.getAllPendingInvoices());
    }
    @GetMapping("/admin/users")
    @AdminOnly
    public ResponseEntity<List<OutgoingUserDTO>> getAllUser () {
        return ResponseEntity.ok(employeeService.getAllUsers());
    }
    @DeleteMapping("/admin/user/{id}")
    @AdminOnly
    public ResponseEntity<OutgoingUserDTO> deleteUser (@PathVariable int id) {
        return ResponseEntity.ok(employeeService.deleteUser(id));
    }
    @PatchMapping("/admin/status")
    @AdminOnly
    public ResponseEntity<Invoice> changeStatus (@RequestBody InvoiceDTO invoice) {
        return ResponseEntity.ok(employeeService.editStatus(invoice.getId(), invoice.getDescription()));
    }
    @PatchMapping("/employee/desc")
    public ResponseEntity<Invoice> changeDesc (@RequestBody InvoiceDTO invoice) throws AccountException {
        Invoice i = employeeService.editOwnDescription(invoice.getId(), invoice.getDescription(), this.getId());
        if (i == null) {
            throw new AccountException("you don't own this invoice");
        }
        return ResponseEntity.ok(i);
    }
    @PostMapping("/employee/add")
    public ResponseEntity<Invoice> newInvoice (@RequestBody IncomingInvoiceDTO invoice) {
        return ResponseEntity.ok(employeeService.insertInvoice(invoice));
    }
}
