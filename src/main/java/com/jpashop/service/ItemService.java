package com.jpashop.service;

import com.jpashop.domain.item.Item;
import com.jpashop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne (Long id) {
        return itemRepository.findOne(id);
    }
}
