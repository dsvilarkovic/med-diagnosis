package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame {

	private MenuBar menuBar;
	private JPanel leftPanel;
	private JPanel centralPanel;
	
	public MainFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		this.setSize(800, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("Diagnostics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.menuBar = new MenuBar();
		this.setJMenuBar(menuBar);
		this.setLayout(new BorderLayout(10,10));
		
		this.leftPanel = new JPanel();
		this.centralPanel = new MedicalExaminationPanel();
		this.add(this.leftPanel,BorderLayout.WEST);
		this.add(this.centralPanel, BorderLayout.CENTER);
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
	}

	public JPanel getCentralPanel() {
		return centralPanel;
	}

	public void setCentralPanel(JPanel centralPanel) {
		this.centralPanel = centralPanel;
	}
}
