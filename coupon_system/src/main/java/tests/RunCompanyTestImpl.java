package tests;

import Consolemessages.AsciiLogos;
import Consolemessages.ConsoleColorsUtil;
import beans.Category;
import beans.Company;
import beans.Coupon;
import dao.CompanyDAO;
import dao.CouponDAO;
import dao.dbdao.CompanyDBDAO;
import dao.dbdao.CouponDBDAO;
import exception.CouponSystemException;
import facade.CompanyFacade;
import security.ClientType;
import security.LoginManager;
import tests.interfaces.RunCompanyTest;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * The RunCompanyTestImpl class implements all methods defined in the RunCompanyTest interface
 * to perform a comprehensive set of tests related to company operations within the coupon system.
 */
public class RunCompanyTestImpl implements RunCompanyTest {

    private static CompanyFacade facade = null;

    private static LoginManager loginManager = null;
    private static CompanyDAO companyDAO = null;
    private static CouponDAO couponDAO = null;
    private static Company company = null;

    private final static RunCompanyTestImpl instance = new RunCompanyTestImpl();
    private final static int companyId = company.getId();
    private final static String companyEmail = company.getEmail();
    private final static String companyPassword = company.getPassword();
    private static final int timeForThreadDelayMessage = 600;


    private RunCompanyTestImpl() {
        try {
            companyDAO = CompanyDBDAO.getInstance();
            couponDAO = CouponDBDAO.getInstance();
            loginManager = LoginManager.getInstance();
            company = companyDAO.getLastRecordCompany();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static RunCompanyTestImpl getInstance() {
        return instance;
    }

    @Override
    public void runAllCompanyTests() throws InterruptedException {
        Thread.sleep(timeForThreadDelayMessage);
        AsciiLogos.companyLogo();
        //Sign in first as a company
        loginTest();

        //Testing all company facade functions with current data
        ConsoleColorsUtil.printSuccessfulMessage("Starting tests that should works...");
        //Thread is for delaying the message and test result for better reading
        Thread.sleep(timeForThreadDelayMessage);
        addCouponTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateCouponTest();
        Thread.sleep(timeForThreadDelayMessage);
        deleteCouponTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCompanyCouponsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCompaniesWithCouponsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCompaniesWithCouponsByCategoryIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        getAllCompaniesWithCouponsByCategoryTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCompanyCouponsFilteredByCategoryTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCompanyCouponsFilteredByMaxPriceTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCompanyDetailsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCompanyDetailsWithCouponsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getSingleByEmailTest();
        Thread.sleep(timeForThreadDelayMessage);

        //Testing all company facade functions with wrong data to check exceptions
        ConsoleColorsUtil.printFailedMessage("Starting tests that shouldn't works...");
        Thread.sleep(timeForThreadDelayMessage);
        addCouponByWrongCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        addCouponThatBeenAddedTest();
        Thread.sleep(timeForThreadDelayMessage);
        addCouponSameTitleTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateCouponByWrongCompanyIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateCouponByWrongCouponIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateCouponOfAnotherCompanyTest();
        Thread.sleep(timeForThreadDelayMessage);
        updateCouponSameTitleTest();
        Thread.sleep(timeForThreadDelayMessage);
        deleteCouponByWrongCouponIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        deleteCouponByWrongCompanyIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCompanyCouponsFilteredByWrongCompanyIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCompanyCouponsFilteredByMaxPriceByWrongCompanyIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        getWrongCompanyDetailsTest();
        Thread.sleep(timeForThreadDelayMessage);
        getCompanyDetailsWithCouponsWithWrongIdTest();
        Thread.sleep(timeForThreadDelayMessage);
        getSingleByWrongEmailTest();
        Thread.sleep(timeForThreadDelayMessage);
    }

    @Override
    public void loginTest() {
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Company logged in successfully");
            facade = (CompanyFacade) loginManager.login(companyEmail, companyPassword, ClientType.COMPANY);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCouponTest()  {
        Date startDate = Date.valueOf(LocalDate.now());
        Date endDate = Date.valueOf(LocalDate.now().plusDays(3));
        Coupon coupon = new Coupon(0,company.getId(), beans.Category.getRandomCategory(),"50% off the 'spring collections' :) using java",
                "In israel",startDate,endDate,20,5.5,"path: https//www.johnBryce.co.il/Image");
        try {
            facade.addCoupon(coupon.getCompanyId(), coupon);
            ConsoleColorsUtil.printSuccessfulMessage("Successfully added the coupon\n"+couponDAO.getLastRecordCoupon());
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCouponByWrongCompanyTest()  {
        try {
            //Trying to Add coupon by wrong company that not exists - should throw an CouponSystemException
            facade.addCoupon(100, couponDAO.getFirstRecordCoupon());
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCouponThatBeenAddedTest()  {
        try {
            //Trying to add coupon that has been added already - should throw an CouponSystemException
            facade.addCoupon(companyId,couponDAO.getFirstRecordCoupon());
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCouponSameTitleTest()  {
        try {
            //Trying to add coupon that has same title like another coupon that belong to
            //that companyId - should throw an CouponSystemException

            //Taking record that exists on db and change set its id
            //But not changing its title for the test
            Coupon coupon = couponDAO.getLastRecordCoupon();
            coupon.setId(300);

            //I don't use the companyId value because I deleted its coupon on the 1st part of the test
            facade.addCoupon(coupon.getCompanyId(), coupon);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateCouponTest()  {
        Date startDate = Date.valueOf(LocalDate.now().plusDays(3));
        Date endDate = Date.valueOf(LocalDate.now().plusDays(9));
        try {
            Coupon coupon = new Coupon(couponDAO.getLastRecordCoupon().getId(),
                    companyId, beans.Category.getRandomCategory(),"75% off the" +
                    " 'spring collections' :) using java",
                    "In Morocco",startDate,endDate,23,6.6,
                    "path: https//www.johnBryce.co.il/Image");

            facade.updateCoupon(coupon.getCompanyId(), coupon);
            ConsoleColorsUtil.printSuccessfulMessage("Successfully updated the coupon\n"+coupon);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateCouponByWrongCompanyIdTest()  {
        try {
            //Trying to Update the last record with wrong companyId
            //should throw an CouponSystemException
            Coupon coupon = couponDAO.getLastRecordCoupon();
            coupon.setPrice(500);
            coupon.setDescription("Some description");

            facade.updateCoupon(500, coupon);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateCouponByWrongCouponIdTest()  {
        try {
            //Trying to Update the last record with wrong couponId
            //should throw an CouponSystemException
            facade.updateCoupon(companyId, new Coupon());
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateCouponOfAnotherCompanyTest()  {
        try {
            //Trying to Update coupon that belong to another company
            //should throw an CouponSystemException
            facade.updateCoupon(companyId, couponDAO.getFirstRecordCoupon());
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateCouponSameTitleTest()  {
        try {
            //Trying to update coupon that has same title like another coupon that belong to
            //that companyId if company have any coupons of course -
            //should throw an CouponSystemException

            //On the update if a company doesn't change its coupon title
            //It's will let it update the coupon but - if a company update its coupon title to
            //another title that exists already on this company coupons we will get
            //'CouponSystemException' (COUPON_TITLE_EXISTS_FOR_THIS_COMPANY)

            //Adding 2 coupons to the companyId for testing, Taking the other coupon's title
            //that belong to that company and insert it to the other and test the CouponSystemException

            Date startDate = Date.valueOf(LocalDate.now().plusDays(4));
            Date endDate = Date.valueOf(LocalDate.now().plusDays(9));
            Coupon couponFirst = new Coupon(0,companyId, beans.Category.getRandomCategory(),"50% off the 'spring collections' :) using java",
                    "In israel",startDate,endDate,20,5.5,"path: https//www.johnBryce.co.il/Image");

            //Adding 1st coupon to company
            facade.addCoupon(company.getId(), couponFirst);

            //Saving the coupon that been inserted just now for later use
            Coupon couponFirstFromDb = couponDAO.getLastRecordCoupon();

            Coupon couponSecondFromDb = couponDAO.getLastRecordCoupon();
            couponSecondFromDb.setTitle("Some Title");
            couponSecondFromDb.setId(30);
            //Adding 2nd coupon to company with different title
            facade.addCoupon(companyId, couponSecondFromDb);

            //Now after inserting 2 coupons for the company - we take title from first coupon and put it
            //in the second coupon and try to update it to the db - According to
            //the instructions of the project
            //Company can't have duplicates titles for its coupons
            couponSecondFromDb = couponDAO.getLastRecordCoupon();
            couponSecondFromDb.setTitle(couponFirstFromDb.getTitle());

            facade.updateCoupon(companyId,couponSecondFromDb);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void deleteCouponTest()  {
        try {
            ConsoleColorsUtil.printSuccessfulMessage("Successfully deleted the coupon\n"+couponDAO.getLastRecordCoupon());
            facade.deleteCoupon(companyId,couponDAO.getLastRecordCoupon().getId());
        } catch (CouponSystemException | SQLException e) {
        System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteCouponByWrongCouponIdTest()  {
        try {
            facade.deleteCoupon(companyId,700);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteCouponByWrongCompanyIdTest()  {
        try {
            facade.deleteCoupon(700,couponDAO.getLastRecordCoupon().getId());
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCompanyCouponsTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all company's coupons (if exists)");
        try {
         List<Coupon> companyCoupons= facade.getCompanyCoupons(company.getId());
         if(!companyCoupons.isEmpty()){
             System.out.println("This coupons belong to this company: "+company);
             companyCoupons.forEach(System.out::println);
         }
        } catch (CouponSystemException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void getAllCompaniesWithCouponsTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all companies coupons (if exists)");
        try {
           facade.getAllCompaniesWithCoupons().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getAllCompaniesWithCouponsByCategoryIdTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all companies with coupons filtered by categoryId (if exists)");
        try {
            facade.getAllCompaniesWithCouponsByCategoryId(1).forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void getAllCompaniesWithCouponsByCategoryTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all companies with coupons filtered by category (FOOD) - (if exists)");
        try {
            facade.getAllCompaniesWithCouponsByCategory(Category.FOOD).forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCompanyCouponsFilteredByCategoryTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all company's coupons filtered by category (FOOD) - (if exists)");
        try {
            facade.getCompanyCoupons(company.getId(),Category.FOOD).forEach(System.out::println);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCompanyCouponsFilteredByWrongCompanyIdTest(){
        try {
            facade.getCompanyCoupons(70,Category.FOOD).forEach(System.out::println);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCompanyCouponsFilteredByMaxPriceTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you all company's coupons filtered by max price of 30$ - (if exists)");
        try {
            facade.getCompanyCoupons(company.getId(),30).forEach(System.out::println);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCompanyCouponsFilteredByMaxPriceByWrongCompanyIdTest(){
        try {
            facade.getCompanyCoupons(70,30).forEach(System.out::println);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCompanyDetailsTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you company info - (if not been removed)");
        try {
            System.out.println(facade.getCompanyDetails(company.getId()));
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getWrongCompanyDetailsTest(){
        try {
            facade.getCompanyDetails(600);
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getCompanyDetailsWithCouponsTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you company info with coupons - (if not been removed \n and if company having coupons only)");
        try {
            Company company = facade.getCompanyDetailsWithCoupons(facade.getFirstCompanyRecord().getId());
            if(company!=null){
                System.out.println(company);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void getCompanyDetailsWithCouponsWithWrongIdTest(){
        try {
            facade.getCompanyDetailsWithCoupons(70);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getSingleByEmailTest(){
        ConsoleColorsUtil.printSuccessfulMessage("Successfully got you company info by email - (if not been removed)");
        try {
            System.out.println(facade.getSingleByEmail(company.getEmail()));
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void getSingleByWrongEmailTest(){
        try {
            //Find a company by wrong email that not exist
            facade.getSingleByEmail("MosheMoshe@JustForException.com");
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }





}
