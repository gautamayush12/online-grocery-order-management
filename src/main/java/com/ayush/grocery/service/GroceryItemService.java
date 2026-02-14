package com.ayush.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ayush.grocery.entity.GroceryItem;
import com.ayush.grocery.exception.ResourceNotFoundException;
import com.ayush.grocery.repository.GroceryItemRepository;

@Service
public class GroceryItemService {
    private final GroceryItemRepository groceryItemRepository;

    //Dependency Injection
    public GroceryItemService(GroceryItemRepository groceryItemRepository){
        this.groceryItemRepository = groceryItemRepository;
    }

    //Create Item
    public GroceryItem createItem(GroceryItem item){
        return groceryItemRepository.save(item);
    }

    //Get All Items
    public List<GroceryItem> getAllItems(){
        return groceryItemRepository.findAll();
    }

    //Get Item By Id
    public GroceryItem getItemById(Long id){
        return groceryItemRepository.findById(id).
        orElseThrow(() -> new ResourceNotFoundException("Item Not Found!"));
    }

    //Update Item
    public GroceryItem updateItem(Long id, GroceryItem updatedItem){
        GroceryItem existing = getItemById(id);

        existing.setName(updatedItem.getName());
        existing.setCategory(updatedItem.getCategory());
        existing.setPrice(updatedItem.getPrice());
        existing.setQuantity(updatedItem.getQuantity());

        return groceryItemRepository.save(existing);
    }

    //Delete Item
    public void deleteItem(Long id){
        groceryItemRepository.deleteById(id);
    }
}
