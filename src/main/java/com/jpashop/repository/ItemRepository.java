package com.jpashop.repository;

import com.jpashop.domain.item.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            // 식별자 값이 없으면 새로운 엔티티로 판단하여 persist로 영속화 한다.
            em.persist(item);
        } else {
            // 이미 한 번 영속화 되었던 엔티티로 판단하여 merge()로 수정(병합) 한다.
            em.merge(item); // 준영속 상태의 엔티티를 수정할 때 사용한다.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
