package manager;
import javax.swing.JFrame;
import View.BoardPanel;

/**
 * Client Application -> Main
 * @author DuongHo
 * 
 * Main
 */
public class ClientMain {
	
	public static void main(String[] args) {
		ClientApp client = new ClientApp();
		client.setTitle("Checkers");
		client.pack();
		client.setVisible(true);
		client.setLocation(250, 0);
		client.setSize(900, 730);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
