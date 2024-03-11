package com.jpashop;

import com.jpashop.domain.Address;
import com.jpashop.domain.Member;
import com.jpashop.domain.Order;
import com.jpashop.domain.OrderStatus;
import com.jpashop.domain.exception.NotEnoughStockException;
import com.jpashop.domain.item.Book;
import com.jpashop.domain.item.Item;
import com.jpashop.repository.OrderRepository;
import com.jpashop.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class OrderControllerTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); // 이름, 가격, 재고
        int orderCount = 2;

        // When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * 2, getOrder.getTotalPrice());
        assertEquals(8, item.getStockQuantity());
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 11;

        // When
        NotEnoughStockException thrown = assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
        assertEquals("need more stock", thrown.getMessage());
    }

    @Test
    public void 주문취소() {
        // Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // When
        orderService.cancelOrder(orderId);

        // Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(10, item.getStockQuantity());
    }


    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}
