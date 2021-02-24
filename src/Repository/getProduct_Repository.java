package Repository;

import Tables.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-24
 * Time: 11:58
 * Project: ShoeDB2
 */
public class getProduct_Repository {

    private Properties p = new Properties();

    public getProduct_Repository() {
        try {
            p.load(new FileInputStream("src/Settings.properties"));
            // Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Products getProductByOrder_detailsID(int orderDetailsID){
        Products product = new Products();
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(
                     "select products.id, products.name, products.price, products.saldo from products inner join order_details on order_details.product_id = products.id where order_details.id = ?")){

            stmt.setInt(1,orderDetailsID);
            rs = stmt.executeQuery();


            while (rs.next()){
                int productID = rs.getInt("id");
                Sizes size = getSizeByProductId(productID);
                Colors color = getColorByProductId(productID);
                Brands brand = getBrandByProductId(productID);

                product = new Products(
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
        return product;
    }



    public void getProductsFromOrder(int orderID){
        ResultSet rs = null;
        List<Order_Details> productsOrders = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(
                     "Select order_details.id as odId, order_details.amount from orders inner join order_details on order_details.order_id = orders.id where orders.id = ?")) {

            stmt.setInt(1, orderID);
            stmt.executeQuery();

            rs = stmt.executeQuery();

            while (rs.next()){
                int order_detailID = rs.getInt("odId");
                Orders order = getOrderByOrder_detailsID(order_detailID);
                Products product = getProductByOrder_detailsID(order_detailID);


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
            System.out.println((order_details.getProduct().getName()+ " | Färg: "+ order_details.getProduct().getColor().getColor() + " | Storlek: "+ order_details.getProduct().getSize().getSize() + " | Märke: " + order_details.getProduct().getBrand().getBrand() + " | Antal: "+ order_details.getAmount()));
        });
    }

    public List<Products> filterProductsOnColor(int colorID, int saldo){
        List <Products> filterListOnColor = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select * from products inner join colors on colors.id = products.color_id where saldo >= ? AND colors.id = ?;")) {

            stmt.setInt(1, saldo);
            stmt.setInt(2, colorID);
            stmt.executeQuery();

            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                int productID = rs.getInt("id");
                Sizes size = getSizeByProductId(productID);
                Colors color = getColorByProductId(productID);
                Brands brand = getBrandByProductId(productID);

                filterListOnColor.add(new Products(
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

        filterListOnColor.forEach(products -> {

            System.out.println((products.getName()+ " | Färg: "+ products.getColor().getColor() + " | Storlek: "+ products.getSize().getSize() + " | Märke: " + products.getBrand().getBrand() + " | Saldo: "+ products.getSaldo()));

        });
        return filterListOnColor;
    }


    public void filterProductsOnColorAndSize(int colorID, int saldo, int sizeID){
        List <Products> filterList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select * from products inner join colors on colors.id = products.color_id inner join sizes on sizes.id = products.size_id where saldo >= ? AND colors.id = ? AND sizes.id = ?;")) {

            stmt.setInt(1, saldo);
            stmt.setInt(2, colorID);
            stmt.setInt(3,sizeID);
            stmt.executeQuery();

            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                int productID = rs.getInt("id");
                Sizes size = getSizeByProductId(productID);
                Colors color = getColorByProductId(productID);
                Brands brand = getBrandByProductId(productID);

                filterList.add(new Products(
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

        filterList.forEach(products -> {

            System.out.println((products.getName()+ " | Färg: "+ products.getColor().getColor() + " | Storlek: "+ products.getSize().getSize() + " | Märke: " + products.getBrand().getBrand() + " | Saldo: "+ products.getSaldo()));

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

    public Cities getCityByCustomerID(int customerID){
        Cities city = new Cities();
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select cities.id, cities.city from cities inner join customers on customers.city_id = cities.id where customers.id = ?")) {

            stmt.setInt(1, customerID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                city = new Cities(rs.getInt("id"), rs.getString("city"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return city;
    }

    public Customers getCustomerByOrderId(int orderID){
        Customers customer = new Customers();
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(
                     "select customers.id, customers.firstname, customers.lastname, customers.password from customers inner join orders on orders.customer_id = customers.id where orders.id = ?")){

            stmt.setInt(1,orderID);
            rs = stmt.executeQuery();



            while (rs.next()){
                int customerID = rs.getInt("id");
                Cities city = getCityByCustomerID(customerID);
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

    public Orders getOrderByOrder_detailsID(int orderDetailsID){
        Orders order = new Orders();
        ResultSet rs = null;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(
                     "select orders.id, orders.datum from orders inner join order_details on order_details.order_id = orders.id where order_details.id = ?")){

            stmt.setInt(1,orderDetailsID);
            rs = stmt.executeQuery();

            while (rs.next()){
                int orderID = rs.getInt("orders.id");
                Customers customer = getCustomerByOrderId(orderID);

                order = new Orders(
                        rs.getInt("id"),
                        rs.getDate("datum"),
                        customer
                );
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
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
