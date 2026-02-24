package com.valkyrie.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valkyrie.order.model.Order;
import com.valkyrie.order.model.OrderDTO;
import com.valkyrie.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService service;
    @Autowired
    private void setService(OrderService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody Order order) {
        return service.save(order);
    }

    @GetMapping("/find")
    public ResponseEntity<List<OrderDTO>> find(@RequestParam String customerId) {
        return service.find(customerId);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Boolean> cancel(@RequestParam String orderId) {
        return service.cancel(orderId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete() {
        return service.delete();
    }
}
