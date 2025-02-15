
import tests.RunAllTests;

/**
 * The Program class serves as the entry point for the application.
 *
 * The main method executes a test suite for the coupon system application. It initializes
 * the database, introduces the coupon system, starts a daily job to remove expired coupons,
 * runs various tests for admin, company, and customer functionalities, and finally stops the
 * daily job and closes all database connections.
 *
 * @throws InterruptedException if any thread has interrupted the current thread.
 */
public class Program {

    public static void main(String[] args) throws InterruptedException {
        RunAllTests.getInstance().runAllTests();
    }
}
