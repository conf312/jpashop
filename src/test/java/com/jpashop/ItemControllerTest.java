package com.jpashop;

import com.jpashop.domain.Category;
import com.jpashop.domain.item.Album;
import com.jpashop.domain.item.Book;
import com.jpashop.domain.item.Item;
import com.jpashop.domain.item.Movie;
import com.jpashop.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ItemControllerTest {
    @Autowired
    private ItemService itemService;

    @Test
    public void 상품등록() {
        // Given
        Book book = new Book();
        book.setName("[Book] ORM 표준 JPA 프로그래밍");
        book.setAuthor("김영한");
        book.setIsbn("ISBN_ORM_JPA#00001");
        book.setPrice(25000);
        book.setStockQuantity(10);

        Album album = new Album();
        album.setName("[Album] ORM 표준 JPA 프로그래밍");
        album.setArtist("아티스트");
        album.setEtc("ETC");
        album.setPrice(25000);
        album.setStockQuantity(20);

        Movie movie = new Movie();
        movie.setName("[Movie] ORM 표준 JPA 프로그래밍");
        movie.setActor("배우");
        movie.setDirector("감독");
        movie.setPrice(25000);
        movie.setStockQuantity(30);

        List<Item> items = new ArrayList<>();
        items.add(book);
        items.add(album);
        items.add(movie);

        Category category = new Category();
        category.setName("아이템 세트");
        category.setItems(items);

        // When
        Long saveId = itemService.saveItem(book);

        // Then
        assertTrue(itemService.findOne(saveId).getId() > 0);
        assertTrue(itemService.findItems().stream().anyMatch(item -> item.getId().equals(saveId)));
    }
}
