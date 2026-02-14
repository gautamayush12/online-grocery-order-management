package com.ayush.grocery.controller;

import com.ayush.grocery.entity.CustomerOrder;
import com.ayush.grocery.service.CustomerOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")  // Base URL for Order APIs
public class CustomerOrderController {

    private final CustomerOrderService orderService;

    // Constructor Injection
    public CustomerOrderController(CustomerOrderService orderService) {
        this.orderService = orderService;
    }

    // ---------------- CREATE ORDER ----------------
    // Example:
    // POST /orders?customerId=1&itemIds=2&itemIds=3
    @PostMapping
    public ResponseEntity<CustomerOrder> createOrder(
            @RequestParam Long customerId,
            @RequestParam Set<Long> itemIds
    ) {

        CustomerOrder createdOrder =
                orderService.createOrder(customerId, itemIds);

        // Ideally 201 CREATED should be returned
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // ---------------- GET ALL ORDERS ----------------
    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllOrders() {

        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ---------------- GET ORDER BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOrder(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // ---------------- UPDATE ORDER ----------------
    // Example:
    // PUT /orders/1?itemIds=2&itemIds=4
    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrder> updateOrder(
            @PathVariable Long id,
            @RequestParam Set<Long> itemIds
    ) {

        CustomerOrder updatedOrder =
                orderService.updateOrder(id, itemIds);

        return ResponseEntity.ok(updatedOrder);
    }

    // ---------------- DELETE ORDER ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long id
    ) {

        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}
