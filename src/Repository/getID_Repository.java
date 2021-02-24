package Repository;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-24
 * Time: 11:53
 * Project: ShoeDB2
 */
public class getID_Repository {
    private Properties p = new Properties();

    public getID_Repository() {
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

    public int getSizeID(String sizeInString){
        int sizeID = 0;
        int sizeToInt = Integer.parseInt(sizeInString);
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select id from sizes where size = ?;")) {

            stmt.setInt(1, sizeToInt);
            stmt.executeQuery();
            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                sizeID = rs.getInt("id");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return sizeID;
    }


    public int getColorID(String colorName){
        int colorID = 0;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"), p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select id from colors where color = ?;")) {

            stmt.setString(1, colorName);
            stmt.executeQuery();
            ResultSet rs = null;
            rs = stmt.executeQuery();

            while (rs.next()){
                colorID = rs.getInt("id");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return colorID;
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
}
