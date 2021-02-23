import java.util.List;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:08
 * Project: ShoeDB2
 */
public class Products {

    private int id;
    private String name;
    private Sizes size;
    private Colors color;
    private Brands brand;
    private int price;
    private int saldo;
    private List<Products> productsList;

    public Products(int id, String name, Sizes size, Colors color, Brands brand, int price, int saldo) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.color = color;
        this.brand = brand;
        this.price = price;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sizes getSize() {
        return size;
    }

    public void setSize(Sizes size) {
        this.size = size;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public Brands getBrand() {
        return brand;
    }

    public void setBrand(Brands brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
