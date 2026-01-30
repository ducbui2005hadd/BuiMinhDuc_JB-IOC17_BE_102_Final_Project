package business;

import dao.InvoiceDAO;
import dao.ProductDAO;
import entity.Invoice;
import entity.InvoiceDetail;
import entity.Product;
import java.util.List;

public class InvoiceService {
    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private ProductDAO productDAO = new ProductDAO();

    public boolean createInvoice(int customerId, List<InvoiceDetail> details) {
        double total = 0;
        for (InvoiceDetail detail : details) {
            Product p = productDAO.getProductById(detail.getProductId());
            if (p == null || p.getStock() < detail.getQuantity()) {
                return false;
            }
            detail.setUnitPrice(p.getPrice());
            total += p.getPrice() * detail.getQuantity();
        }
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setTotalAmount(total);
        invoiceDAO.createInvoice(invoice, details);
        return true;
    }

    public List<Invoice> getAllInvoices() { return invoiceDAO.getAllInvoices(); }
    public double getRevenueByDate(int d, int m, int y) { return invoiceDAO.getRevenueByDate(d, m, y); }
    public double getRevenueByMonth(int m, int y) { return invoiceDAO.getRevenueByMonth(m, y); }
    public double getRevenueByYear(int y) { return invoiceDAO.getRevenueByYear(y); }
}