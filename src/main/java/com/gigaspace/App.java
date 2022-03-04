package com.gigaspace;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/gigatest";
    public static final String USER_NAME = "postgres";
    public static final String PASSWORD = "password";
    public static Logger log = Logger.getLogger(App.class.getName());

    public static void main( String[] args ) {
        log.info("Start");
        Connection con = null;
        PreparedStatement stmt = null;
        Statement queryStmt = null;
        ResultSet rs = null;

        try {
            App app = new App();
            con = app.getConnection();

            log.info("Inserting new row.");
            String sql = "INSERT INTO foo (entrynumber, mymessage) VALUES (?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, 2);
            stmt.setString(2, "Hello GigaSpaces");
            stmt.executeUpdate();

            log.info("Fetching all rows");
            String sqlQuery = "select * from foo";
            queryStmt = con.createStatement();
            rs = queryStmt.executeQuery(sqlQuery);

            System.out.println("----------------------");
            while (rs != null && rs.next()) {
                System.out.println("Entry Number: " + rs.getInt(1) + ", Message: " + rs.getString(2));
            }
            System.out.println("----------------------");

            log.info("Exiting.");
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Exception while processing", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.log(Level.SEVERE, "Exception while closing rs", e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.log(Level.SEVERE, "Exception while closing stmt", e);
                }
            }
            if (queryStmt != null) {
                try {
                    queryStmt.close();
                } catch (SQLException e) {
                    log.log(Level.SEVERE, "Exception while closing queryStmt", e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.log(Level.SEVERE, "Exception while closing con", e);
                }
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
    }
}
