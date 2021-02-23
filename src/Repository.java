import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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

    public Products getProductByID(int productID){
        Products prod = new Products();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select * from products where id = ?")){

            stmt.setInt(1,productID);
            stmt.executeQuery();
            ResultSet rs = null;

            while (rs != null && rs.next()){
                Sizes size = getSizeByProductId(productID);
                Colors color = getColorByProductId(productID);
                Brands brand = getBrandByProductId(productID);

                prod = new Products(
                        rs.getInt("id"),
                        rs.getString("name"),
                        size,
                        color,
                        brand,
                        rs.getInt("price"),
                        rs.getInt("saldo"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return prod;
    }

    public Cities getCityByID(int cityID){
        Cities city = new Cities();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select * from cities where id = ?")) {

            stmt.setInt(1, cityID);
            stmt.executeQuery();

            ResultSet rs = null;

            while (rs.next()) {
                city = new Cities(rs.getInt("id"), rs.getString("city"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return city;
    }

    public Customers getCustomerById(int customerID){
        Customers customer = new Customers();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select * from customers where id = ?")){

            stmt.setInt(1,customerID);
            stmt.executeQuery();

            ResultSet rs = null;

            while (rs.next()){
                int cityID = rs.getInt("city");
                Cities city = getCityByID(cityID);
                customer = new Customers(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        city,
                        rs.getString("password")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return customer;
    }

    public Orders getOrderByID(int orderID){
        Orders ord = new Orders();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select * from orders where id = ?")){

            stmt.setInt(1,orderID);
            stmt.executeQuery();
            ResultSet rs = null;

            while (rs != null && rs.next()){
                int customerID = rs.getInt("customer_id");
                Customers customer = getCustomerById(customerID);

                ord = new Orders(
                        rs.getInt("id"),
                        rs.getDate("date"),
                        customer
                        );
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return ord;
    }

    public void getProductsFromOrder(int orderID){
        List<Order_Details> productsOrders = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(
                     "select order_details.id as odId, products.id as pId, order_details.amount as amount from products inner join order_details on product_id = products.id where order_id = ?;")) {

            stmt.setInt(1, orderID);
            stmt.executeQuery();
            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                int productID = rs.getInt("pId");
                Orders order = getOrderByID(orderID);
                Products product = getProductByID(productID);


                productsOrders.add(new Order_Details(
                        rs.getInt("odId"),
                        order,
                        product,
                        rs.getInt("amount"))
                );
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }

        productsOrders.forEach(order_details -> {

                System.out.println("Order_Details{" +
                        ", product=" + order_details.getProduct().getName() +
                        ", färg:=" + order_details.getProduct().getColor() +
                        ", storlek:=" + order_details.getProduct().getSize() +
                        ", märke:=" + order_details.getProduct().getBrand() +
                        ", amount=" + order_details.getAmount());

        });
    }


    public void getlistOfProductsDependingOnSaldo(int saldo){
         List <Products> productsDependingOnSaldoList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select * from products where saldo >= ?;")) {

            stmt.setInt(1, saldo);
            stmt.executeQuery();

            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                int productID = rs.getInt("id");
                Sizes size = getSizeByProductId(productID);
                Colors color = getColorByProductId(productID);
                Brands brand = getBrandByProductId(productID);

                productsDependingOnSaldoList.add(new Products(
                        rs.getInt("id"),
                        rs.getString("name"),
                        size,
                        color,
                        brand,
                        rs.getInt("price"),
                        rs.getInt("saldo")));
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }

        productsDependingOnSaldoList.forEach(products -> {

            if (saldo >= 1){
                System.out.println((products.getName()+ " | Färg: "+ products.getColor().getColor() + " | Storlek: "+ products.getSize().getSize() + " | Märke: " + products.getBrand().getBrand() + " | Saldo: "+ products.getSaldo()));
            }
            else {
                System.out.println(products.getName() + " | Färg: " + products.getColor().getColor() + " | Märke: " + products.getBrand().getBrand());

            }

        });
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
        int result = 0;
        String message = "";
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             CallableStatement stmt = con.prepareCall("call AddToCart(?,?,?);")) {

            stmt.setInt(1,CustomerId);
            stmt.setInt(2,OrderId);
            stmt.setInt(3,ProductId);
            result = stmt.executeUpdate();

            rs = stmt.getResultSet();
            while (rs != null && rs.next()){
                message = rs.getString("error");
                System.out.println(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result == -1){
            return message;
        }
        else return "succes in added a new order";
    }

    public String Rate(int gradeID, String aComment, int customerID, int productID) {
        ResultSet rs = null;
        String message = "";
        int result = 0;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             CallableStatement stmt = con.prepareCall("call Rate(?,?,?,?);")) {

            stmt.setInt(1,gradeID);
            stmt.setString(2,aComment);
            stmt.setInt(3,customerID);
            stmt.setInt(4,productID);

            result = stmt.executeUpdate();
            rs = stmt.getResultSet();

            while (rs != null && rs.next()){
                message = rs.getString("error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result == -1){
            return message;
        }
        else return "succes in added a new review";
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

    public Sizes getSizeByProductId(int productID){
        Sizes size = new Sizes();
        ResultSet rs = null;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(
                     "select sizes.id, sizes.size from sizes inner join products on products.size_id = sizes.id where products.id = ?")){

            stmt.setInt(1, productID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                size = new Sizes(
                        rs.getInt("id"),rs.getInt("size"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

    public Colors getColorByProductId(int productID){
        Colors color = new Colors();
        ResultSet rs = null;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(
                     "select colors.id, colors.color from colors inner join products on products.color_id = colors.id where products.id = ?")){

            stmt.setInt(1, productID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                color = new Colors(
                        rs.getInt("id"),rs.getString("color"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return color;
    }

    public Brands getBrandByProductId(int productID){
        Brands brand = new Brands();
        ResultSet rs = null;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(
                     "select brands.id, brands.brand from brands inner join products on products.brand_id = brands.id where products.id = ?")){

            stmt.setInt(1, productID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                brand = new Brands(
                        rs.getInt("id"),rs.getString("brand"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return brand;
    }

}
