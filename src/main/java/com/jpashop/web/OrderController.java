package com.jpashop.web;

import com.jpashop.service.ItemService;
import com.jpashop.service.MemberService;
import com.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @PostMapping("/order")
    public ResponseEntity<Long> order(@RequestParam("memberId") Long memberId,
                                                        @RequestParam("itemId") Long itemId,
                                                        @RequestParam("count") int count) {
        Long orderId = orderService.order(memberId, itemId, count);
        return ResponseEntity.ok(orderId);
    }
}
