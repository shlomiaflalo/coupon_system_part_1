package exception;
import static Consolemessages.ConsoleColorsUtil.WHITE_BACKGROUND_BRIGHT;
import static Consolemessages.ConsoleColorsUtil.RED_BOLD;
import static Consolemessages.ConsoleColorsUtil.RESET;

/**
 * Enum representing various error messages used in the coupon system.
 * Each enum constant holds a specific error message string.
 */
public enum ErrorMessage {
    COMPANY_NOT_FOUND("Company does not exists"),
    COMPANY_ID_ALREADY_EXISTS("Impossible to change company id"),
    COMPANY_NAME_ALREADY_EXISTS("Company name already exists"),
    COMPANY_EMAIL_ALREADY_EXISTS("Company email already exists"),
    CANNOT_EDIT_COMPANY_NAME_AND_ID("Company name and id cannot be change"),
    COUPON_ALREADY_EXISTS("Coupon already exists"),
    COUPON_INVENTORY_IS_0("Coupon already exists"),
    COUPON_NOT_EXISTS("Coupon not exists and therefore you cannot purchase it"),
    CUSTOMER_BOUGHT_THIS_COUPON_BEFORE("Customer already bought this coupon"),
    NO_COUPONS_FOR_CUSTOMER("Customer do not have any coupons"),
    NO_COUPONS_FOR_CUSTOMER_BY_CATEGORY("Customer do not have any coupons order by category"),
    DUE_DATE_COUPON("Unfortunately, The due date of this coupon is over and therefore you can't purchase it"),
    COUPON_IS_OUT_OF_STOCK("Coupon is out of stock and its amount is 0"),
    EMAIL_IS_NOT_CORRECT("Email is not correct"),
    EMAIL_IS_NOT_FOUND("Email is not found"),
    EMAIL_AND_PASSWORD_IS_NOT_CORRECT("Email and password is not correct"),
    PASSWORD_IS_NOT_CORRECT("Password is not correct"),
    CUSTOMER_IS_NOT_EXISTS("customer is not exists"),
    CUSTOMER_EMAIL_DO_NOT_MATCH_TO_CUSTOMER_ID("No matching between customer id and existing email"),
    CUSTOMER_IS_ALREADY_EXISTS("customer is already exists"),
    EMAIL_IN_USE_BY_ANOTHER_CUSTOMER("Another customers using this email address already"),
    CUSTOMER_BOUGHT_THIS_COUPON_TYPE_BEFORE("Customer already have this coupon type and can buy just 1 coupon from every category"),
    COUPON_TITLE_EXISTS_FOR_THIS_COMPANY("A company can't have same title for its coupons"),
    CANNOT_EDIT_COUPON_OF_ANOTHER_COMPANY("Coupon is belonging to another company and you can't update it"),
    WRONG_CATEGORY("Cannot get customers with coupons filtered by wrong category"),
    COUPON_MAX_PRICE_IS_OUT_OF_RANGE("Coupon max price can't be lower or equal to 0 or bigger than 100k");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return WHITE_BACKGROUND_BRIGHT+RED_BOLD+message+RESET;
    }

}
