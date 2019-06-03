package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.GenerateAdditionalCheckupsAction;

public class AdditionalCheckupsPanel extends JPanel {
	private TableHandler suggestedAdditionalCheckups;
	private TableHandler chosenAdditionalCheckups;
	private JComboBox<String> symptomsValue;
	
	public AdditionalCheckupsPanel() {
		this.suggestedAdditionalCheckups = new TableHandler(300, 300);
		this.chosenAdditionalCheckups = new TableHandler(300, 300);
		String[] symptoms = {"normal", "not normal"};
		this.symptomsValue = new JComboBox<>(symptoms);
		initGUI();
	}
	
	private void initGUI() {
		JPanel titlePanel = new JPanel();
		JPanel additionalPanel = new JPanel();
		JLabel additionalCheckupsLabel = new JLabel("Additional Checkups");
		JButton generateAdditionalCheckupsButton = new JButton(new GenerateAdditionalCheckupsAction());
		additionalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		initSuggestedAdditionalCheckupsTable();
		initChosenAdditionalCheckupsTable();
		additionalPanel.add(new JScrollPane(suggestedAdditionalCheckups.getTableView()));
		additionalPanel.add(new JScrollPane(chosenAdditionalCheckups.getTableView()));
		additionalCheckupsLabel.setFont(new Font(additionalCheckupsLabel.getFont().getFontName(), Font.BOLD,16));
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.add(additionalCheckupsLabel);
		titlePanel.add(generateAdditionalCheckupsButton);
		this.setLayout(new BorderLayout(15, 15));
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(additionalPanel, BorderLayout.WEST);
		this.add(new JPanel(), BorderLayout.CENTER);
	}
	
	private void initSuggestedAdditionalCheckupsTable() {
		this.suggestedAdditionalCheckups.addColumn("Additional Checkup");
		this.suggestedAdditionalCheckups.addColumn("Urgency");
		this.suggestedAdditionalCheckups.addColumn("Choose");
		// akcija na klik za brisanje brise iz tabele i dodaje u listu ponudjenih
		Action choose = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Object object = suggestedAdditionalCheckups.getTableModel().getValueAt(modelRow, 0);
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(suggestedAdditionalCheckups.getTableView(), choose, 2);
	}
	
	private void initChosenAdditionalCheckupsTable() {
		this.chosenAdditionalCheckups.addColumn("Symptom");
		this.chosenAdditionalCheckups.addColumn("Result");
		this.chosenAdditionalCheckups.addColumn("Remove");
		TableColumn tableCol = this.chosenAdditionalCheckups.getTableView().getColumnModel().getColumn(1);
		tableCol.setCellEditor(new DefaultCellEditor(symptomsValue));
		// akcija na klik za brisanje brise iz tabele i dodaje u listu ponudjenih
		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Object object = chosenAdditionalCheckups.getTableModel().getValueAt(modelRow, 0);
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(chosenAdditionalCheckups.getTableView(), delete, 2);

		
	}
	
	public void generateSuggestedAdditionalCheckups(Map<String, String> suggestedAdditionalCheckupsMap) {
		System.out.println(suggestedAdditionalCheckupsMap);
		suggestedAdditionalCheckups.clearTable();
		for (Map.Entry<String, String> entry : suggestedAdditionalCheckupsMap.entrySet()) {
			Vector v = new Vector();
			v.add(entry.getKey());
			v.add(entry.getValue());
			suggestedAdditionalCheckups.insertRow(v);
		}
	}
}
