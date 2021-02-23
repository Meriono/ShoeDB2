/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:08
 * Project: ShoeDB2
 */
public class Customers {

    private int id;
    private String firstname;
    private String lastname;
    private Cities city;
    private String password;

    public Customers(int id, String firstname, String lastname, Cities city, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.password = password;
    }

    public Customers(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
