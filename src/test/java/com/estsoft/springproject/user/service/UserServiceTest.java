package com.estsoft.springproject.user.service;

import com.estsoft.springproject.user.domain.Users;
import com.estsoft.springproject.user.domain.dto.AddUserRequest;
import com.estsoft.springproject.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository repository;

    @Spy
    BCryptPasswordEncoder encoder;

    @Test
    public void testSave() {
        // given
        String email = "mock_email";
        String password = "mock_password";
        AddUserRequest request = new AddUserRequest();
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        request.setEmail(email);
        request.setPassword(pwEncoder.encode(password));

//        Mockito.when(repository.save(any())).thenReturn(new Users(request.getEmail(), request.getPassword()));
        Mockito.doReturn(new Users(email, password))
                .when(repository).save(any(Users.class));

        // when
        Users returnUser = userService.save(request);

        // then
//        assertEquals(request.getEmail(), returnUser.getEmail());
//        assertEquals(request.getPassword(), returnUser.getPassword());
        assertThat(returnUser.getEmail(), is(email));

        verify(repository, times(1)).save(any());
        verify(encoder, times(1)).encode(any());
    }
}