/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:08
 * Project: ShoeDB2
 */
public class Colors {

    private int id;
    private String color;

    public Colors(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
