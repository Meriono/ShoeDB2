/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:07
 * Project: ShoeDB2
 */
public class Brands {

    private int id;
    private String brand;

    public Brands(int id, String brand) {
        this.id = id;
        this.brand = brand;
    }

    public Brands(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
