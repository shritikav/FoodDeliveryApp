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

    public MenuItem getById(long id) throws SQLException {
        String sql = "SELECT * FROM menu_item WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getLong("id"));
                item.setRestaurantId(rs.getLong("restaurant_id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                return item;
            }
            return null;
        }
    }

    public List<MenuItem> getAll() throws SQLException {
        String sql = "SELECT * FROM menu_item";
        List<MenuItem> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getLong("id"));
                item.setRestaurantId(rs.getLong("restaurant_id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                list.add(item);
            }
        }
        return list;
    }

    public void update(MenuItem item) throws SQLException {
        String sql = "UPDATE menu_item SET restaurant_id=?, name=?, price=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, item.getRestaurantId());
            ps.setString(2, item.getName());
            ps.setDouble(3, item.getPrice());
            ps.setLong(4, item.getId());
            ps.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM menu_item WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
