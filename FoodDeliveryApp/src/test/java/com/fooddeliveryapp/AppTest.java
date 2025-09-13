// package com.fooddeliveryapp;

// import com.fooddeliveryapp.util.DBConnection;
// import org.junit.jupiter.api.Test;

// import java.sql.Connection;
// import java.sql.ResultSet;
// import java.sql.Statement;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// class AppTest {

// @Test
// void testDatabaseConnection() {
// try (Connection conn = DBConnection.getConnection()) {
// assertNotNull(conn, "Connection should not be null");

// Statement stmt = conn.createStatement();
// ResultSet rs = stmt.executeQuery("SELECT 1");

// assertTrue(rs.next(), "Query should return at least one row");
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
// }
