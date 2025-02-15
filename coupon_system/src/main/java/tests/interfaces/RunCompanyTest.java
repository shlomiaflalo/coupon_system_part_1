package tests.interfaces;

public interface RunCompanyTest {
    void runAllCompanyTests() throws InterruptedException;

    void loginTest();

    void addCouponTest();

    void addCouponByWrongCompanyTest();

    void addCouponThatBeenAddedTest();

    void addCouponSameTitleTest();

    void updateCouponTest();

    void updateCouponByWrongCompanyIdTest();

    void updateCouponByWrongCouponIdTest();

    void updateCouponOfAnotherCompanyTest();

    void updateCouponSameTitleTest();

    void deleteCouponTest();

    void deleteCouponByWrongCouponIdTest();

    void deleteCouponByWrongCompanyIdTest();

    void getCompanyCouponsTest();

    void getAllCompaniesWithCouponsTest();

    void getAllCompaniesWithCouponsByCategoryIdTest();

    void getAllCompaniesWithCouponsByCategoryTest();

    void getCompanyCouponsFilteredByCategoryTest();

    void getCompanyCouponsFilteredByWrongCompanyIdTest();

    void getCompanyCouponsFilteredByMaxPriceTest();

    void getCompanyCouponsFilteredByMaxPriceByWrongCompanyIdTest();

    void getCompanyDetailsTest();

    void getWrongCompanyDetailsTest();

    void getCompanyDetailsWithCouponsTest();

    void getCompanyDetailsWithCouponsWithWrongIdTest();

    void getSingleByEmailTest();

    void getSingleByWrongEmailTest();
}
