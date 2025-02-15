package db;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for converting various objects and data structures.
 */
public final class ConvertUtils {

    // This method converts a ResultSet into a List of Maps, where each Map represents a row in the ResultSet.
// Each Map has column names as keys and their corresponding values from the row as values.
// It throws SQLException if there are any issues accessing the ResultSet or its metadata.
    public static List<?> toList(ResultSet resultSet) throws SQLException {

        // Create a List to store the Map of each row. Each Map will contain column names as keys and column values as values.
        List<Map<String, Object>> list = new ArrayList<>();

        // Get the metadata of the ResultSet, which contains information about the columns (like their names).
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        // Get the number of columns in the ResultSet (to iterate through them).
        final int columnCount = resultSetMetaData.getColumnCount();

        // Loop through each row in the ResultSet.
        while (resultSet.next()) {

            // Create an array to store the values of each column for the current row.
            Object[] values = new Object[columnCount];

            // Create a Map to store the column name as the key and its value for the current row.
            Map<String, Object> keyAndValue = new HashMap<>();

            // Loop through all columns for the current row and retrieve their values.
            // The column index starts at 1 (not 0), hence we start from 1 to columnCount.
            for (int i = 1; i <= columnCount; i++) {

                // Retrieve the value of the current column (column index i) and store it in the values array.
                values[i - 1] = resultSet.getObject(i);

                // Put the column name and its corresponding value into the Map.
                // We get the column name from the metadata (resultSetMetaData.getColumnName(i)) and use it as the key.
                keyAndValue.put(resultSetMetaData.getColumnName(i), values[i - 1]);
            }

            // Add the Map (representing a row) to the List.
            list.add(keyAndValue);
        }

        // Return the List containing all rows (as Maps) from the ResultSet.
        return list;
    }


    public static Customer objectToCustomer(Map<String, Object> map) {
        int id = (int) map.get("id");
        String firstName = (String) map.get("first_name");
        String lastName = (String) map.get("last_name");
        String email = (String) map.get("email");
        String password = (String) map.get("password");

        return new Customer(id, firstName, lastName, email, password,null);
    }

    public static Company objectToCompany(Map<String, Object> map) {
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        return new Company(id, name, email, password, null);
    }

    public static Coupon objectToCoupon(Map<String, Object> map) {
        int id = (int) map.get("id");
        int companyId = (int) map.get("company_id");
        Category category = Category.values()[(int) map.get("category_id")-1];
        String title = (String) map.get("title");
        String description = (String) map.get("description");
        Date startDate = (Date) map.get("start_date");
        Date endDate = (Date) map.get("end_date");
        int amount = (int) map.get("amount");
        double price = (double) map.get("price");
        String image = (String) map.get("image");

        return new Coupon(id, companyId, category, title, description, startDate, endDate, amount, price, image);

    }

    public static Category objectToCategory(Map<String, Object> map) {
        String name = (String) map.get("name");
        return Category.valueOf(name);
    }

    public static boolean objectToBoolean(Map<String, Object> map) {
        return ((long) map.get("result") == 1);
    }

    public static double objectToDouble(Map<String, Object> map) {
        return ((double) map.get("result"));
    }

    public static int objectToInt(Map<String, Object> map) {
        return (int) (map.get("id"));
    }
}