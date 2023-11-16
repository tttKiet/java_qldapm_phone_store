package app;

import java.util.Scanner;
import java.sql.*;

import common.Category;
import common.Product;
import common.User;
import sql.ConnectionManager;

public class App {
    static int userIdLogin;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        conn = ConnectionManager.getConnection();

        try {
            System.out.println();
            System.out.println("----------------------Chao mung ban den voi phone store!----------------");
            int chosse = 0;
            do {
                switch (chosse) {
                    case 1:
                        handleLogin(conn);
                        showMenu();
                        break;
                    case 2:
                        handleRegister(conn);
                        showMenu();

                        break;
                    default:
                        showMenu();
                        break;
                }
                chosse = sc.nextInt();
            } while (chosse != 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sc.close();
        }
    }

    public static void handleLogin(Connection conn) {
        CallableStatement cStmt = null;
        Scanner sc = new Scanner(System.in);
        // kiem tra
        System.out.println("----------------------Dang nhap vao tai khoan----------------");
        System.out.print("Nhap so dien thoai: ");
        String phone = sc.nextLine();

        System.out.print("Mat khau: ");
        String password = sc.nextLine();

        try {

            cStmt = conn.prepareCall("{? = call isAuth(?, ?)}");
            cStmt.setString(2, phone);
            cStmt.setString(3, password);
            cStmt.registerOutParameter(1, Types.INTEGER);
            cStmt.execute();
            int balance = cStmt.getInt(1);
            if (balance != -1) {
                if (balance == 1) {
                    System.out.println();
                    System.out.println("Xin chao admin id = " + balance);
                    homeAdminPage(conn);
                } else {
                    System.out.println();
                    userIdLogin = balance;
                    System.out.println("Thanh cong. Ban da dang nhap id nguoi dung " + balance);
                    homePage(conn);
                }
                // giao dien trang chu
            } else {
                System.out.println("So dien thoai hoac mat khau khong chinh xac!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void handleRegister(Connection conn) {
        Scanner sc = new Scanner(System.in);
        System.out.println("------------------- Nhap thong tin --------------------");
        System.out.print("Ho va ten: ");
        String fullName = sc.nextLine();

        System.out.print("Mat khau: ");
        String password = sc.nextLine();

        System.out.print("So dien thoai: ");
        String phone = sc.nextLine();

        System.out.print("Dia chi: ");
        String address = sc.nextLine();

        User user = new User(fullName, phone, address, password);
        System.out.println(user.toString());
        try {

            PreparedStatement pStmt = null;

            pStmt = conn.prepareStatement("insert into user(fullName, password, address, phone) values(?,?,?,?);");
            pStmt.setString(1, user.getFullName());
            pStmt.setString(2, user.getPassword());
            pStmt.setString(3, user.getAddress());
            pStmt.setString(4, user.getPhone());
            pStmt.executeUpdate();

            System.out.println("Dang ky thanh cong!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void showMenu() {
        System.out.println("1. Dang nhap");
        System.out.println("2. Dang ky");
        System.out.println("0. Thoat");
    }

    public static void homeAdminPage(Connection conn) {
        System.out.println("----------------------Trang admin--------------------");
        Scanner sc = new Scanner(System.in);
        int chosse = 0;
        do {
            switch (chosse) {
                case 1:
                    showMenuAdmin();
                    handleViewProduct(conn);
                    showMenuAdmin();
                    break;
                case 2:
                    showMenuAdmin();
                    handleCreateProduct(conn);
                    showMenuAdmin();

                    break;

                case 3:
                    showMenuAdmin();
                    handleDeleteProduct(conn);
                    showMenuAdmin();

                    break;
                case 4:
                    showMenuAdmin();
                    handleViewCategory(conn);
                    showMenuAdmin();
                    break;

                case 5:
                    handleCreateCategory(conn);
                    showMenuAdmin();
                    break;

                case 6:
                    showMenuAdmin();
                    handleViewOrder(conn, 0);
                    showMenuAdmin();
                    break;
                default:
                    showMenuAdmin();
                    break;
            }
            System.out.print("Nhap lua chon cua ban: ");
            chosse = sc.nextInt();
        } while (chosse != 0);
    }

    public static void homePage(Connection conn) {
        int chosse = 0;
        do {
            System.out.println("Trang chu!");
            Scanner sc = new Scanner(System.in);
            switch (chosse) {
                case 1:
                    showMenuHome();
                    handleViewProduct(conn);
                    showMenuHome();
                    break;
                case 2:
                    showMenuHome();
                    handleCreateOrder(conn);
                    showMenuHome();

                    break;

                case 3:
                    showMenuHome();
                    handleViewOrder(conn, 1);
                    showMenuHome();

                    break;
                default:
                    showMenuHome();
                    break;
            }
            System.out.print("Nhap lua chon cua ban: ");
            chosse = sc.nextInt();
        } while (chosse != 0);

    }

    public static void showMenuHome() {
        System.out.println("1. Xem danh sach san pham");
        System.out.println("2. Order");
        System.out.println("3. Xem danh sach Order");

        System.out.println("0. Dang xuat");
    }

    public static void showMenuAdmin() {
        System.out.println("1. Xem danh sach san pham");
        System.out.println("2. Them san pham");
        System.out.println("3. Xoa san pham");
        System.out.println("4. Xem danh muc");
        System.out.println("5. Them danh muc");
        System.out.println("6. Xem danh sach order");

        System.out.println("0. Dang xuat");
    }

    public static void handleCreateOrder(Connection conn) {
        Scanner sc = new Scanner(System.in);
        System.out.println("------------------- Nhap thong tin order --------------------");
        System.out.print("Id san pham: ");
        String idProduct = sc.nextLine();

        System.out.print("So luong: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        System.out.print("So dien thoai nhan hang: ");
        String phone = sc.nextLine();

        System.out.print("Dia chi nhan: ");
        String address = sc.nextLine();

        try {
            CallableStatement cStmt = null;
            cStmt = conn.prepareCall("{call create_order(?, ?, ?, ?, ?, ?)}");

            cStmt.setString(1, idProduct);
            cStmt.setString(2, phone);
            cStmt.setString(3, address);
            cStmt.setInt(4, quantity);
            cStmt.setInt(5, userIdLogin);

            cStmt.registerOutParameter(6, Types.INTEGER);
            cStmt.executeUpdate();

            int result = cStmt.getInt(6);
            if (result == 1) {
                System.out.println("Dat hang thanh cong!");
            } else if (result == 0) {
                System.out.println("Khong tim thay san pham!");
            } else {
                System.out.println("So luong san pham khong du de cung cap cho ban!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void handleViewCategory(Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM category");

            System.out.println("-----------------------Danh sách danh muc:----------------");
            System.out.println("Id" + "\t " + "Ten danh muc" + "\t " + "Mo ta");

            while (rs.next()) {
                String name = rs.getString("name"); // resultset trả về gồm 2 trường là
                String description = rs.getString("description");
                String id = rs.getString("idCategory"); // MSSV và họ tên
                // MSSV và họ tên
                System.out.println(id + "\t " + name + "\t " + description);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void handleViewProduct(Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(
                    "SELECT p.idProduct, p.name, p.description, p.price, p.quantity, p.hardWare, c.name nameCategory FROM product p left join category c on p.idCategory = c.idCategory");

            System.out.println(
                    "---------------------------------------------- Danh sach san pham ------------------------------------------------");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------");
            System.out.format("%-3s %-18s %-25s %-18s %-18s %-18s %-18s\n", "Id", "Ten san pham", "Mo ta", "Gia",
                    "So luong",
                    "Phan cung", "Danh muc");

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                String price = rs.getString("price");
                String hardWare = rs.getString("hardWare");
                String nameCategory = rs.getString("nameCategory");
                Integer quantity = rs.getInt("quantity");
                String id = rs.getString("idProduct");
                System.out.format("%-3s %-18s %-25s %-18s %-18d %-18s %-18s\n", id, name, description, price, quantity,
                        hardWare, nameCategory);
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void handleViewOrder(Connection conn, int isUser) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try {
            String sql;
            if (isUser == 1) {
                sql = "SELECT *, p.name productName  FROM orderList,product p WHERE idUser = ? and p.idProduct = orderList.idProduct; ";
                pStmt = conn.prepareStatement(sql);

                System.out.println(
                        "userIdLogin ----------------------------------------------------------------" + userIdLogin);
                pStmt.setInt(1, userIdLogin);
                rs = pStmt.executeQuery();
                System.out.println(
                        "------------------------------------------------------------------------------------------------------------------");
                System.out.format("%-3s %-18s %-25s %-18s %-18s %-18s %-18s\n", "Id", "Ten san pham", "So luong",
                        "SDT nhan", "Dia chi nhan",
                        "Ngay dat hang", "Tong gia");
                while (rs.next()) {
                    int id = rs.getInt("idOrderList");
                    int total = rs.getInt("amount");
                    Date date = rs.getDate("date");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");

                    System.out.format("%-3s %-18s %-25d %-18s %-18s %-18s %-18d\n", id, productName, quantity, phone,
                            address,
                            date.toString(), total);
                }
            } else {
                sql = "SELECT *, user.fullName userName, p.name productName  FROM orderList,user, product p \n" + //
                        "WHERE user.idUser = orderList.idUser and p.idProduct = orderList.idProduct;";
                pStmt = conn.prepareStatement(sql);

                rs = pStmt.executeQuery();
                System.out.println(
                        "------------------------------------------------------------------------------------------------------------------");
                System.out.format("%-3s %-18s %-25s %-18s %-18s %-18s %-18s %-18s\n", "Id", "Ten san pham", "So luong",
                        "SDT nhan", "Dia chi nhan",
                        "Ngay dat hang", "Khach hang", "Tong gia");
                while (rs.next()) {
                    int id = rs.getInt("idOrderList");
                    int total = rs.getInt("amount");
                    Date date = rs.getDate("date");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String productName = rs.getString("productName");
                    int userName = rs.getInt("userName");
                    int quantity = rs.getInt("quantity");

                    System.out.format("%-3s %-18s %-25d %-18s %-18s %-18s %-18d %-18d\n", id, productName, quantity,
                            phone,
                            address,
                            date.toString(), userName, total);
                }
            }

            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void handleCreateCategory(Connection conn) {
        Scanner sc = new Scanner(System.in);
        System.out.println("------------------- Nhap thong tin danh muc--------------------");
        System.out.print("Ten danh muc: ");
        String name = sc.nextLine();

        System.out.print("Mo ta: ");
        String description = sc.nextLine();

        Category category = new Category(name, description);
        System.out.println(category.toString());
        try {
            PreparedStatement pStmt = null;
            pStmt = conn.prepareStatement("insert into category(name, description) values(?,?);");
            pStmt.setString(1, category.getName());
            pStmt.setString(2, category.getDescription());
            pStmt.executeUpdate();

            System.out.println("Tao danh muc thanh cong!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void handleDeleteProduct(Connection conn) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------Nhap id san pham muon xoa--------------------");
        System.out.print("idSanPham: ");
        String id = sc.nextLine();

        try {
            PreparedStatement pStmt = null;
            pStmt = conn.prepareStatement("DELETE FROM product WHERE idProduct = ?;");
            pStmt.setString(1, id);
            pStmt.executeUpdate();

            System.out.println("Xoa san pham thanh cong!");
            System.out.println();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void handleCreateProduct(Connection conn) {
        Scanner sc = new Scanner(System.in);
        System.out.println("------------------- Nhap thong tin san pham--------------------");
        System.out.print("Ten san pham: ");
        String name = sc.nextLine();

        System.out.print("Mo ta: ");
        String description = sc.nextLine();

        System.out.print("Gia: ");
        String price = sc.nextLine();

        System.out.print("Cau hinh: ");
        String hardWare = sc.nextLine();

        System.out.print("So luong: ");
        Integer quantity = sc.nextInt();

        System.out.println("Danh muc: ");
        handleViewCategory(conn);
        System.out.print("Lua chon: ");

        int idCategory = sc.nextInt();

        Product product = new Product(name, description, price, quantity, hardWare, idCategory);

        System.out.println(product.toString());
        try {
            CallableStatement cStmt = null;
            cStmt = conn.prepareCall("{call add_product(?, ?, ?, ?, ?, ?, ? )}");

            cStmt.setString(1, product.getName());
            cStmt.setString(2, product.getDescription());
            cStmt.setString(3, product.getPrice());
            cStmt.setInt(4, product.getQuantity());
            cStmt.setString(5, product.gethHardWare());
            cStmt.setInt(6, product.getiIdCategory());
            cStmt.registerOutParameter(7, Types.INTEGER);
            cStmt.executeUpdate();

            int result = cStmt.getInt(7);
            if (result == 1) {
                System.out.println("Tao san pham thanh cong!");
            } else {
                System.out.println("Danh muc khong tim thay!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
