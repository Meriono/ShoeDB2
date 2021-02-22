import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-18
 * Time: 15:05
 * Project: ShoeDB2
 */
public class Repository {
    private Properties p = new Properties();

    public Repository() {
        try {
            p.load(new FileInputStream("src/Settings.properties"));
            // Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUsersID(String lastname, String password){
        int usersID = 0;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("SELECT id as nu from customers where lastname = ? AND password = ?")) {

            stmt.setString(1, lastname);
            stmt.setString(2, password);
            stmt.executeQuery();

            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                usersID = rs.getInt("nu");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return usersID;
    }

    public List<String> getUsersOrders(int usersID){
        List <String> orderDatum = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select datum as nu from orders where customer_id = ?;")) {

            stmt.setInt(1, usersID);
            stmt.executeQuery();

            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                orderDatum.add(rs.getString("nu"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return orderDatum;
    }

    public int getOrderID(String datumForAnOrder){
        int orderID = 0;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select id as nu from orders where datum = ?;")) {

            stmt.setString(1, datumForAnOrder);
            stmt.executeQuery();
            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                orderID = rs.getInt("nu");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return orderID;
    }

    public List<String> getProductsFromOrder(int orderID){
        List<String> products = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select name as nu from products inner join order_details on product_id = products.id where order_id = ?;")) {

            stmt.setInt(1, orderID);
            stmt.executeQuery();
            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                products.add(rs.getString("nu"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return products;
    }

    public List<String> getlistOfProductsDependingOnSaldo(int saldo){
        List <String> products = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select name as nu from products where saldo >= ?;")) {

            stmt.setInt(1, saldo);
            stmt.executeQuery();

            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                products.add(rs.getString("nu"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return products;
    }

    public List<String> getlistOfGrades(){
        List <String> grades = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select iText as nu from gradings")) {

            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                grades.add(rs.getString("nu"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return grades;
    }

    public int getProductID(String productNamn){
        int productID = 0;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select id as nu from products where name = ?;")) {

            stmt.setString(1, productNamn);
            stmt.executeQuery();
            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                productID = rs.getInt("nu");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return productID;
    }

    public int getGradeID(String gradeNamn){
        int gradeID = 0;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select id as nu from gradings where iText = ?;")) {

            stmt.setString(1, gradeNamn);
            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                gradeID = rs.getInt("nu");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return gradeID;
    }

    public String addToCart(int CustomerId, int OrderId, int ProductId) {
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             CallableStatement stmt = con.prepareCall("call AddToCart(?,?,?);")) {

            stmt.setInt(1,CustomerId);
            stmt.setInt(2,OrderId);
            stmt.setInt(3,ProductId);
            rs = stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "succes in added a new order";
    }

    public String Rate(int gradeID, String aComment, int customerID, int productID) {
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             CallableStatement stmt = con.prepareCall("call Rate(?,?,?,?);")) {

            stmt.setInt(1,gradeID);
            stmt.setString(2,aComment);
            stmt.setInt(3,customerID);
            stmt.setInt(4,productID);
            rs = stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "succes in added a new review";
    }

    public void viewReviewForAProduct(int ProductId) {
        ResultSet rs = null;
        String productNamn = "";
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt2 = con.prepareStatement("select name as nu from products where id = ?;");
             PreparedStatement stmt = con.prepareStatement("select * from review_details_OnAllProducts where Namn = ?;");
             PreparedStatement stmt3 = con.prepareStatement("select Kommentar from review_details where Produkt = ?;")) {

            stmt2.setInt(1,ProductId);

            rs = stmt2.executeQuery();
            while (rs.next()){
                productNamn = rs.getString("nu");
            }

            stmt.setString(1,productNamn);
            stmt3.setString(1,productNamn);
            List<String> listOfComments = new ArrayList<>();
            rs = stmt3.executeQuery();
            while (rs.next()){
                if(!rs.getString(1).equalsIgnoreCase("No comment"))
                listOfComments.add(rs.getString(1));
            }

            rs = stmt.executeQuery();


                while (rs.next()){
                    if(rs.getString(3) != null){
                    System.out.println("Medelbetyg i siffror: " + rs.getString(3));
                    System.out.println("Medelbetyg: " + rs.getString(4));
                    System.out.println("Kommentarer: " + listOfComments);
                    }
                    else
                        System.out.println("Produkten har inga omdömmen");
                }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
