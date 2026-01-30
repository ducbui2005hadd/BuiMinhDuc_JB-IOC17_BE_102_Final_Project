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
        System.out.println("\n--- tim kiem san pham ---");
        System.out.println("1. theo hang (brand)");
        System.out.println("2. theo khoang gia");
        System.out.println("3. theo so luong ton kho");
        System.out.print("chon tieu chi: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            List<Product> result = null;

            switch (choice) {
                case 1:
                    System.out.print("nhap ten hang: ");
                    String brand = scanner.nextLine();
                    result = productService.searchByBrand(brand);
                    break;
                case 2:
                    // Validate gia thap nhat
                    double minP;
                    while (true) {
                        System.out.print("gia thap nhat: ");
                        try {
                            minP = Double.parseDouble(scanner.nextLine());
                            if (minP >= 0) break;
                            System.out.println("loi: gia khong duoc am!");
                        } catch (NumberFormatException e) {
                            System.out.println("loi: vui long nhap so!");
                        }
                    }

                    // Validate gia cao nhat
                    double maxP;
                    while (true) {
                        System.out.print("gia cao nhat: ");
                        try {
                            maxP = Double.parseDouble(scanner.nextLine());
                            if (maxP >= 0) {
                                if (maxP >= minP) break; // Kiem tra them logic max > min
                                else System.out.println("loi: gia cao nhat phai lon hon gia thap nhat!");
                            } else {
                                System.out.println("loi: gia khong duoc am!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("loi: vui long nhap so!");
                        }
                    }

                    result = productService.searchByPrice(minP, maxP);
                    break;
                case 3:
                    // Validate ton kho min
                    int minS;
                    while (true) {
                        System.out.print("so luong min: ");
                        try {
                            minS = Integer.parseInt(scanner.nextLine());
                            if (minS >= 0) break;
                            System.out.println("loi: so luong khong duoc am!");
                        } catch (NumberFormatException e) {
                            System.out.println("loi: vui long nhap so nguyen!");
                        }
                    }

                    // Validate ton kho max
                    int maxS;
                    while (true) {
                        System.out.print("so luong max: ");
                        try {
                            maxS = Integer.parseInt(scanner.nextLine());
                            if (maxS >= 0) {
                                if (maxS >= minS) break;
                                else System.out.println("loi: max phai lon hon min!");
                            } else {
                                System.out.println("loi: so luong khong duoc am!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("loi: vui long nhap so nguyen!");
                        }
                    }

                    result = productService.searchByStock(minS, maxS);
                    break;
                default:
                    System.out.println("sai lua chon");
            }

            if (result != null && !result.isEmpty()) {
                System.out.println("--- ket qua tim kiem ---");
                for (Product p : result) System.out.println(p);
            } else {
                System.out.println("khong tim thay san pham nao!");
            }
        } catch (NumberFormatException e) {
            System.out.println("loi nhap lieu!");
        }
    }

    private static void showAllProducts() {
        List<Product> list = productService.getAllProducts();
        if (list.isEmpty()) System.out.println("Danh sach trong!");
        else for (Product p : list) System.out.println(p);
    }

    private static void addNewProduct() {
        System.out.print("ten: ");
        String name = scanner.nextLine();

        System.out.print("hang: ");
        String brand = scanner.nextLine();

        // validate gia (phai lon hon 0)
        double price;
        while (true) {
            System.out.print("gia: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price > 0) {
                    break; // gia hop le -> thoat vong lap
                }
                System.out.println("loi: gia phai lon hon 0!");
            } catch (NumberFormatException e) {
                System.out.println("loi: vui long nhap so!");
            }
        }

        // validate ton kho (phai >= 0)
        int stock;
        while (true) {
            System.out.print("ton kho: ");
            try {
                stock = Integer.parseInt(scanner.nextLine());
                if (stock >= 0) {
                    break; // ton kho hop le -> thoat vong lap
                }
                System.out.println("loi: ton kho khong duoc am!");
            } catch (NumberFormatException e) {
                System.out.println("loi: vui long nhap so nguyen!");
            }
        }

        productService.addProduct(name, brand, price, stock);
        System.out.println("thanh cong!");
    }

    private static void updateProduct() {
        System.out.print("nhap id: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (productService.findProductById(id) == null) {
            System.out.println("khong tim thay!");
            return;
        }

        System.out.print("ten moi: ");
        String name = scanner.nextLine();

        System.out.print("hang moi: ");
        String brand = scanner.nextLine();

        // validate gia moi
        double price;
        while (true) {
            System.out.print("gia moi: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price > 0) break;
                System.out.println("loi: gia phai lon hon 0!");
            } catch (NumberFormatException e) {
                System.out.println("loi: nhap sai dinh dang!");
            }
        }

        // validate ton kho moi
        int stock;
        while (true) {
            System.out.print("ton kho moi: ");
            try {
                stock = Integer.parseInt(scanner.nextLine());
                if (stock >= 0) break;
                System.out.println("loi: ton kho khong duoc am!");
            } catch (NumberFormatException e) {
                System.out.println("loi: nhap sai dinh dang!");
            }
        }

        productService.updateProduct(id, name, brand, price, stock);
        System.out.println("cap nhat thanh cong!");
    }

    private static void deleteProduct() {
        System.out.print("nhap id san pham muon xoa: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("loi: id phai la so!");
            return;
        }

        // 1. KIEM TRA TON TAI
        Product p = productService.findProductById(id);
        if (p == null) {
            System.out.println("loi: san pham id " + id + " khong ton tai!");
            return;
        }

        // 2. HIEN THONG TIN & HOI XAC NHAN
        System.out.println("thong tin san pham se xoa:");
        System.out.println("- Ten: " + p.getName());
        System.out.println("- Hang: " + p.getBrand());
        System.out.printf("- Gia: %,.0f VND\n", p.getPrice()); // Format gia tien cho dep

        System.out.print("ban co chac chan muon xoa vinh vien khong? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            // 3. THUC HIEN XOA
            boolean result = productService.deleteProduct(id);
            if (result) {
                System.out.println("da xoa san pham thanh cong!");
            } else {
                // Truong hop nay thuong gap nhat la do san pham da duoc ban trong Hoa don
                System.out.println("loi: khong xoa duoc! (co the san pham nay da nam trong hoa don ban hang).");
            }
        } else {
            System.out.println("da huy thao tac xoa.");
        }
    }

    // cac ham xu ly khach hang
    private static void showAllCustomers() {
        List<Customer> list = customerService.getAllCustomers();
        if (list.isEmpty()) System.out.println("Danh sach trong!");
        else for (Customer c : list) System.out.println(c);
    }

    private static void addNewCustomer() {
        System.out.print("ten kh: ");
        String name = scanner.nextLine();

        // validate so dien thoai (10 so, bat dau bang 0)
        String phone;
        while (true) {
            System.out.print("sdt: ");
            phone = scanner.nextLine();
            // regex: bat dau bang 0, theo sau la 9 chu so bat ky
            if (phone.matches("^0[0-9]{9}$")) {
                break;
            }
            System.out.println("loi: sdt phai co 10 so va bat dau bang 0!");
        }

        // validate email (chuan format quoc te)
        String email;
        while (true) {
            System.out.print("email: ");
            email = scanner.nextLine();
            // regex check email co ban
            if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                break;
            }
            System.out.println("loi: email khong dung dinh dang (vidu: abc@gmail.com)!");
        }

        System.out.print("dia chi: ");
        String address = scanner.nextLine();

        customerService.addCustomer(name, phone, email, address);
        System.out.println("them kh thanh cong!");
    }

    private static void updateCustomer() {
        System.out.print("nhap id kh: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("loi: id phai la so!");
            return;
        }

        if (customerService.getCustomerById(id) == null) {
            System.out.println("khong tim thay!");
            return;
        }

        System.out.print("ten moi: ");
        String name = scanner.nextLine();

        // validate sdt moi
        String phone;
        while (true) {
            System.out.print("sdt moi: ");
            phone = scanner.nextLine();
            if (phone.matches("^0[0-9]{9}$")) {
                break;
            }
            System.out.println("loi: sdt phai co 10 so va bat dau bang 0!");
        }

        // validate email moi
        String email;
        while (true) {
            System.out.print("email moi: ");
            email = scanner.nextLine();
            if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                break;
            }
            System.out.println("loi: email khong dung dinh dang!");
        }

        System.out.print("dia chi moi: ");
        String address = scanner.nextLine();

        customerService.updateCustomer(id, name, phone, email, address);
        System.out.println("cap nhat kh thanh cong!");
    }

    private static void deleteCustomer() {
        System.out.print("nhap id khach hang muon xoa: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("loi: id phai la so!");
            return;
        }

        // BUOC 1: KIEM TRA TON TAI TRUOC
        // Goi service de lay thong tin khach hang
        Customer c = customerService.getCustomerById(id);

        if (c == null) {
            // Neu khong tim thay -> Bao loi ngay
            System.out.println("loi: khach hang co id " + id + " khong ton tai!");
            return; // Thoat khoi ham, khong hoi xac nhan nua
        }

        // BUOC 2: HOI XAC NHAN
        // Hien thi ten de nguoi dung biet minh dang xoa ai
        System.out.println("tim thay khach hang: " + c.getName() + " (Sdt: " + c.getPhone() + ")");
        System.out.print("ban co chac chan muon xoa khong? (y/n): ");

        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            // BUOC 3: THUC HIEN XOA
            boolean result = customerService.deleteCustomer(id);
            if (result) {
                System.out.println("da xoa khach hang thanh cong!");
            } else {
                // Truong hop hy huu: vua check xong thi o may khac da xoa mat roi
                System.out.println("loi: khong xoa duoc (co the da bi xoa boi nguoi khac).");
            }
        } else {
            System.out.println("da huy thao tac xoa.");
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