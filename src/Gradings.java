/**
 * Created by Hanna Edlund
 * Date: 2021-02-22
 * Time: 14:08
 * Project: ShoeDB2
 */
public class Gradings {

    private int id;
    private String iText;
    private int iSiffror;

    public Gradings(int id, String iText, int iSiffror) {
        this.id = id;
        this.iText = iText;
        this.iSiffror = iSiffror;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getiText() {
        return iText;
    }

    public void setiText(String iText) {
        this.iText = iText;
    }

    public int getiSiffror() {
        return iSiffror;
    }

    public void setiSiffror(int iSiffror) {
        this.iSiffror = iSiffror;
    }
}
