package dao;

import database.DatabaseConnection;
import entity.Invoice;
import entity.InvoiceDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public void createInvoice(Invoice invoice, List<InvoiceDetail> details) {
        Connection conn = null;
        PreparedStatement stmtInvoice = null;
        PreparedStatement stmtDetail = null;
        PreparedStatement stmtUpdateStock = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlInvoice = "INSERT INTO invoice (customer_id, total_amount, created_at) VALUES (?, ?, CURRENT_TIMESTAMP)";
            stmtInvoice = conn.prepareStatement(sqlInvoice, Statement.RETURN_GENERATED_KEYS);
            stmtInvoice.setInt(1, invoice.getCustomerId());
            stmtInvoice.setDouble(2, invoice.getTotalAmount());
            stmtInvoice.executeUpdate();

            ResultSet rs = stmtInvoice.getGeneratedKeys();
            int invoiceId = 0;
            if (rs.next()) {
                invoiceId = rs.getInt(1);
            }

            String sqlDetail = "INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
            stmtDetail = conn.prepareStatement(sqlDetail);

            String sqlStock = "UPDATE product SET stock = stock - ? WHERE id = ?";
            stmtUpdateStock = conn.prepareStatement(sqlStock);

            for (InvoiceDetail detail : details) {
                stmtDetail.setInt(1, invoiceId);
                stmtDetail.setInt(2, detail.getProductId());
                stmtDetail.setInt(3, detail.getQuantity());
                stmtDetail.setDouble(4, detail.getUnitPrice());
                stmtDetail.addBatch();

                stmtUpdateStock.setInt(1, detail.getQuantity());
                stmtUpdateStock.setInt(2, detail.getProductId());
                stmtUpdateStock.addBatch();
            }

            stmtDetail.executeBatch();
            stmtUpdateStock.executeBatch();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        String query = "SELECT i.id, i.customer_id, i.created_at, i.total_amount, c.name as customer_name " +
                "FROM invoice i JOIN customer c ON i.customer_id = c.id ORDER BY i.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Invoice inv = new Invoice();
                inv.setId(rs.getInt("id"));
                inv.setCustomerId(rs.getInt("customer_id"));
                inv.setCreatedAt(rs.getTimestamp("created_at"));
                inv.setTotalAmount(rs.getDouble("total_amount"));
                inv.setCustomerName(rs.getString("customer_name"));
                list.add(inv);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public double getRevenueByDate(int day, int month, int year) {
        String query = "SELECT SUM(total_amount) FROM invoice WHERE EXTRACT(DAY FROM created_at)=? AND EXTRACT(MONTH FROM created_at)=? AND EXTRACT(YEAR FROM created_at)=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, day);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double getRevenueByMonth(int month, int year) {
        String query = "SELECT SUM(total_amount) FROM invoice WHERE EXTRACT(MONTH FROM created_at)=? AND EXTRACT(YEAR FROM created_at)=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double getRevenueByYear(int year) {
        String query = "SELECT SUM(total_amount) FROM invoice WHERE EXTRACT(YEAR FROM created_at)=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}