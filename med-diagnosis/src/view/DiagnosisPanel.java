package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.GenerateDiagnosisAction;

public class DiagnosisPanel extends JPanel  {
	private TableHandler diagnosisTableHandler;
	private JTextField diagnosisTextField;

	public DiagnosisPanel() {
		this.diagnosisTableHandler = new TableHandler(400, 300);
		this.diagnosisTextField = new JTextField();
		initGUI();
	}
	
	private void initGUI() {
		JPanel diagnosisResultTextPanel = new JPanel();
		JPanel diagnosisTextPanel = new JPanel();
		JPanel emptyPanel = new JPanel();
		JLabel diagnosisLabel = new JLabel("Diagnosis");
		JButton getDiagnosisButton = new JButton(new GenerateDiagnosisAction());
		JLabel diagnosisSelected = new JLabel("Diagnosis is: ");
		diagnosisLabel.setFont(new Font(diagnosisLabel.getFont().getFontName(), Font.BOLD,16));
		diagnosisTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		diagnosisTextPanel.add(diagnosisLabel);
		diagnosisTextPanel.add(getDiagnosisButton);
		emptyPanel.setLayout(new BorderLayout());
		emptyPanel.add(new JPanel(), BorderLayout.CENTER);

		diagnosisTextField.setPreferredSize(new Dimension(200, 30));
		diagnosisTextField.setMaximumSize(new Dimension(200, 30));
		diagnosisResultTextPanel.setLayout(new BoxLayout(diagnosisResultTextPanel, BoxLayout.X_AXIS));
		diagnosisResultTextPanel.add(diagnosisSelected);
		diagnosisResultTextPanel.add(diagnosisTextField);
		initDiagnTable();
		this.setLayout(new BorderLayout(5, 5));
		this.add(diagnosisTextPanel, BorderLayout.NORTH);
		this.add(emptyPanel, BorderLayout.WEST);
		this.add(diagnosisResultTextPanel, BorderLayout.SOUTH);
		this.add(new JScrollPane(this.diagnosisTableHandler.getTableView()), BorderLayout.CENTER);
	}
	private void initDiagnTable() {
		this.diagnosisTableHandler.addColumn("Diagnosis");
		this.diagnosisTableHandler.addColumn("Choose");
		Action choose = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				if (modelRow != -1) {
					Object object = diagnosisTableHandler.getTableModel().getValueAt(modelRow, 0);
					diagnosisTextField.setText((String) object);
				}
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(diagnosisTableHandler.getTableView(), choose, 1);
		buttonColumn.setMnemonic(KeyEvent.VK_C);
	}
}
