package business;

import dao.ProductDAO;
import entity.Product;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public List<Product> getAllProducts() { return productDAO.getAllProducts(); }
    public void addProduct(String n, String b, double p, int s) { productDAO.addProduct(new Product(n, b, p, s)); }
    public void updateProduct(int id, String n, String b, double p, int s) { productDAO.updateProduct(new Product(id, n, b, p, s)); }
    public void deleteProduct(int id) { productDAO.deleteProduct(id); }
    public Product findProductById(int id) { return productDAO.getProductById(id); }
    public List<Product> searchByBrand(String brand) { return productDAO.searchByBrand(brand); }
    public List<Product> searchByPrice(double min, double max) { return productDAO.searchByPriceRange(min, max); }
    public List<Product> searchByStock(int min, int max) { return productDAO.searchByStock(min, max); }
}