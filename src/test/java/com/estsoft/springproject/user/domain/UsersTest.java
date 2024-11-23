package com.estsoft.springproject.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users("test@test.com", "password123");
    }

    @Test
    void testGetUsername() {
        assertEquals("test@test.com", user.getUsername());
    }

    @Test
    void testGetAuthorities() {
        var authorities = user.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities.stream().anyMatch(authority -> authority.getAuthority().equals("user")));
        assertTrue(authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(user.isEnabled());
    }
}