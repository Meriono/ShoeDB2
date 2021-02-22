/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:09
 * Project: ShoeDB2
 */
public class Reviews {

    private int id;
    private Customers customer;
    private Products product;
    private Gradings grade;
    private String comment;

    public Reviews(int id, Customers customer, Products product, Gradings grade, String comment) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.grade = grade;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Gradings getGrade() {
        return grade;
    }

    public void setGrade(Gradings grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
