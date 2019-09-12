package jdbc;

import atm.Customer;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JdbcSQLiteConnection {

    String databaseName;

    public JdbcSQLiteConnection (String databaseName) {
        this.databaseName = databaseName;
    }

    public Map<Integer, Customer> readCustomers() throws IOException {
        try {
            //set up
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:"+this.databaseName;
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("Connected to the database");

                //display database information
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Product name: " + dm.getDatabaseProductName());

                //execute SQL statements
                System.out.println("---- Data in deposite table ----");

                String query = "SELECT * FROM customers";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                Map<Integer, Customer> customers = new HashMap<Integer, Customer>();

                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    int pin = resultSet.getInt(2);
                    int amount = resultSet.getInt(3);
                    Customer tempCustomer = new Customer(id, pin, amount);
                    customers.put(tempCustomer.getCustomerNumber(), tempCustomer);
                }

                return customers;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
