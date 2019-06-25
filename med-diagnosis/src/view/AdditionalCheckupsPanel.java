package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
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

import controller.FuzzyOpenGraphVisualizer;
import controller.GenerateAdditionalCheckupsAction;
import utils.Regime;
import utils.Singleton;

public class AdditionalCheckupsPanel extends JPanel {
	private TableHandler suggestedAdditionalCheckups;
	private TableHandler chosenAdditionalCheckups;
	private JComboBox<String> symptomsValue;
	
	public AdditionalCheckupsPanel() {
		this.suggestedAdditionalCheckups = new TableHandler(600, 300);
		this.chosenAdditionalCheckups = new TableHandler(600, 300);
		String[] symptoms = {"normal", "not normal"};
		this.symptomsValue = new JComboBox<>(symptoms);
		initGUI();
	}
	
	private void initGUI() {
		JPanel titlePanel = new JPanel();
		JPanel additionalPanel = new JPanel();
		JLabel additionalCheckupsLabel = new JLabel("Additional Checkups");
		JButton generateAdditionalCheckupsButton = new JButton(new GenerateAdditionalCheckupsAction());
		JButton showVisualExplButton = new JButton(new FuzzyOpenGraphVisualizer());
		generateAdditionalCheckupsButton.setBackground(new Color(64,128,243));
		showVisualExplButton.setBackground(new Color(64,128,243));
		additionalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		initSuggestedAdditionalCheckupsTable();
		initChosenAdditionalCheckupsTable();
		additionalPanel.add(new JScrollPane(suggestedAdditionalCheckups.getTableView()));
		additionalPanel.add(new JScrollPane(chosenAdditionalCheckups.getTableView()));
		additionalCheckupsLabel.setFont(new Font(additionalCheckupsLabel.getFont().getFontName(), Font.BOLD,18));
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.add(additionalCheckupsLabel);
		titlePanel.add(generateAdditionalCheckupsButton);
		
		if(Singleton.getInstance().getRegime() == Regime.RULE_BASED) {
			titlePanel.add(showVisualExplButton);
		}
		
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
				Vector<Object> v = suggestedAdditionalCheckups.getTableModel().getRow(modelRow);
				v.set(2, "remove");
				v.insertElementAt("normal", 2);
				
				
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
				chosenAdditionalCheckups.insertRow(v);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(suggestedAdditionalCheckups.getTableView(), choose, 2);
	}
	
	private void initChosenAdditionalCheckupsTable() {
		this.chosenAdditionalCheckups.addColumn("Symptom");
		this.chosenAdditionalCheckups.addColumn("Urgency");
		this.chosenAdditionalCheckups.addColumn("Result");
		this.chosenAdditionalCheckups.addColumn("Remove");
		TableColumn tableCol = this.chosenAdditionalCheckups.getTableView().getColumnModel().getColumn(2);
		tableCol.setCellEditor(new DefaultCellEditor(symptomsValue));
		// akcija na klik za brisanje brise iz tabele i dodaje u listu ponudjenih
		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Vector<Object> v = chosenAdditionalCheckups.getTableModel().getRow(modelRow);
				v.remove(2);
				v.set(2, "choose");
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
				suggestedAdditionalCheckups.insertRow(v);
			}
		};
		
		ButtonColumn buttonColumn = new ButtonColumn(chosenAdditionalCheckups.getTableView(), delete, 3);

		
	}
	
	public void generateSuggestedAdditionalCheckups(Map<String, String> suggestedAdditionalCheckupsMap) {
		System.out.println(suggestedAdditionalCheckupsMap);
		suggestedAdditionalCheckups.clearTable();
		chosenAdditionalCheckups.clearTable();
		for (Map.Entry<String, String> entry : suggestedAdditionalCheckupsMap.entrySet()) {
			Vector<Object> v = new Vector<Object>();
			v.add(entry.getKey());
			v.add(entry.getValue());
			v.add("choose");
			suggestedAdditionalCheckups.insertRow(v);
		}
	}
	
	public List<String> getAdditionalCheckupsList() {
		List<String> resultList = new ArrayList<String>();
		
		for(int i=0; i< chosenAdditionalCheckups.getTableModel().getRowCount();i++) {
			Vector v = chosenAdditionalCheckups.getTableModel().getRow(i);
			if(v.get(3).equals("not normal")) {
				resultList.add((String)v.get(0));
			}
		}
		
		
		return resultList;
	}
	
	public List<String> getAllAdditionalCheckupsList() {
		List<String> resultList = new ArrayList<String>();
		
		for(int i=0; i< chosenAdditionalCheckups.getTableModel().getRowCount();i++) {
			Vector v = chosenAdditionalCheckups.getTableModel().getRow(i);
			if(v.get(3).equals("not normal")) {
				resultList.add((String)v.get(0) + "_not_good");
			}else {
				resultList.add((String)v.get(0) + "_good");
			}
		}
		
		
		return resultList;
	}
}
