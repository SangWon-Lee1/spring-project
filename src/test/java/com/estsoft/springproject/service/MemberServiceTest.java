package com.estsoft.springproject.service;

import com.estsoft.springproject.entity.Member;
import com.estsoft.springproject.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        member1 = new Member(1L, "name1");
        member2 = new Member(2L, "name2");
    }

    @Test
    void testGetAllMembers() {
        // Given
        List<Member> memberList = Arrays.asList(member1, member2);
        when(memberRepository.findAll()).thenReturn(memberList);

        // When
        List<Member> result = memberService.getAllMembers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testSaveMember() {
        // Given
        when(memberRepository.save(any(Member.class))).thenReturn(member1);

        // When
        Member savedMember = memberService.saveMember(member1);

        // Then
        assertNotNull(savedMember);
        assertEquals("name1", savedMember.getName());
        verify(memberRepository, times(1)).save(member1);
    }
}