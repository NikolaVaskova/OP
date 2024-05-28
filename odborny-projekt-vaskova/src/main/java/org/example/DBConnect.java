package org.example;

import org.example.logic.ChecklistItem;

import java.sql.*;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
public class DBConnect {
    private static String url;
    private Connection connection;

    public DBConnect() {
        connection = connect();
    }

    public Connection connect() {
        url = "jdbc:sqlite::resource:CompanyProgram.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return conn;
        }
    }

    public void executeSQL(String sql) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ChecklistItem> getChecklistItems() {
        String sql = "SELECT id, label FROM ChecklistItems";
        List<ChecklistItem> items = new ArrayList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String label = rs.getString("label");
                items.add(new ChecklistItem(id, label));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    public void addUser(String name, String email, String password) throws SQLException {
        String sql = "INSERT INTO Users(name, email, password) VALUES(?,?,?)";

        try (
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Number of rows affected: " + affectedRows);

            // Verify insertion
            String verifySql = "SELECT * FROM Users";
            try (PreparedStatement verifyPstmt = connection.prepareStatement(verifySql)) {

                ResultSet rs = verifyPstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Inserted Users - ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
                }
            }
        }
    }

    public List<ChecklistItem> getItemsFromTable(String tableName) {
        String sql = "SELECT id, Item FROM " + tableName;
        List<ChecklistItem> items = new ArrayList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String label = rs.getString("Item");
                items.add(new ChecklistItem(id, label));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    public void addItemInTable(String lastUsedTable, String itemToAdd) {
        String sql = "INSERT INTO " + lastUsedTable + "(Item) VALUES('" + itemToAdd + "')";
        executeSQL(sql);
    }

    public void removeItemById(String lastUsedTable, int id) throws SQLException {
        String sql = "DELETE FROM " + lastUsedTable + " WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public String findPasswordFromUsername(String name) {
        return name;
    }
}