package jobs;

import dao.CouponDAO;
import dao.dbdao.CouponDBDAO;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * The CouponExpirationDailyJob class is responsible for removing expired coupons from the database.
 * It implements the Runnable interface and uses a CouponDAO instance to interact with the database.
 * This job is intended to run once every day.
 */
public class CouponExpirationDailyJob implements Runnable{
    private final static CouponExpirationDailyJob instance = new CouponExpirationDailyJob();
    private CouponDAO couponDAO;
    private boolean quit;

    public static CouponExpirationDailyJob getInstance() {
        return instance;
    }

    private CouponExpirationDailyJob() {
        couponDAO = CouponDBDAO.getInstance();
        quit = false;
    }

    @Override
    public void run() {

            while(!quit){
                try {
                    couponDAO.removeAllExpiredCoupons();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    TimeUnit.HOURS.sleep(24);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


    }

    public void stop(){
        this.quit = true;
    }
}
