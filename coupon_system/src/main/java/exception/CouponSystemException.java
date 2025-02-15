package exception;

public class CouponSystemException extends Exception {
    public CouponSystemException(ErrorMessage message) {
        super(message.getMessage());
    }
}
