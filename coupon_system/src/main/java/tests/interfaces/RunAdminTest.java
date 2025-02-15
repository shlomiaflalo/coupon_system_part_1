package tests.interfaces;

public interface RunAdminTest {
    void runAllAdminTests() throws InterruptedException;

    void loginTest();

    void addCompanyTest();

    void addWrongCompanyTest();

    void updateCompanyTest();

    void updateWrongCompanyTest();

    void deleteCompanyTest();

    void deleteWrongCompanyTest();

    void getAllCompaniesTest();

    void getOneCompanyTest();

    void getOneWrongCompanyTest();

    void addCustomerTest();

    void addWrongCustomerTest();

    void updateCustomerTest();

    void updateWrongCustomerTest();

    void deleteCustomerTest();

    void deleteWrongCustomerTest();

    void getAllCustomersTest();

    void getOneCustomerTest();

    void getOneWrongCustomerTest();

    void getSingleCompanyByEmailTest();

    void getWrongSingleCompanyByEmailTest();

    void getSingleCustomerByEmail();

    void getWrongSingleCustomerByEmail();
}
