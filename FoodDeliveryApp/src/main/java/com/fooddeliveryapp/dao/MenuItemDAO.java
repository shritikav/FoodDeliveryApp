package com.fooddeliveryapp.dao;

import com.fooddeliveryapp.model.MenuItem;
import com.fooddeliveryapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public void create(MenuItem item) throws SQLException {
        String sql = "INSERT INTO menu_item (restaurant_id, name, price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, item.getRestaurantId());
            ps.setString(2, item.getName());
            ps.setDouble(3, item.getPrice());
            ps.executeUpdate();
        }
    }

    // ✅ Add this method to get menu items by restaurant
    public List<MenuItem> getByRestaurant(long restaurantId) throws SQLException {
        String sql = "SELECT * FROM menu_item WHERE restaurant_id = ?";
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, restaurantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getLong("id"));
                item.setRestaurantId(rs.getLong("restaurant_id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                menuItems.add(item);
            }
        }
        return menuItems;
    }

    public List<MenuItem> getAll() throws SQLException {
        String sql = "SELECT * FROM menu_item";
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getLong("id"));
                item.setRestaurantId(rs.getLong("restaurant_id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                menuItems.add(item);
            }
        }
        return menuItems;
    }
}
