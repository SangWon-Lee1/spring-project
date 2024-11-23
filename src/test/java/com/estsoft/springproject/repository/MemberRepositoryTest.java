package com.estsoft.springproject.repository;

import com.estsoft.springproject.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(1L, "name");
    }

    @Test
    void testSaveMember() {
        Member savedMember = memberRepository.save(member);

        assertNotNull(savedMember);
        assertEquals("name", savedMember.getName());
    }

    @Test
    void testFindById() {
        // Given
        memberRepository.save(member);

        // When
        Optional<Member> foundMember = memberRepository.findById(member.getId());

        // Then
        assertTrue(foundMember.isPresent());
        assertEquals(member.getId(), foundMember.get().getId());
    }

    @Test
    void testFindAll() {
        // Given
        memberRepository.save(member);
        Member secondMember = new Member(2L, "name2");
        memberRepository.save(secondMember);

        // When
        Iterable<Member> members = memberRepository.findAll();

        // Then
        assertTrue(members.iterator().hasNext());
    }

    @Test
    void testDelete() {
        // Given
        Member savedMember = memberRepository.save(member);

        // When
        memberRepository.delete(savedMember);

        // Then
        Optional<Member> deletedMember = memberRepository.findById(savedMember.getId());
        assertFalse(deletedMember.isPresent());
    }
}