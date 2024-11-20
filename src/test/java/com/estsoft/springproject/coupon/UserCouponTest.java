package com.estsoft.springproject.coupon;

import com.estsoft.springproject.user.coupon.DummyCoupon;
import com.estsoft.springproject.user.coupon.ICoupon;
import com.estsoft.springproject.user.coupon.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class UserCouponTest {

    @Test
    public void testAddCoupon() {
        User user = new User("area00");
        assertEquals(0, user.getTotalCouponCount()); // 쿠폰 수령 전

        ICoupon coupon = new DummyCoupon(); // dummy object를 생성해서 사용

        user.addCoupon(coupon);
        assertEquals(1, user.getTotalCouponCount()); // 쿠폰 수령 후 쿠폰수 검증
    }

    @DisplayName("쿠폰이 유효할 경우에만 유저에게 발급")
    @Test
    public void testAddCouponWithMock() {
        User user = new User("area00");
        assertEquals(0, user.getTotalCouponCount()); // 쿠폰 수령 전

        ICoupon coupon = Mockito.mock(ICoupon.class);   // mock객체 - 행위 정의 가능
//        Mockito.when(coupon.isValid()).thenReturn(true);    // stub(실제로 동작하는 것 처럼 보이게)
        // 위 아래 같음
        Mockito.doReturn(true).when(coupon).isValid();

        user.addCoupon(coupon);
        assertEquals(1, user.getTotalCouponCount()); // 쿠폰 수령 후 쿠폰수 검증
    }

    @DisplayName("쿠폰이 유효하지 않을 경우")
    @Test
    public void testAddCouponWithMock2() {
        User user = new User("area00");
        assertEquals(0, user.getTotalCouponCount()); // 쿠폰 수령 전

        ICoupon coupon = Mockito.mock(ICoupon.class);   // mock객체 - 행위 정의 가능
        Mockito.when(coupon.isValid()).thenReturn(false);

        user.addCoupon(coupon);
        assertEquals(0, user.getTotalCouponCount()); // 쿠폰 수령 후 쿠폰수 검증
    }
}