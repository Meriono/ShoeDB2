import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-18
 * Time: 14:27
 * Project: ShoeDB2
 */
public class Testing {
    Repository r = new Repository();
    Scanner sc = new Scanner(System.in);
    String lastname;
    String password;
    int customerID;
    int amountOfOrders = -1;
    int orderID = 0;
    int productID;
    int gradeID;
    String datumOfOrder;
    String aComment;
    List<String> listOfOrders = new ArrayList<>();


    public Testing() {
        do{
            System.out.println("Skriv in ditt efternamn:");
            lastname = sc.nextLine();
            System.out.println("Skriv in ditt lösenord:");
            password = sc.nextLine();
            customerID = r.getUsersID(lastname, password);
        } while (!isValidId(customerID));


        System.out.println("Vill du se produkter i en beställning? y/n");
        if(sc.nextLine().equalsIgnoreCase("y")){
            listOfOrders = r.getUsersOrders(customerID);
            amountOfOrders = listOfOrders.size();
        }


        if(amountOfOrders > 1){
            do{
                System.out.println("Ange datumet för vilken av dina "+ amountOfOrders +" beställningar du vill se produkterna på\n" + listOfOrders.toString());
                datumOfOrder = sc.nextLine();
            } while (!isValidDate());
            getAllProductsFromOrder();
        }
        else if(amountOfOrders == 1) {
            System.out.println("Du har bara en order från " + listOfOrders.get(0));
            datumOfOrder = listOfOrders.get(0);
            getAllProductsFromOrder();
        }
        else if(amountOfOrders == 0)
            System.out.println("Du har inga orders");


        if(amountOfOrders >= 1){
            System.out.println("Vill du uppdatera ordern? y/n");
            if(sc.nextLine().equalsIgnoreCase("y")){
                addToCart();
            }
        }

        System.out.println("Vill du göra en ny beställning? y/n");
        if(sc.nextLine().equalsIgnoreCase("y")){
            orderID = 0;
            addToCart();
        }

        //Skapa funktionalitet så att användaren kan se genomsnittligt betyg samt kommentarer för en produkt
        System.out.println("Vill du se omdömmen för en produkt? y/n");
        if(sc.nextLine().equalsIgnoreCase("y")){
            selectProduct(0);
            r.viewReviewForAProduct(productID);
        }

        //Skapa funktionalitet så att användaren kan ge betyg och kommentera på en produkt
        System.out.println("Vill du skriva ett omdömme på en produkt? y/n");
        if(sc.nextLine().equalsIgnoreCase("y")){
            selectProduct(0);
            selectGrade();
            System.out.println("Vill du lägga till en kommentar? y/n");
            if(sc.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Skriv din kommentar, avsluta med enter");
                aComment = sc.nextLine();
            }
            else {
                aComment = "No comment";
            }
            r.Rate(gradeID,aComment,customerID,productID);
        }

        //Vill du filtrera sökningen efter produkter?
        //Om ja, färg, märke eller storlek?
        // skriv in x
        //Skriv in namnet på produkten du vill lägga till i beställningen
    }

    private boolean isValidId(int id){
        if(id == 0){
            System.out.println("Oj något gick fel, försök igen!");
            return false;
        }
        else  return true;
    }

    private boolean isValidDate(){
        if(!listOfOrders.contains(datumOfOrder)){
            System.out.println("Oj något gick fel, försök igen!");
            return false;
        }
        else return true;
    }

    private void getAllProductsFromOrder(){
        orderID = r.getOrderID(datumOfOrder);
        System.out.println(r.getProductsFromOrder(orderID));
    }

    public void addToCart(){
        selectProduct(1);
        System.out.println(r.addToCart(customerID, orderID, productID));
    }

    public void selectProduct(int saldo){
        do{
            System.out.println("Välj en av följande produkter? \n " + r.getlistOfProductsDependingOnSaldo(saldo).toString());
            String answer = sc.nextLine();
            productID = r.getProductID(answer);
        } while (!isValidId(productID));
    }

    public void selectGrade(){
        do{
            System.out.println("Välj en av följande betyg? \n " + r.getlistOfGrades());
            String answer = sc.nextLine();
            gradeID = r.getGradeID(answer);
        } while (!isValidId(gradeID));
    }

    public static void main(String[] args) {
        Testing t = new Testing();
    }
}
