package com.fooddeliveryapp.dao;

import com.fooddeliveryapp.model.Orders;
import com.fooddeliveryapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {

    public void create(Orders o) throws SQLException {
        String sql = "INSERT INTO orders (user_id, total, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, o.getUserId());
            ps.setDouble(2, o.getTotal());
            ps.setString(3, o.getStatus());
            ps.executeUpdate();
        }
    }

    public Orders getById(long id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Orders o = new Orders();
                o.setId(rs.getLong("id"));
                o.setUserId(rs.getLong("user_id"));
                o.setTotal(rs.getDouble("total"));
                o.setStatus(rs.getString("status"));
                return o;
            }
            return null;
        }
    }

    public List<Orders> getAll() throws SQLException {
        String sql = "SELECT * FROM orders";
        List<Orders> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Orders o = new Orders();
                o.setId(rs.getLong("id"));
                o.setUserId(rs.getLong("user_id"));
                o.setTotal(rs.getDouble("total"));
                o.setStatus(rs.getString("status"));
                list.add(o);
            }
        }
        return list;
    }

    public void update(Orders o) throws SQLException {
        String sql = "UPDATE orders SET user_id=?, total=?, status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, o.getUserId());
            ps.setDouble(2, o.getTotal());
            ps.setString(3, o.getStatus());
            ps.setLong(4, o.getId());
            ps.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
