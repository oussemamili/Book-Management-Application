package mini_projet;


//Importation_des_bibliothèques
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIInterface extends Remote {
    public Book findBook(Book b) throws RemoteException;
    public List<Book> allBooks() throws RemoteException;
    public double getBooksPrice() throws RemoteException;
}