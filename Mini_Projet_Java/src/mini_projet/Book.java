package mini_projet;


// Importation_des_bibliothèques
import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titre;
    private String isbn;
    private double prix;

    // Constructeur_paramétré
    public Book(String titre, String isbn, double prix) {
        this.titre = titre;
        this.isbn = isbn;
        this.prix = prix;
    }

    // Constructeur_non_paramétré
    public Book() {}

    // toString_function
    @Override
    public String toString() {
        return "Book{" + "titre='" + titre + '\'' + ", isbn='" + isbn + '\'' + ", prix=" + prix + '}';
    }

    // getTitre_function
    public String getTitre() {
        return titre;
    }

    // getIsbn_function
    public String getIsbn() {
        return isbn;
    }

    // getPrix_function
    public double getPrix() {
        return prix;
    }
}