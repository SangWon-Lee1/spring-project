package com.estsoft.springproject.filter;

import java.io.IOException;

import static org.mockito.Mockito.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirstFilterTest {
    private FirstFilter firstFilter;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private FilterChain mockFilterChain;

    @BeforeEach
    void setUp() {
        firstFilter = new FirstFilter();
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockFilterChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilter() throws ServletException, IOException {
        when(mockRequest.getRequestURI()).thenReturn("/test/path");

        // When
        firstFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        // Then
        verify(mockRequest, times(1)).getRequestURI();
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }
}