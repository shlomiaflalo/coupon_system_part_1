package tests;

import db.ConnectionPool;
import db.CreateDataBase;
import db.InitializeDataBase;
import jobs.CouponExpirationDailyJob;
import tests.introduction.IntroductionToCouponSystem;

import java.sql.SQLException;

/**
 * Singleton class that orchestrates the process of running all tests for the coupon system.
 *
 * This class is responsible for initializing the database, introducing the system, starting
 * and stopping the daily job for removing expired coupons, and running admin, company, and
 * customer facade tests.
 */
public class RunAllTests {
    private final static RunAllTests instance = new RunAllTests();

    private RunAllTests() {
    }

    public static RunAllTests getInstance() {
        return instance;
    }

    public void runAllTests() throws InterruptedException {

        //Initializing database and filling it with some data
        try {
            CreateDataBase.dropAndCreateStrategy();
            InitializeDataBase.initializeAllData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Introduction by voice and typing of the project
        IntroductionToCouponSystem introductionToCouponSystem = new IntroductionToCouponSystem();
        Thread typing = getThread(introductionToCouponSystem);
        typing.join();

        //Starting the daily job task of removing expired coupons
        CouponExpirationDailyJob couponExpirationDailyJob = CouponExpirationDailyJob.getInstance();
        getThread(couponExpirationDailyJob);

        //Testing admin & company & customer facade functions
        RunAdminTestImpl.getInstance().runAllAdminTests();
        RunCustomerTestImpl.getInstance().runAllCustomerTests();
        RunCompanyTestImpl.getInstance().runAllCompanyTests();

        //Stopping the daily job task for removing expired coupons &
        //Closing all connections from the pool
        couponExpirationDailyJob.stop();
        ConnectionPool.getInstance().closeAllConnections();

    }

        private static Thread getThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true); //Dying together with the main once the main stops
        thread.start();
        return thread;
    }
}
