package Tables;

import Tables.Products;

import java.util.Date;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:09
 * Project: ShoeDB2
 */
public class SlutiLager {

    private int id;
    private Date datum;
    private Products product;

    public SlutiLager(int id, Date datum, Products product) {
        this.id = id;
        this.datum = datum;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
