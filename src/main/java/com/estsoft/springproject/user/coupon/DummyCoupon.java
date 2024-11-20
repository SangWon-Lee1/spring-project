package com.estsoft.springproject.user.coupon;

// 테스트 코드에서만 사용하는 더미객체클래스
public class DummyCoupon implements ICoupon{
    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int getDiscountPercent() {
        return 0;
    }

    @Override
    public boolean isAppliable(Item item) {
        return false;
    }

    @Override
    public void doExpire() {

    }
}
