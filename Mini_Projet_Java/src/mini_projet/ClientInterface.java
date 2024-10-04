package mini_projet;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.util.List;

public class ClientInterface extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private RMIInterface bookStore;
    private JTextField isbnField;
    private JTextArea resultArea;

    public ClientInterface() {
        super("Interface Client");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, 
                    "Êtes-vous sûr de vouloir quitter?", "Confirmation de fermeture",
                    JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        setSize(500, 400);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 1, 5, 5));
        JLabel isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(10);
        JButton searchButton = new JButton("Chercher");
        JButton allBooksButton = new JButton("Afficher tous les livres");
        JButton averagePriceButton = new JButton("Afficher prix moyen");
        JButton quitButton = new JButton("Quitter");
        searchButton.addActionListener(this);
        allBooksButton.addActionListener(this);
        averagePriceButton.addActionListener(this);
        quitButton.addActionListener(this);
        topPanel.add(isbnLabel);
        topPanel.add(isbnField);
        topPanel.add(searchButton);
        topPanel.add(allBooksButton);
        topPanel.add(averagePriceButton);
        topPanel.add(quitButton);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
        try {
            bookStore = (RMIInterface) Naming.lookup("rmi://localhost:9000/BK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Chercher")) {
            String isbn = isbnField.getText().trim();
            if (!isbn.isEmpty()) {
                try {
                    Book bookToFind = new Book("", isbn, 0.0);
                    Book book = bookStore.findBook(bookToFind);
                    if (book != null) {
                        resultArea.setText("Titre : " + book.getTitre() + "\n" + "Prix : " + book.getPrix());
                    } else {
                        resultArea.setText("Livre non trouvé.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                resultArea.setText("Veuillez entrer un ISBN.");
            }
        } else if (e.getActionCommand().equals("Afficher tous les livres")) {
            try {
                List<Book> books = bookStore.allBooks();
                StringBuilder sb = new StringBuilder();
                sb.append("Liste des livres :\n");
                for (Book book : books) {
                    sb.append("Titre : ").append(book.getTitre()).append(", Prix : ").append(book.getPrix()).append("\n");
                }
                resultArea.setText(sb.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getActionCommand().equals("Afficher prix moyen")) {
            try {
                double averagePrice = bookStore.getBooksPrice();
                resultArea.setText("Prix moyen des livres : " + averagePrice);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getActionCommand().equals("Quitter")) {
            int confirmed = JOptionPane.showConfirmDialog(null, 
                    "Êtes-vous sûr de vouloir quitter?", "Confirmation de fermeture",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientInterface());
    }
}
