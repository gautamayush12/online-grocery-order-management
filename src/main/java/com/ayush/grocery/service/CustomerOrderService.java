package com.ayush.grocery.service;

import com.ayush.grocery.entity.Customer;
import com.ayush.grocery.entity.CustomerOrder;
import com.ayush.grocery.entity.GroceryItem;
import com.ayush.grocery.exception.ResourceNotFoundException;
import com.ayush.grocery.repository.CustomerOrderRepository;
import com.ayush.grocery.repository.CustomerRepository;
import com.ayush.grocery.repository.GroceryItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final GroceryItemRepository itemRepository;

    // Constructor Injection (Best Practice)
    public CustomerOrderService(CustomerOrderRepository orderRepository,
                                CustomerRepository customerRepository,
                                GroceryItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    // ---------------- CREATE ORDER ----------------
    public CustomerOrder createOrder(Long customerId, Set<Long> itemIds) {

        // 1️⃣ Fetch customer from DB
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        // 2️⃣ Fetch grocery items using item IDs
        Set<GroceryItem> items = itemIds.stream()
                .map(id -> itemRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Item not found with id: " + id)))
                .collect(Collectors.toSet());

        // 3️⃣ Calculate total price
        double totalPrice = items.stream()
                .mapToDouble(item -> item.getPrice().doubleValue())
                .sum();

        // 4️⃣ Create new order object
        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setItems(items);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(totalPrice);

        // 5️⃣ Save order in DB
        return orderRepository.save(order);
    }

    // ---------------- GET ALL ORDERS ----------------
    public java.util.List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    // ---------------- GET ORDER BY ID ----------------
    public CustomerOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));
    }

    // ---------------- UPDATE ORDER ----------------
    public CustomerOrder updateOrder(Long orderId, Set<Long> itemIds) {

        // 1️⃣ Fetch existing order
        CustomerOrder existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        // 2️⃣ Fetch updated items
        Set<GroceryItem> items = itemIds.stream()
                .map(id -> itemRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Item not found with id: " + id)))
                .collect(Collectors.toSet());

        // 3️⃣ Recalculate total price
        double totalPrice = items.stream()
                .mapToDouble(item -> item.getPrice().doubleValue())
                .sum();

        // 4️⃣ Update order object
        existingOrder.setItems(items);
        existingOrder.setTotalPrice(totalPrice);

        // 5️⃣ Save updated order
        return orderRepository.save(existingOrder);
    }

    // ---------------- DELETE ORDER ----------------
    public void deleteOrder(Long id) {

        // Check if order exists
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found");
        }

        orderRepository.deleteById(id);
    }
}
