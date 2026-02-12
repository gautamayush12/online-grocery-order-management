package com.ayush.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ayush.grocery.entity.Customer;
import com.ayush.grocery.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService (CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    //Save Customer
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    //Get Customers
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    //Get Customer By Id
    public Customer getCustomerById(Long id){
        return customerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Customer Not Found!"));
    }

    //Update Customer
    public Customer updateCustomer(Long id, Customer updatedCustomer){
        Customer existing = getCustomerById(id);

        existing.setName(updatedCustomer.getName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setAddress(updatedCustomer.getAddress());
        existing.setPhone(updatedCustomer.getPhone());

        return customerRepository.save(existing);       
    }

    //Delete Customer
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

}
