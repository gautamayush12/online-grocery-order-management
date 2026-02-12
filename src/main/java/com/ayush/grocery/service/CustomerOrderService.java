package com.ayush.grocery.service;

import com.ayush.grocery.entity.Customer;
import com.ayush.grocery.entity.CustomerOrder;
import com.ayush.grocery.entity.GroceryItem;
import com.ayush.grocery.repository.CustomerOrderRepository;
import com.ayush.grocery.repository.CustomerRepository;
import com.ayush.grocery.repository.GroceryItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final GroceryItemRepository itemRepository;

    public CustomerOrderService(CustomerOrderRepository orderRepository,
                                CustomerRepository customerRepository,
                                GroceryItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    public CustomerOrder createOrder(Long customerId, Set<Long> itemIds) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Set<GroceryItem> items = itemIds.stream()
                .map(id -> itemRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Item not found")))
                .collect(java.util.stream.Collectors.toSet());

        double totalPrice = items.stream()
                .mapToDouble(item -> item.getPrice().doubleValue())
                .sum();

        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setItems(items);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public CustomerOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
