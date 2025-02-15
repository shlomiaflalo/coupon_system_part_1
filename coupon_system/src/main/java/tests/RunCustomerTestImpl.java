package tests;

import Consolemessages.AsciiLogos;
import Consolemessages.ConsoleColorsUtil;
import beans.Category;
import beans.Customer;
import dao.CustomerDAO;
import dao.dbdao.CustomerDBDAO;
import exception.CouponSystemException;
import facade.CustomerFacade;
import security.ClientType;
import security.LoginManager;
import tests.interfaces.RunCustomerTest;

import java.sql.SQLException;

/**
 * The {@code RunCustomerTestImpl} class implements the {@code RunCustomerTest} interface and is
 * responsible for running various tests related to customer operations. It uses the singleton
 * design pattern to ensure only one instance of this class exists.
 *
 * It performs a suite of tests to verify the functionality of the customer facade, including
 * login operations, coupon purchases, and retrieval of customer details and coupons.
 * The tests are divided into those that should pass with valid data and those that should fail
 * with invalid data to check exception handling and other edge cases.
 */
public class RunCustomerTestImpl implements RunCustomerTest {
    private static CustomerFacade facade = null;
    private static LoginManager loginManager = null;
    private static CustomerDAO customerDAO = null;
    private static Customer customer = null;

    private final static RunCustomerTestImpl instance = new RunCustomerTestImpl();
    private final static int customerId = customer.getId();
    private final static String customerEmail = customer.getEmail();
    private final static String customerPassword = customer.getPassword();
    private static final int timeForThreadDelayMessage = 600;


    private RunCustomerTestImpl() {
        try {
            customerDAO = CustomerDBDAO.getInstance();
            loginManager = LoginManager.getInstance();
            customer = customerDAO.getLastRecordCustomer();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static RunCustomerTestImpl getInstance() {
        return instance;
    }


    @Override
    public void runAllCustomerTests() throws InterruptedException {
        Thread.sleep(timeForThreadDelayMessage);
        AsciiLogos.customerLogo();
        //Sign in first as a customer
        loginTest();

        //Testing all customer facade functions with current data
        ConsoleColorsUtil.printSuccessfulMessage("Starting tests that should works...");
        //Thread is for delaying the message and test result for better reading
        Thread.sleep(timeForThreadDelayMessage);
        purchaseTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCustomerCouponsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCustomerCouponsByCategoryTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCustomerCouponsByMaxPriceTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCustomerDetailsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCustomersWithCouponsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCustomersWithCouponsByCategoryIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCustomersWithCouponsByCategoryTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCustomerDetailsWithCouponsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getSingleByEmailTest();
        Thread.sleep(timeForThreadDelayMessage);

        //Testing all customer facade functions with wrong data to check exceptions and situations
        //that must fail
        ConsoleColorsUtil.printFailedMessage("Starting tests that shouldn't works...");
        wrongPurchasedThisCouponTest();
        Thread.sleep(timeForThreadDelayMessage);
        wrongPurchasedByWrongCustomerIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        wrongPurchasedByWrongCouponIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        getWrongCustomerCouponsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getWrongCustomerCouponsByCategoryTest();
        Thread.sleep(timeForThreadDelayMessage);
        getWrongCustomerCouponsByMaxPriceTest();
        Thread.sleep(timeForThreadDelayMessage);
        getWrongCustomerDetailsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCustomersWithCouponsByWrongCategoryIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCustomersWithCouponsByWrongCategoryTest();
        Thread.sleep(timeForThreadDelayMessage);
        getWrongCustomerDetailsWithCouponsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getSingleByEmailWrongTest();
        Thread.sleep(timeForThreadDelayMessage);
    }

    @Override
    public void loginTest() {
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Customer logged in successfully");
            System.out.println(customer);
            facade = (CustomerFacade) loginManager.login(customerEmail,customerPassword, ClientType.CUSTOMER);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Executes the purchase of a coupon for a customer. This method attempts to purchase
     * the first available coupon for the customer identified by `customerId`.
     * If the purchase is successful, a success message is printed to the console.
     * If an error occurs during the purchase process, the error message is printed to the console.
     *
     * Exceptions:
     * - CouponSystemException: Thrown if there are issues related to the coupon system.
     * - SQLException: Thrown if there are database access issues.
     */
    @Override
    public void purchaseTest()  {
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Customer purchased coupon successfully");
            facade.purchase(customerId,facade.getFirstRecordCoupon().getId());
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void wrongPurchasedByWrongCustomerIdTest()  {
        try {
            //Purchase coupon by wrong customer that not exists - should throw an CouponSystemException
            facade.purchase(100, facade.getLastRecordCoupon().getId());
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void wrongPurchasedByWrongCouponIdTest()  {
        try {
            //Purchase coupon by customer, wrong CouponId that not exists - should throw an CouponSystemException
            facade.purchase(customerId,100);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void wrongPurchasedThisCouponTest()  {
        try {
            //Purchase coupon that a customer already bought - should throw an CouponSystemException
            facade.purchase(customerId,facade.getFirstRecordCoupon().getId());
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCustomerCouponsTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all customer's coupons purchases (if exists)");
            System.out.println("Customer that bought it: "+customer);
            facade.getCustomerCoupons(customerId).forEach(System.out::println);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getWrongCustomerCouponsTest(){
        try {
            //Trying to get coupons by customer that not exists - should throw an CouponSystemException
            facade.getCustomerCoupons(100);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCustomerCouponsByCategoryTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all customer's coupons by category 'BEAUTY' \n (if customer have it)");
            facade.getCustomerCoupons(customerId, Category.BEAUTY).forEach(System.out::println);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getWrongCustomerCouponsByCategoryTest(){
        try {
            //Trying to get customer's coupons filtered by category 'BEAUTY' with wrong customerId - should
            //throw an CouponSystemException
            facade.getCustomerCoupons(100, Category.BEAUTY);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCustomerCouponsByMaxPriceTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all customer's coupons by max price of 30 dollars \n (if customer have it)");
            facade.getCustomerCoupons(customerId, 30).forEach(System.out::println);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getWrongCustomerCouponsByMaxPriceTest(){
        try {
            //Trying to get customer's coupons filtered by max price of 30 dollars with wrong customerId - should
            //throw an CouponSystemException
            facade.getCustomerCoupons(100, 30);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCustomerDetailsTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you one customer as requested \n (if customer wasn't removed)");
            Customer customer = facade.getCustomerDetails(customerId);
            System.out.println(customer);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getWrongCustomerDetailsTest(){
        try {
            //Trying to get customer that not exists - should throw an CouponSystemException
            facade.getCustomerDetails(100);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getAllCustomersWithCouponsTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all customers with coupons as requested (if any customer bought coupon only) ");
            facade.getAllCustomersWithCoupons().forEach(System.out::println);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void getAllCustomersWithCouponsByCategoryIdTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all customers with filtered coupons by category number 1 (FOOD) as requested" +
                    "\n(if any customer bought coupon only - from FOOD category)");
            facade.getAllCustomersWithCouponsByCategoryId(1).forEach(System.out::println);
        }catch (SQLException | CouponSystemException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void getAllCustomersWithCouponsByWrongCategoryIdTest(){
        try {
            //Trying to get customers with filtered coupons by categoryId that not exists - should
            //throw an SQLException or CouponSystemException
            facade.getAllCustomersWithCouponsByCategoryId(50);
        }catch (SQLException | CouponSystemException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getAllCustomersWithCouponsByCategoryTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all customers with filtered coupons by category 'Food' as requested"
            +"\n(if any customer bought coupon only - from FOOD category)");
            facade.getAllCustomersWithCouponsByCategory(Category.FOOD).forEach(System.out::println);
        }catch (SQLException | CouponSystemException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getAllCustomersWithCouponsByWrongCategoryTest(){
        try {
            //Trying to get customers with filtered coupons by category (American movies) that not exists - should
            //throw an SQLException or CouponSystemException
            facade.getAllCustomersWithCouponsByCategory(Category.valueOf("American movies"));
        }catch (IllegalArgumentException e){
            ConsoleColorsUtil.printFailedMessage("Enum category American movies is wrong and not exists");
        }catch (SQLException | CouponSystemException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCustomerDetailsWithCouponsTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you a customer details" +
                    " with coupons as requested \n(if customer bought the any coupon only)");
            Customer customer = facade.getCustomerDetailsWithCoupons(customerId);
            if(customer == null){
                ConsoleColorsUtil.printFailedMessage("No customer found with coupons with " +
                        "customer id " + customerId);
            }else{
                ConsoleColorsUtil.printSuccessfulMessage("Customer: " +customer);
            }
        }catch (SQLException | CouponSystemException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getWrongCustomerDetailsWithCouponsTest(){
        try {
            //Trying to get wrong customerId details with coupons - should
            //throw an SQLException or get nothing
            facade.getCustomerDetailsWithCoupons(100);
        }catch (SQLException | CouponSystemException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getSingleByEmailTest(){
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully got you a customer details by email as requested (if customer wasn't removed)");
            Customer customer = facade.getSingleByEmail(customerEmail);
            System.out.println(customer);
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getSingleByEmailWrongTest(){
        try {
            //Trying to get wrong customer details with email that not exists - should
            //throw an CouponSystemException
            facade.getSingleByEmail("MosheMoshe@MosheMoshe.com");
        }catch (CouponSystemException | SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
