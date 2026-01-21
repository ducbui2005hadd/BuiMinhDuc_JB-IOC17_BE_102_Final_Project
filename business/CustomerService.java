package business;

import dao.CustomerDAO;
import entity.Customer;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public List<Customer> getAllCustomers() { return customerDAO.getAllCustomers(); }
    public void addCustomer(String n, String p, String e, String a) { customerDAO.addCustomer(new Customer(n, p, e, a)); }
    public void updateCustomer(int id, String n, String p, String e, String a) { customerDAO.updateCustomer(new Customer(id, n, p, e, a)); }
    public void deleteCustomer(int id) { customerDAO.deleteCustomer(id); }
    public Customer getCustomerById(int id) { return customerDAO.getCustomerById(id); }
}