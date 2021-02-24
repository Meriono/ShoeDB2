package Tables;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:08
 * Project: ShoeDB2
 */
public class Cities {

    private int id;
    private String city;

    public Cities(int id, String city) {
        this.id = id;
        this.city = city;
    }

    public Cities(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
