package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.CheckedOutputStream;

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

public class DiagnosisPanel extends JPanel {
	private TableHandler diagnosisTableHandler;
	private JTextField diagnosisTextField;

	public DiagnosisPanel() {
		this.diagnosisTableHandler = new TableHandler(600, 300);
		this.diagnosisTextField = new JTextField();
		initGUI();
	}

	private void initGUI() {
		JPanel diagnosisResultTextPanel = new JPanel();
		JPanel diagnosisTextPanel = new JPanel();
		JLabel diagnosisLabel = new JLabel("Diagnosis");
		JButton getDiagnosisButton = new JButton(new GenerateDiagnosisAction());
		getDiagnosisButton.setBackground(new Color(64,128,243));
		JLabel diagnosisSelected = new JLabel("Diagnosis is: ");

		diagnosisLabel.setFont(new Font(diagnosisLabel.getFont().getFontName(), Font.BOLD, 18));
		diagnosisTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		diagnosisTextPanel.add(diagnosisLabel);
		diagnosisTextPanel.add(getDiagnosisButton);

		diagnosisTextField.setPreferredSize(new Dimension(200, 30));
		diagnosisTextField.setMaximumSize(new Dimension(200, 30));
		diagnosisResultTextPanel.setLayout(new BoxLayout(diagnosisResultTextPanel, BoxLayout.X_AXIS));
		diagnosisResultTextPanel.add(diagnosisSelected);
		diagnosisResultTextPanel.add(diagnosisTextField);
		initDiagnTable();
		this.setLayout(new BorderLayout(5, 5));
		this.add(diagnosisTextPanel, BorderLayout.NORTH);
		this.add(diagnosisResultTextPanel, BorderLayout.SOUTH);
		this.add(new JScrollPane(this.diagnosisTableHandler.getTableView()), BorderLayout.CENTER);
	}

	private void initDiagnTable() {
		this.diagnosisTableHandler.addColumn("Diagnosis");
		this.diagnosisTableHandler.addColumn("Percentage");
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

		ButtonColumn buttonColumn = new ButtonColumn(diagnosisTableHandler.getTableView(), choose, 2);
	}

	public void generateDiagnosis(Map<String, Float> diagnosis) {
		this.diagnosisTableHandler.clearTable();
		this.diagnosisTextField.setText("");
		for (Map.Entry<String, Float> entry : diagnosis.entrySet()) {
			Vector<Object> v = new Vector<Object>();
			v.add(entry.getKey());
			v.add(entry.getValue());
			v.add("choose");
			diagnosisTableHandler.insertRow(v);
		}
	}
	
	public String getDiagnose() {
		return diagnosisTextField.getText().trim();
	}

}
