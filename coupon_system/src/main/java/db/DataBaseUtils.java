package db;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.dbdao.CompanyDBDAO;
import dao.dbdao.CustomerDBDAO;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for performing database operations.
 * This class provides methods for executing SQL queries with and without parameters,
 * and for retrieving results from queries.
 */
public final class DataBaseUtils {

    //This class running queries on the database with and without parameters - it's can send queries
    //and can get something from the database

        public static void runQuery(String sql) throws SQLException {
            //Step 1 - getConnection from Connection pool
            Connection connection = ConnectionPool.getInstance().getConnection();
            //Step 2 - Prepare Statement & Execute
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            //Step 3 - releaseConnection to Connection pool
            ConnectionPool.getInstance().releaseConnection(connection);
        }

        public static void runQuery(String sql, Map<Integer, Object> map) throws SQLException {
            //Step 1 - getConnection from Connection pool
            Connection connection = ConnectionPool.getInstance().getConnection();
            //Step 2 - Prepare Statement & Execute
            PreparedStatement statement = connection.prepareStatement(sql);
            statement = supportParams(statement, map);
            statement.execute();
            //Step 3 - releaseConnection to Connection pool
            ConnectionPool.getInstance().releaseConnection(connection);

        }

        public static List<?> runQueryWithResult(String sql) throws SQLException {
            List<?> rows = null;
            //Step 1 - getConnection from Connection pool
            Connection connection = ConnectionPool.getInstance().getConnection();
            //Step 2 - Prepare Statement & Execute
            PreparedStatement statement = connection.prepareStatement(sql);

            //Step 3 - Support ResultSet
            ResultSet resultSet = statement.executeQuery();
            rows = ConvertUtils.toList(resultSet);
            //Step 4 - releaseConnection to Connection pool & Close resources
            resultSet.close();
            ConnectionPool.getInstance().releaseConnection(connection);

            return rows;
        }

        public static List<?> runQueryWithResult(String sql, Map<Integer, Object> params) throws SQLException {

            List<?> rows = null;
            //Step 1 - getConnection from Connection pool
            Connection connection = ConnectionPool.getInstance().getConnection();
            //Step 2 - Prepare Statement & Execute
            PreparedStatement statement = connection.prepareStatement(sql);
            statement = supportParams(statement, params);

            //Step 3 - Support ResultSet
            ResultSet resultSet = statement.executeQuery();
            rows = ConvertUtils.toList(resultSet);
            //Step 4 - releaseConnection to Connection pool & Close resources
            resultSet.close();
            ConnectionPool.getInstance().releaseConnection(connection);

            return rows;
        }

// This method is used to support setting parameters of various types (Integer, String, Double, Boolean, Date)
// into a PreparedStatement. It takes a PreparedStatement and a Map of parameters, where the key is the parameter index
// and the value is the parameter to be set in the statement.
    private static PreparedStatement supportParams(PreparedStatement statement, Map<Integer, Object> params) throws SQLException {

        // Iterate over each entry in the Map. The Map contains the index (key) and the value to be set at that index in the PreparedStatement.
        for (Map.Entry<Integer, Object> entry : params.entrySet()) {

            // Get the key (parameter index) and value (parameter value) from the current entry.
            int key = entry.getKey();
            Object value = entry.getValue();

            // Check if the value is of type Integer. If so, set the parameter at the given index (key) to the integer value.
            if (value instanceof Integer) {
                statement.setInt(key, (Integer) value);  // Set the integer value at the specified index.
                continue; // Skip to the next iteration after setting the parameter.

                // Check if the value is of type String. If so, set the parameter at the given index (key) to the string value.
            } else if (value instanceof String) {
                statement.setString(key, (String) value);  // Set the string value at the specified index.
                continue; // Skip to the next iteration after setting the parameter.

                // Check if the value is of type Double. If so, set the parameter at the given index (key) to the double value.
            } else if (value instanceof Double) {
                statement.setDouble(key, (Double) value);  // Set the double value at the specified index.
                continue; // Skip to the next iteration after setting the parameter.

                // Check if the value is of type Boolean. If so, set the parameter at the given index (key) to the boolean value.
            } else if (value instanceof Boolean) {
                statement.setBoolean(key, (Boolean) value);  // Set the boolean value at the specified index.
                continue; // Skip to the next iteration after setting the parameter.

                // Check if the value is of type Date. If so, set the parameter at the given index (key) to the date value.
            } else if (value instanceof Date) {
                statement.setDate(key, (Date) value);  // Set the Date value at the specified index.
            }
        }

        // Return the prepared statement with all the parameters set.
        return statement;
    }


}
