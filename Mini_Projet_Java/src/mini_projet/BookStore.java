package mini_projet;


//Importation_des_bibliothèques
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class BookStore extends UnicastRemoteObject implements RMIInterface {
    private static final long serialVersionUID = 1L;
    private List<Book> books = new ArrayList<>();

    // Constructeur
    public BookStore() throws RemoteException {
        super();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\pc\\Desktop\\Book_Store.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String titre = parts[0];
                    String isbn = parts[1];
                    double prix = Double.parseDouble(parts[2]);
                    books.add(new Book(titre, isbn, prix));
                } else {
                    System.out.println("Erreur de format de ligne : " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // allBooks_function
    @Override
    public List<Book> allBooks() throws RemoteException {
        return books;
    }

    // findBook_function
    @Override
    public Book findBook(Book b) throws RemoteException {
        for (Book book : books) {
            if (book.getIsbn().equals(b.getIsbn())) {
                return book;
            }
        }
        return null;
    }
   
    // getBooksPrice_function
    @Override
    public double getBooksPrice() throws RemoteException {
        double somme = 0;
        for (Book b : books) {
            somme += b.getPrix();
        }
        return books.size() > 0 ? somme / books.size() : 0;
    }
}