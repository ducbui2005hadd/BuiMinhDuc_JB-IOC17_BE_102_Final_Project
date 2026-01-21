package dao;

import database.DatabaseConnection;
import entity.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"), rs.getString("name"), rs.getString("brand"),
                        rs.getDouble("price"), rs.getInt("stock")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return products;
    }

    public void addProduct(Product p) {
        String query = "INSERT INTO product (name, brand, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getBrand());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getStock());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateProduct(Product p) {
        String query = "UPDATE product SET name=?, brand=?, price=?, stock=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getBrand());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getStock());
            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteProduct(int id) {
        String query = "DELETE FROM product WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Product getProductById(int id) {
        String query = "SELECT * FROM product WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id"), rs.getString("name"), rs.getString("brand"),
                        rs.getDouble("price"), rs.getInt("stock")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Product> searchByBrand(String brandKeyword) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM product WHERE brand ILIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + brandKeyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"), rs.getString("name"), rs.getString("brand"),
                        rs.getDouble("price"), rs.getInt("stock")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM product WHERE price BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, min);
            stmt.setDouble(2, max);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"), rs.getString("name"), rs.getString("brand"),
                        rs.getDouble("price"), rs.getInt("stock")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Product> searchByStock(int minStock, int maxStock) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM product WHERE stock BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, minStock);
            stmt.setInt(2, maxStock);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"), rs.getString("name"), rs.getString("brand"),
                        rs.getDouble("price"), rs.getInt("stock")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}