package com.ayush.grocery.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.grocery.entity.GroceryItem;
import com.ayush.grocery.service.GroceryItemService;

@RestController
@RequestMapping("/items")
public class GroceryItemController {

    //Dependency Injection
    private final GroceryItemService itemService;
    public GroceryItemController(GroceryItemService itemService){
        this.itemService = itemService;
    }

    //Create Item
    @PostMapping
    public ResponseEntity<GroceryItem> createItem(@RequestBody GroceryItem item){
        return new ResponseEntity<>(
            itemService.createItem(item),
            HttpStatus.CREATED
        );
    }

    //Get All Items
    @GetMapping
    public ResponseEntity<List<GroceryItem>> getAllItems(){
        return ResponseEntity.ok(itemService.getAllItems());
    }

    //Get Item by id
    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> getItem(@PathVariable Long id){
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    //Update Item
    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> updateItem(@PathVariable Long id,
        @RequestBody GroceryItem item
    ){
        return ResponseEntity.ok(itemService.updateItem(id, item));
    }

    //Delete item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}
