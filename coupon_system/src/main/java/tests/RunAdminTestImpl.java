package tests;

import Consolemessages.AsciiLogos;
import Consolemessages.ConsoleColorsUtil;
import beans.Company;
import beans.Customer;
import exception.CouponSystemException;
import facade.AdminFacade;
import security.ClientType;
import security.LoginManager;
import tests.interfaces.RunAdminTest;

import java.sql.SQLException;

/**
 * Implementation of the RunAdminTest interface for administrating tests.
 * This singleton class provides methods to run tests for administrative actions such as
 * adding, updating, and deleting companies and customers, as well as retrieving them.
 * The class provides both successful test scenarios and scenarios designed to fail to
 * verify exception handling.
 */
public class RunAdminTestImpl implements RunAdminTest {

    private static AdminFacade facade = null;
    private static LoginManager loginManager = null;
    private final static RunAdminTestImpl instance = new RunAdminTestImpl();
    private static final int timeForThreadDelayMessage = 600;

    private RunAdminTestImpl() {
    loginManager = LoginManager.getInstance();
    }

    public static RunAdminTestImpl getInstance() {
        return instance;
    }

    @Override
    public void runAllAdminTests() throws InterruptedException {
        Thread.sleep(timeForThreadDelayMessage);
        AsciiLogos.adminLogo();
        //Sign in first as an Admin
        loginTest();
        //Testing all Admin facade functions with current data
        ConsoleColorsUtil.printSuccessfulMessage("Starting tests that should works...");
        //Thread is for delaying the message and test result for better reading
        Thread.sleep(timeForThreadDelayMessage);
        addCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        deleteCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCompaniesTest();
        Thread.sleep(timeForThreadDelayMessage);
        getOneCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        addCustomerTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateCustomerTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCustomersTest();
        Thread.sleep(timeForThreadDelayMessage);
        getOneCustomerTest();
        Thread.sleep(timeForThreadDelayMessage);
        deleteCustomerTest();
        Thread.sleep(timeForThreadDelayMessage);
        getSingleCompanyByEmailTest();
        Thread.sleep(timeForThreadDelayMessage);
        getSingleCustomerByEmail();
        Thread.sleep(timeForThreadDelayMessage);

        //Testing all Admin facade functions with wrong data to check exceptions and situations
        //that must fail
        ConsoleColorsUtil.printFailedMessage("Starting tests that shouldn't works...");
        Thread.sleep(timeForThreadDelayMessage);
        addWrongCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateWrongCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        deleteWrongCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        getOneWrongCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        addWrongCustomerTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateWrongCustomerTest();
        Thread.sleep(timeForThreadDelayMessage);
        deleteWrongCustomerTest();
        Thread.sleep(timeForThreadDelayMessage);
        getOneWrongCustomerTest();
        Thread.sleep(timeForThreadDelayMessage);
        getWrongSingleCompanyByEmailTest();
        Thread.sleep(timeForThreadDelayMessage);
        getWrongSingleCustomerByEmail();
        Thread.sleep(timeForThreadDelayMessage);
    }

    @Override
    public void loginTest() {
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Admin logged in successfully\n");
            facade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCompanyTest() {
        Company company = new Company(0, "Intel", "intel@gmail.com", "1234", null);
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Company is being added successfully");
            facade.addCompany(company);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addWrongCompanyTest(){
        try {
            System.out.println("Getting a company from database to insert it again");
            Company company = facade.getOneCompany(7);
            //Adding company that already exists - should throw an CouponSystemException
            facade.addCompany(company);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateCompanyTest(){
        Company companyFromDb = null;
        try {
            companyFromDb = facade.getSingleCompanyByEmail("intel@gmail.com");
            companyFromDb.setEmail("intel2@gmail.com");
            companyFromDb.setPassword("123456");
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }

        try {
            ConsoleColorsUtil.printSuccessfulMessage("company updated successfully");
            facade.updateCompany(companyFromDb);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateWrongCompanyTest(){
        try {
            System.out.println("Getting a companies from database to update another existing company email");
            Company companyFromDb1 = facade.getOneCompany(5);
            Company companyFromDb2 = facade.getOneCompany(2);
            companyFromDb1.setEmail(companyFromDb2.getEmail());
            //Trying to update another company email on 'companyFromDb1' - should throw an CouponSystemException
            facade.updateCompany(companyFromDb1);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteCompanyTest(){
        int companyId = 1;
        try {
            System.out.println("This company is going to be deleted: \n"+facade.getOneCompany(companyId));
            ConsoleColorsUtil.printSuccessfulMessage("company id: "+companyId+" is deleted successfully\n");
            facade.deleteCompany(companyId);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteWrongCompanyTest(){
        int companyId = 1000;
        try {
            //Trying to delete company that don't exists - should throw an CouponSystemException
            facade.deleteCompany(companyId);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getAllCompaniesTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all companies");
            facade.getAllCompanies().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getOneCompanyTest(){
        int companyId = 2;
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you one company as requested");
            facade.getOneCompany(companyId);
            System.out.println();
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getOneWrongCompanyTest(){
        int companyId = 300;
        try {
            //Trying to get a company that do not exists - should throw an CouponSystemException
            facade.getOneCompany(companyId);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCustomerTest(){
        Customer customer = new Customer(0,"George","Orwell","George@Orwell.com","1234", null);
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully added a customer as requested");
            facade.addCustomer(customer);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addWrongCustomerTest(){
        try {
            System.out.println("Getting a customer from database to insert it again");
            Customer customer = facade.getOneCustomer(1);
            //Trying to add a customer that already exists - should throw an CouponSystemException
            facade.addCustomer(customer);
            System.out.println(customer);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateCustomerTest(){
        Customer customer = new Customer(1,"Moshe","Cohen","Moshe@Cohen.com","1234", null);
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully updated a customer with id: "+customer.getId()+" as requested");
            facade.updateCustomer(customer);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateWrongCustomerTest(){
        try {
            System.out.println("Getting a customers from database to update another existing customer email");
            Customer customer1 = facade.getOneCustomer(1);
            Customer customer2 = facade.getOneCustomer(2);
            customer1.setEmail(customer2.getEmail());
            //Trying to update a customer with another customer email - should throw an CouponSystemException
            facade.updateCustomer(customer1);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteCustomerTest(){
        int customerId = 1;
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully deleted a customer id : "+customerId+" as requested");
            facade.deleteCustomer(customerId);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteWrongCustomerTest(){
        int customerId = 350;
        try {
            //Trying to delete a customer that don't exists - should throw an CouponSystemException
            facade.deleteCustomer(customerId);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getAllCustomersTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all customers");
            facade.getAllCustomers().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getOneCustomerTest(){
        int customerId = 1;
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you one customer as requested");
            facade.getOneCustomer(customerId);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getOneWrongCustomerTest(){
        int customerId = 500;
        try {
            //Trying to get a customer that don't exists - should throw an CouponSystemException
            facade.getOneCustomer(customerId);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getSingleCompanyByEmailTest(){
        Company company;
        try {
            company = facade.getOneCompany(2);
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you one company by email as requested");
            facade.getSingleCompanyByEmail(company.getEmail());
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getWrongSingleCompanyByEmailTest(){
        Company company;
        try {
            company = facade.getOneCompany(700);
            //Trying to get a company by email that don't exists - should throw an CouponSystemException
            facade.getSingleCompanyByEmail(company.getEmail());
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getSingleCustomerByEmail(){
        Customer customer;
        try {
            customer = facade.getOneCustomer(2);
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you one company by email as requested");
            facade.getSingleCustomerByEmail(customer.getEmail());
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getWrongSingleCustomerByEmail(){
        Customer customer;
        try {
            customer = facade.getOneCustomer(700);
            //Trying to get a customer by email that don't exists - should throw an CouponSystemException
            facade.getSingleCustomerByEmail(customer.getEmail());
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }


}
