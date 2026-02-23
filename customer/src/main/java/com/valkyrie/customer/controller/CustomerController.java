package com.valkyrie.customer.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.valkyrie.customer.model.Customer;
import com.valkyrie.customer.model.CustomerDTO;
import com.valkyrie.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService service;
    @Autowired
    private void setService(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @PostMapping("/update-img")
    public ResponseEntity<Boolean> updateImg(@RequestParam String username, @RequestParam MultipartFile img) throws IOException {
        return service.updateImage(username, img);
    }

    @PostMapping("/update-customer")
    public ResponseEntity<Boolean> updateCustomer(@RequestParam String username, @RequestParam String fieldNo, @RequestParam String value) {
        return service.updateCustomer(username, fieldNo, value);
    }

    @GetMapping("/get-customer")
    public ResponseEntity<CustomerDTO> getCustomer(@RequestParam String username) {
        return service.find(username);
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity<Boolean> removeCustomer(@RequestParam String username) {
        return service.remove(username);
    }
}
