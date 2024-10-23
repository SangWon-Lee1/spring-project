package com.estsoft.springproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JUnitTest {
    @Test
    public void test() {
        // given
        int a = 1;
        int b = 2;

        // when : 검증하고 싶은 메소드 호출
        int sum = a + b;

        // then : when절에서 실행한 결과 검증
        // JUnit
//        Assertions.assertEquals(3, sum);
        // 위랑 같지만 다른 방법
        // assertJ
        Assertions.assertThat(sum).isEqualTo(3);

        Assertions.assertThat(sum).isEven();
        Assertions.assertThat(sum).isOdd();
    }
}
