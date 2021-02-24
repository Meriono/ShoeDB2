package Tables;

import java.util.Date;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:08
 * Project: ShoeDB2
 */
public class Orders {

    private int id;
    private Date datum;
    private Customers customer;

    public Orders(int id, Date datum, Customers customer) {
        this.id = id;
        this.datum = datum;
        this.customer = customer;
    }
    public Orders(){}

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

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }
}
