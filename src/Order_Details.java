/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:10
 * Project: ShoeDB2
 */
public class Order_Details {
    private int id;
    private Orders order;
    private Products product;
    private int amount;

    public Order_Details(int id, Orders order, Products product, int amount) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
