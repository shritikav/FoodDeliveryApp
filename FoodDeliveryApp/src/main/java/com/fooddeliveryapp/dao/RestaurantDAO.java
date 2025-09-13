package com.fooddeliveryapp.dao;

import com.fooddeliveryapp.model.Restaurant;
import com.fooddeliveryapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

    public void create(Restaurant r) throws SQLException {
        String sql = "INSERT INTO restaurant (name, address) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getAddress());
            ps.executeUpdate();
        }
    }

    public Restaurant getById(long id) throws SQLException {
        String sql = "SELECT * FROM restaurant WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Restaurant r = new Restaurant();
                r.setId(rs.getLong("id"));
                r.setName(rs.getString("name"));
                r.setAddress(rs.getString("address"));
                return r;
            }
            return null;
        }
    }

    public List<Restaurant> getAll() throws SQLException {
        String sql = "SELECT * FROM restaurant";
        List<Restaurant> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Restaurant r = new Restaurant();
                r.setId(rs.getLong("id"));
                r.setName(rs.getString("name"));
                r.setAddress(rs.getString("address"));
                list.add(r);
            }
        }
        return list;
    }

    public void update(Restaurant r) throws SQLException {
        String sql = "UPDATE restaurant SET name=?, address=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getAddress());
            ps.setLong(3, r.getId());
            ps.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM restaurant WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
