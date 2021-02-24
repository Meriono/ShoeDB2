package Tables;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:10
 * Project: ShoeDB2
 */
public class Category_product {
    private int id;
    private Products product;
    private Categories category;

    public Category_product(int id, Products product, Categories category) {
        this.id = id;
        this.product = product;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }
}
