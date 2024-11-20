package com.estsoft.springproject.user.repository;

import com.estsoft.springproject.user.domain.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// 기본적으로 H2 DB사용 - 테스트 끝나면 트랜잭션 롤백됨.
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    // 사용자 이메일로 조회 기능
    @Test
    public void testFindByEmail() {
        // given : (when절 에서 조회하려는) 사용자 정보 저장
        Users user = getUser();
        userRepository.save(user);

        // when
        Users returnUser = userRepository.findByEmail(user.getEmail()).orElseThrow();

        // then
        assertThat(returnUser.getEmail(), is(user.getEmail()));
        assertThat(returnUser.getPassword(), is(user.getPassword()));
    }

    // 사용자 저장 기능
    @Test
    public void testSave() {
        // given
        Users user = getUser();

        // when
        Users saveUser = userRepository.save(user);

        // then
        assertNotNull(saveUser);
        assertThat(saveUser.getEmail(), is(user.getEmail()));
    }

    // 사용자 전체 조회 기능
    @Test
    public void testFindAll() {
        // given
        userRepository.save(getUser());
        userRepository.save(getUser());

        // when
        List<Users> userList = userRepository.findAll();

        // then
        assertThat(userList.size(), is(2));
    }

    private Users getUser() {
        String email = "test@test.com";
        String password = "pw123456";
        return new Users(email, password);
    }
}