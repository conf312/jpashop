package com.jpashop;

import com.jpashop.domain.Member;
import com.jpashop.repository.MemberRepository;
import com.jpashop.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class MemberControllerTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveId));
    }
}
