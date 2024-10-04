package mini_projet;


//Importation_des_bibliothèques
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServeurRMI {
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(9000);
			BookStore od= new BookStore();
			System.out.println(od.toString());
			Naming.rebind("rmi://localhost:9000/BK",od);
			System.out.println("The server is runnig...");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}