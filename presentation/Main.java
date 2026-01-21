package presentation;

import business.CustomerService;
import business.InvoiceService;
import business.ProductService;
import entity.Customer;
import entity.Invoice;
import entity.InvoiceDetail;
import entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // khai bao cac service va scanner
    private static ProductService productService = new ProductService();
    private static CustomerService customerService = new CustomerService();
    private static InvoiceService invoiceService = new InvoiceService();
    private static Scanner scanner = new Scanner(System.in);

    // main
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== QUAN LY CUA HANG DIEN THOAI ===");
            System.out.println("1. Quan ly san pham");
            System.out.println("2. Quan ly khach hang");
            System.out.println("3. Quan ly hoa don & Thong ke");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: productMenu(); break;
                    case 2: customerMenu(); break;
                    case 3: invoiceMenu(); break;
                    case 0:
                        System.out.println("Tam biet!");
                        System.exit(0);
                    default: System.out.println("Lua chon khong hop le!");
                }
            } catch (Exception e) {
                System.out.println("Vui long nhap so!");
            }
        }
    }

    // menu san pham
    private static void productMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- QUAN LY SAN PHAM ---");
            System.out.println("1. Danh sach san pham");
            System.out.println("2. Them san pham");
            System.out.println("3. Sua san pham");
            System.out.println("4. Xoa san pham");
            System.out.println("5. Tim kiem san pham");
            System.out.println("0. Quay lai");
            System.out.print("Moi chon: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: showAllProducts(); break;
                    case 2: addNewProduct(); break;
                    case 3: updateProduct(); break;
                    case 4: deleteProduct(); break;
                    case 5: searchProductMenu(); break;
                    case 0: back = true; break;
                    default: System.out.println("Sai chuc nang!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nhap sai dinh dang!");
            }
        }
    }

    // menu khach hang
    private static void customerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- QUAN LY KHACH HANG ---");
            System.out.println("1. Danh sach khach hang");
            System.out.println("2. Them khach hang");
            System.out.println("3. Sua khach hang");
            System.out.println("4. Xoa khach hang");
            System.out.println("0. Quay lai");
            System.out.print("Moi chon: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: showAllCustomers(); break;
                    case 2: addNewCustomer(); break;
                    case 3: updateCustomer(); break;
                    case 4: deleteCustomer(); break;
                    case 0: back = true; break;
                    default: System.out.println("Sai chuc nang!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nhap sai dinh dang!");
            }
        }
    }

    // menu hoa don
    private static void invoiceMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- QUAN LY HOA DON & THONG KE ---");
            System.out.println("1. Tao hoa don moi");
            System.out.println("2. Danh sach hoa don");
            System.out.println("3. Thong ke doanh thu");
            System.out.println("0. Quay lai");
            System.out.print("Moi chon: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: createNewInvoice(); break;
                    case 2: showAllInvoices(); break;
                    case 3: showRevenueStats(); break;
                    case 0: back = true; break;
                    default: System.out.println("Sai chuc nang!");
                }
            } catch (Exception e) {
                System.out.println("Loi nhap lieu!");
            }
        }
    }

    // cac ham xu ly san pham
    private static void searchProductMenu() {
        System.out.println("\n--- TIM KIEM SAN PHAM ---");
        System.out.println("1. Theo Hang (Brand)");
        System.out.println("2. Theo khoang Gia");
        System.out.println("3. Theo so luong Ton kho");
        System.out.print("Chon tieu chi: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            List<Product> result = null;

            switch (choice) {
                case 1:
                    System.out.print("Nhap ten hang: ");
                    String brand = scanner.nextLine();
                    result = productService.searchByBrand(brand);
                    break;
                case 2:
                    System.out.print("Gia thap nhat: ");
                    double minP = Double.parseDouble(scanner.nextLine());
                    System.out.print("Gia cao nhat: ");
                    double maxP = Double.parseDouble(scanner.nextLine());
                    result = productService.searchByPrice(minP, maxP);
                    break;
                case 3:
                    System.out.print("So luong min: ");
                    int minS = Integer.parseInt(scanner.nextLine());
                    System.out.print("So luong max: ");
                    int maxS = Integer.parseInt(scanner.nextLine());
                    result = productService.searchByStock(minS, maxS);
                    break;
                default:
                    System.out.println("Sai lua chon");
            }

            if (result != null && !result.isEmpty()) {
                System.out.println("--- KET QUA TIM KIEM ---");
                for (Product p : result) System.out.println(p);
            } else {
                System.out.println("Khong tim thay san pham nao!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Loi nhap lieu!");
        }
    }

    private static void showAllProducts() {
        List<Product> list = productService.getAllProducts();
        if (list.isEmpty()) System.out.println("Danh sach trong!");
        else for (Product p : list) System.out.println(p);
    }

    private static void addNewProduct() {
        System.out.print("Ten: "); String name = scanner.nextLine();
        System.out.print("Hang: "); String brand = scanner.nextLine();
        System.out.print("Gia: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Ton kho: "); int stock = Integer.parseInt(scanner.nextLine());
        productService.addProduct(name, brand, price, stock);
        System.out.println("Thanh cong!");
    }

    private static void updateProduct() {
        System.out.print("Nhap ID: "); int id = Integer.parseInt(scanner.nextLine());
        if (productService.findProductById(id) == null) {
            System.out.println("Khong tim thay!"); return;
        }
        System.out.print("Ten moi: "); String name = scanner.nextLine();
        System.out.print("Hang moi: "); String brand = scanner.nextLine();
        System.out.print("Gia moi: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Ton kho moi: "); int stock = Integer.parseInt(scanner.nextLine());
        productService.updateProduct(id, name, brand, price, stock);
        System.out.println("Cap nhat thanh cong!");
    }

    private static void deleteProduct() {
        System.out.print("Nhap ID: "); int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Xac nhan xoa (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            productService.deleteProduct(id);
            System.out.println("Da xoa!");
        }
    }

    // cac ham xu ly khach hang
    private static void showAllCustomers() {
        List<Customer> list = customerService.getAllCustomers();
        if (list.isEmpty()) System.out.println("Danh sach trong!");
        else for (Customer c : list) System.out.println(c);
    }

    private static void addNewCustomer() {
        System.out.print("Ten KH: "); String name = scanner.nextLine();
        System.out.print("Sdt: "); String phone = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Dia chi: "); String address = scanner.nextLine();
        customerService.addCustomer(name, phone, email, address);
        System.out.println("Them KH thanh cong!");
    }

    private static void updateCustomer() {
        System.out.print("Nhap ID KH: "); int id = Integer.parseInt(scanner.nextLine());
        if (customerService.getCustomerById(id) == null) {
            System.out.println("Khong tim thay!"); return;
        }
        System.out.print("Ten moi: "); String name = scanner.nextLine();
        System.out.print("Sdt moi: "); String phone = scanner.nextLine();
        System.out.print("Email moi: "); String email = scanner.nextLine();
        System.out.print("Dia chi moi: "); String address = scanner.nextLine();
        customerService.updateCustomer(id, name, phone, email, address);
        System.out.println("Cap nhat KH thanh cong!");
    }

    private static void deleteCustomer() {
        System.out.print("Nhap ID KH: "); int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Xac nhan xoa (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            customerService.deleteCustomer(id);
            System.out.println("Da xoa KH!");
        }
    }

    // cac ham xu ly hoa don
    private static void createNewInvoice() {
        System.out.print("Nhap ID Khach hang: ");
        int customerId = Integer.parseInt(scanner.nextLine());
        if (customerService.getCustomerById(customerId) == null) {
            System.out.println("Khach hang khong ton tai!");
            return;
        }

        List<InvoiceDetail> details = new ArrayList<>();
        boolean adding = true;

        while (adding) {
            showAllProducts();
            System.out.print("Nhap ID San pham mua (0 de ket thuc): ");
            int pId = Integer.parseInt(scanner.nextLine());
            if (pId == 0) break;

            Product p = productService.findProductById(pId);
            if (p == null) {
                System.out.println("San pham khong ton tai!");
                continue;
            }

            System.out.print("Nhap so luong mua: ");
            int qty = Integer.parseInt(scanner.nextLine());

            if (qty > p.getStock()) {
                System.out.println("Kho khong du! Con lai: " + p.getStock());
                continue;
            }

            details.add(new InvoiceDetail(pId, qty, p.getPrice()));
            System.out.print("Them tiep? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("n")) adding = false;
        }

        if (!details.isEmpty()) {
            boolean success = invoiceService.createInvoice(customerId, details);
            if (success) System.out.println("Tao hoa don thanh cong!");
            else System.out.println("Loi: Kiem tra lai ton kho!");
        } else {
            System.out.println("Huy tao hoa don (chua chon san pham).");
        }
    }

    private static void showAllInvoices() {
        List<Invoice> list = invoiceService.getAllInvoices();
        if (list.isEmpty()) System.out.println("Chua co hoa don nao.");
        else for (Invoice i : list) System.out.println(i);
    }

    private static void showRevenueStats() {
        System.out.println("1. Theo Ngay");
        System.out.println("2. Theo Thang");
        System.out.println("3. Theo Nam");
        System.out.print("Chon: ");
        int type = Integer.parseInt(scanner.nextLine());

        double revenue = 0;
        switch (type) {
            case 1:
                System.out.print("Nhap Ngay (dd): "); int d = Integer.parseInt(scanner.nextLine());
                System.out.print("Nhap Thang (mm): "); int m = Integer.parseInt(scanner.nextLine());
                System.out.print("Nhap Nam (yyyy): "); int y = Integer.parseInt(scanner.nextLine());
                revenue = invoiceService.getRevenueByDate(d, m, y);
                break;
            case 2:
                System.out.print("Nhap Thang (mm): "); int mm = Integer.parseInt(scanner.nextLine());
                System.out.print("Nhap Nam (yyyy): "); int yy = Integer.parseInt(scanner.nextLine());
                revenue = invoiceService.getRevenueByMonth(mm, yy);
                break;
            case 3:
                System.out.print("Nhap Nam (yyyy): "); int yyy = Integer.parseInt(scanner.nextLine());
                revenue = invoiceService.getRevenueByYear(yyy);
                break;
        }
        System.out.printf("Tong doanh thu: %.2f VND\n", revenue);
    }
}