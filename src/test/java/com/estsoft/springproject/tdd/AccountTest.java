package com.estsoft.springproject.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class AccountTest {
    Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(new BigDecimal("10000"));
    }

    @DisplayName("계좌 생성")
    @Test
    public void testAccount() {
        assertThat(account.getBalance(), is(BigDecimal.valueOf(10000)));    // hamcrest로 검증

        account = new Account(new BigDecimal("20000"));
        assertThat(account.getBalance(), is(BigDecimal.valueOf(20000)));

        account = new Account(new BigDecimal("50000"));
        assertThat(account.getBalance(), is(BigDecimal.valueOf(50000)));
    }

    @DisplayName("입금")
    @Test
    public void testDeposit() {
        account.deposit(new BigDecimal("100000"));
        assertThat(account.getBalance(), is(BigDecimal.valueOf(110000)));
    }

    @DisplayName("출금")
    @Test
    public void testWithdraw() {
        account.withdraw(new BigDecimal("10000"));
        assertThat(account.getBalance(), is(BigDecimal.ZERO));
    }
}