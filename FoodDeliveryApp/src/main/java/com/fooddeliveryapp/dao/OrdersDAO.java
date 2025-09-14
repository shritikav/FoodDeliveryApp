package com.fooddeliveryapp.dao;

// OrdersDAO.java
import com.fooddeliveryapp.model.Orders;
import com.fooddeliveryapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {

    public void create(Orders order) throws SQLException {
        String sql = "INSERT INTO orders(user_id, total, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, order.getUserId());
            ps.setDouble(2, order.getTotal());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getLong(1));
            }
        }
    }

    public List<Orders> getByUser(long userId) throws SQLException {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

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

    public List<Orders> getAll() throws SQLException {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
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
        }
        return null;
    }

    public void update(Orders order) throws SQLException {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, order.getStatus());
            ps.setLong(2, order.getId());
            ps.executeUpdate();
        }
    }

    public void updateStatus(long oid, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStatus'");
    }
}
