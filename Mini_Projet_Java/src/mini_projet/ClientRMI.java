package mini_projet;


//Importation_des_bibliothèques
import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class ClientRMI {
    public static void main(String[] args) {
        try {
            RMIInterface bookStore = (RMIInterface) Naming.lookup("rmi://localhost:9000/BK");
            System.out.println("Service connected");
            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\nMenu:");
                System.out.println("1 : Afficher la liste des livres");
                System.out.println("2 : Chercher un livre par son ISBN");
                System.out.println("3 : Afficher le prix moyen des livres");
                System.out.println("0 : Quitter");
                System.out.print("Choix : ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        List<Book> books = bookStore.allBooks();
                        books.forEach(System.out::println);
                        break;
                    case 2:
                        System.out.print("Entrez l'ISBN du livre à chercher : ");
                        String isbn = scanner.nextLine();
                        Book bookToFind = new Book("", isbn, 0.0);
                        Book book = bookStore.findBook(bookToFind);
                        if (book != null) {
                            System.out.println("Titre : " + book.getTitre());
                            System.out.println("Prix : " + book.getPrix());
                        } else {
                            System.out.println("Livre non trouvé.");
                        }
                        break;
                    case 3:
                        double averagePrice = bookStore.getBooksPrice();
                        System.out.println("Le prix moyen des livres : " + averagePrice);
                        break;
                    case 0:
                        System.out.println("GoodBye !");
                        break;
                    default:
                        System.out.println("Choix invalide, veuillez réessayer.");
                        break;
                }
            } while (choice != 0);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}