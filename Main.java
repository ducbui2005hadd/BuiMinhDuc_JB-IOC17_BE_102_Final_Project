import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("=== GIAO DIEN BAT DAU CHUONG TRINH ===");
            System.out.println("1. Quan ly dien thoai");
            System.out.println("0. Thoat");
            System.out.print("Nhap lua chon cua ban: ");

            int choice = getValidInput();

            switch (choice) {
                case 1:
                    showPhoneMenu();
                    break;
                case 0:
                    System.out.println("Ket thuc chuong trinh.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long nhap lai.");
            }
        }
    }

    private static void showPhoneMenu() {
        while (true) {
            System.out.println("\n=== GIAO DIEN QUAN LY DIEN THOAI ===");
            System.out.println("1. Them moi san pham");
            System.out.println("0. Quay ve menu chinh");
            System.out.print("Nhap lua chon cua ban: ");

            int choice = getValidInput();

            switch (choice) {
                case 1:
                    addNewPhone();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long nhap lai.");
            }
        }
    }

    private static void addNewPhone() {
        System.out.println("\n--- Them Moi San Pham ---");

        System.out.print("Nhap ten dien thoai: ");
        String name = scanner.nextLine();

        System.out.print("Nhap hang san xuat: ");
        String brand = scanner.nextLine();

        double price = 0;
        while (true) {
            try {
                System.out.print("Nhap gia ban: ");
                price = Double.parseDouble(scanner.nextLine());
                if (price > 0) break;
                System.out.println("Gia phai lon hon 0.");
            } catch (NumberFormatException e) {
                System.out.println("Gia tien phai la so. Vui long nhap lai.");
            }
        }

        int quantity = 0;
        while (true) {
            try {
                System.out.print("Nhap so luong: ");
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity >= 0) break;
                System.out.println("So luong phai >= 0.");
            } catch (NumberFormatException e) {
                System.out.println("So luong phai la so nguyen. Vui long nhap lai.");
            }
        }

        Phone newPhone = new Phone(name, brand, price, quantity);
        savePhoneToDb(newPhone);
    }

    private static void savePhoneToDb(Phone phone) {
        String sql = "INSERT INTO phones (name, brand, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn != null) {
                pstmt.setString(1, phone.getName());
                pstmt.setString(2, phone.getBrand());
                pstmt.setDouble(3, phone.getPrice());
                pstmt.setInt(4, phone.getQuantity());

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Them san pham thanh cong!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi khi luu vao CSDL: " + e.getMessage());
        }
    }

    private static int getValidInput() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
        return choice;
    }
}