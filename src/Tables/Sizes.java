package Tables;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:09
 * Project: ShoeDB2
 */
public class Sizes {

    private int id;
    private int size;

    public Sizes(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public Sizes(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
