package tests.interfaces;

public interface RunCustomerTest {
    void runAllCustomerTests() throws InterruptedException;

    void loginTest();

    void purchaseTest();

    void wrongPurchasedByWrongCustomerIdTest();

    void wrongPurchasedByWrongCouponIdTest();

    void wrongPurchasedThisCouponTest();

    void getCustomerCouponsTest();

    void getWrongCustomerCouponsTest();

    void getCustomerCouponsByCategoryTest();

    void getWrongCustomerCouponsByCategoryTest();

    void getCustomerCouponsByMaxPriceTest();

    void getWrongCustomerCouponsByMaxPriceTest();

    void getCustomerDetailsTest();

    void getWrongCustomerDetailsTest();

    void getAllCustomersWithCouponsTest();

    void getAllCustomersWithCouponsByCategoryIdTest();

    void getAllCustomersWithCouponsByWrongCategoryIdTest();

    void getAllCustomersWithCouponsByCategoryTest();

    void getAllCustomersWithCouponsByWrongCategoryTest();

    void getCustomerDetailsWithCouponsTest();

    void getWrongCustomerDetailsWithCouponsTest();

    void getSingleByEmailTest();

    void getSingleByEmailWrongTest();

}
