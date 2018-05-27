/*2018-05-19 */

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class LogoFrame extends JFrame {	
	
	private static final long serialVersionUID = 1L;
	JLayeredPane contentPane;

	public LogoFrame(Icon logo) {
		contentPane = new JLayeredPane();
		JPanel logoPanel = new JPanel();
		JLabel logoLabel = new JLabel();
		if (logo != null) {
			logoLabel.setIcon(logo);
		} else {
			logoLabel.setText("no Icon available");
		}		
		logoPanel.add(logoLabel);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		this.getContentPane().add(logoPanel);
		this.getContentPane().add(contentPane);	
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
	}
	
	public void add(JPanel panel) {
		contentPane.add(panel);
	}
	
	public static void main(String[] args) {
		JFrame frame = new LogoFrame(null);
		JPanel panel = new JPanel();
		JLabel label = new JLabel("this is content panel.");
		panel.add(label);
		frame.add(panel);
		
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
