package com.valkyrie.order.service;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.valkyrie.order.model.Order;
import com.valkyrie.order.model.OrderDTO;
import com.valkyrie.order.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    private OrderRepository repo;
    @Autowired
    private void setRepo(OrderRepository repo) {
        this.repo = repo;
    }

    private String decode(String wrd) {return new String(Base64.getDecoder().decode(wrd.getBytes()));}

    public ResponseEntity<Boolean> save(Order order) {
        String orderId = UUID.randomUUID().toString();
        HttpStatus status = HttpStatus.ACCEPTED;
        Boolean isDone = true;
        order.setOrderId(orderId);

        repo.save(order);

        if (!repo.isExistByOrderId(orderId)) {
            status = HttpStatus.BAD_REQUEST;
            isDone = false;
        }

        return ResponseEntity.status(status).body(isDone);
    }
    
    public ResponseEntity<List<OrderDTO>> find(String customerId) {
        customerId = decode(customerId);
        List<Order> orders = repo.findAllOrdersByCustomerId(customerId);
        List<OrderDTO> orderDTOs = new LinkedList<>();

        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        for (Order o : orders) {
            orderDTOs.add(
                new OrderDTO().setAddress(o.getAddress()).setCustomerId(o.getCustomerId()).setCancel(o.getCancel())
                    .setOrderId(o.getOrderId()).setProductId(o.getProductId()).setSellerId(o.getSellerId())
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderDTOs);
    }

    public ResponseEntity<Boolean> cancel(String orderId) {
        orderId = decode(orderId);
        boolean isExist = repo.isExistByOrderId(orderId);
        Boolean isCanceled = repo.checkCancelStatus(orderId);

        if (!isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (isCanceled) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        isCanceled = repo.cancelOrder(orderId) == 1;

        return ResponseEntity.status(isCanceled? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(isCanceled);
    }

    @Transactional
    public ResponseEntity<Boolean> delete() {
        int isDeleted = repo.deleteLastFiveOrders();

        return ResponseEntity.status(isDeleted > 0? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(isDeleted > 0);
    }
}
