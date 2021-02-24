import Repository.*;
import Tables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Hanna Edlund
 * Date: 2021-02-18
 * Time: 14:27
 * Project: ShoeDB2
 */
public class Dashboard {
    Repository r = new Repository();
    getID_Repository rID = new getID_Repository();
    getProduct_Repository rProduct = new getProduct_Repository();
    Scanner sc = new Scanner(System.in);
    String lastname;
    String password;
    int customerID;
    int amountOfOrders = -1;
    int orderID = 0;
    int productID;
    int gradeID;
    int colorID;
    int sizeID;
    String datumOfOrder;
    String aComment;
    List<String> listOfOrders = new ArrayList<>();
    List<Products> filterList = new ArrayList<>();


    public Dashboard() {
        do{
            System.out.println("Skriv in ditt efternamn:");
            lastname = sc.nextLine();
            System.out.println("Skriv in ditt lösenord:");
            password = sc.nextLine();
            customerID = rID.getUsersID(lastname, password);
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
    }

    private boolean isValidId(int id){
        if(id == 0){
            System.out.println("Oj något gick fel, försök igen!");
            return false;
        }
        else  return true;
    }

    private boolean isValidSizeIDForFilterColor(int id){
        int answer = (int) filterList.stream().filter(products -> products.getSize().getId() != id).count();

        if(answer == filterList.size()){
            System.out.println("Oj något gick fel, försök igen!");
            return false;
        }
        return true;
    }

    private boolean isValidDate(){
        if(!listOfOrders.contains(datumOfOrder)){
            System.out.println("Oj något gick fel, försök igen!");
            return false;
        }
        else return true;
    }

    private void getAllProductsFromOrder(){
        orderID = rID.getOrderID(datumOfOrder);
        rProduct.getProductsFromOrder(orderID);
    }

    public void addToCart(){
        selectProduct(1);
        System.out.println(r.addToCart(customerID, orderID, productID));
    }

    public void selectProduct(int saldo){
        rProduct.getlistOfProductsDependingOnSaldo(saldo);

        if(saldo > 0){
            do{
                System.out.println("\nFiltrera sökningen på färg, skriv in färgen");
                String answer = sc.nextLine();
                colorID = rID.getColorID(answer);
            }while (!(isValidId(colorID)));
            filterList = rProduct.filterProductsOnColor(colorID,saldo);

            if(filterList.size() > 2){
                do{
                    System.out.println("\nFiltrera sökningen på storlek, skriv in storlek");
                    String answer = sc.nextLine();
                    sizeID = rID.getSizeID(answer);
                }while (!(isValidSizeIDForFilterColor(sizeID)));
            }
            rProduct.filterProductsOnColorAndSize(colorID,saldo,sizeID);
        }

        do{
            System.out.println("\nVälj en av produkterna, skriv in namnet");
            String answer = sc.nextLine();
            productID = rID.getProductID(answer);
        } while (!isValidId(productID));
    }

    public void selectGrade(){
        do{
            System.out.println("Välj en av följande betyg? \n " + r.getlistOfGrades());
            String answer = sc.nextLine();
            gradeID = rID.getGradeID(answer);
        } while (!isValidId(gradeID));
    }

    public static void main(String[] args) {
        Dashboard t = new Dashboard();
    }
}
