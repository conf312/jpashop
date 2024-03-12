package com.jpashop.web;

import com.jpashop.domain.item.Book;
import com.jpashop.domain.item.Item;
import com.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/item")
    public ResponseEntity<Long> create(@RequestBody Book item) {
        Long saveId = itemService.saveItem(item);
        return ResponseEntity.ok(saveId);
    }

    @GetMapping("/item")
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = itemService.findItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        Item item = itemService.findOne(id);
        return ResponseEntity.ok(item);
    }

    @PatchMapping("/item")
    public ResponseEntity<Long> update(@RequestBody Book item) {
        Item findItem = itemService.findOne(item.getId());
        findItem.setPrice(item.getPrice());
        Long id = itemService.saveItem(findItem);
        return ResponseEntity.ok(id);
    }

}
