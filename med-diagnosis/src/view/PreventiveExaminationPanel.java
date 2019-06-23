package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.GeneratePreventiveExaminationAction;

public class PreventiveExaminationPanel extends JPanel{
	
	private TableHandler suggestedPreventiveExaminations;
	private TableHandler chosenPreventiveExaminations;

	public PreventiveExaminationPanel() {
		this.suggestedPreventiveExaminations = new TableHandler(600, 300);
		this.chosenPreventiveExaminations = new TableHandler(600, 300);
		
		init();
	}
	
		/**
	 * Metoda za inicijalizaciju GUI komponenti panela za preventivne preglede
	 */
	private void init() {
		initSuggestedPreventiveExaminations();
		initChosenPreventiveExaminations();
		
		//naslov + dugme za generisanje
		JPanel preventiveTextPanel = new JPanel();
		
		JLabel preventiveLabel = new JLabel("Preventive examinations");
		preventiveLabel.setFont(new Font(preventiveLabel.getFont().getFontName(), Font.BOLD,18));
		
		JButton getPreventiveExaminationsButton = new JButton(new GeneratePreventiveExaminationAction());
				
		preventiveTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		preventiveTextPanel.add(preventiveLabel);
		preventiveTextPanel.add(getPreventiveExaminationsButton);
		
		//tabele sa predlozenim i sa izabranim terapijama
		JPanel preventiveExaminationPanel = new JPanel();
		
		preventiveExaminationPanel.add(new JScrollPane(suggestedPreventiveExaminations.getTableView()));
		preventiveExaminationPanel.add(new JScrollPane(chosenPreventiveExaminations.getTableView()));
		
		this.setLayout(new BorderLayout(15, 15));
		this.add(preventiveTextPanel, BorderLayout.NORTH);
		this.add(preventiveExaminationPanel, BorderLayout.WEST);
		
	}

	private void initSuggestedPreventiveExaminations() {
		this.suggestedPreventiveExaminations.addColumn("Preventive examinations");
		this.suggestedPreventiveExaminations.addColumn("");
		
		// akcija na klik prebacuje iz tabele sa ponudjenim terapijama u tabelu sa izabranim terapijama
		Action choose = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Vector<Object> v = suggestedPreventiveExaminations.getTableModel().getRow(modelRow);
				v.set(1, "Remove");
					
					
					((DefaultTableModel) table.getModel()).removeRow(modelRow);
					chosenPreventiveExaminations.insertRow(v);
			}
		};
	
		ButtonColumn buttonColumn = new ButtonColumn(suggestedPreventiveExaminations.getTableView(), choose, 1);
	}

	private void initChosenPreventiveExaminations() {
		this.chosenPreventiveExaminations.addColumn("Preventive examinations");
		this.chosenPreventiveExaminations.addColumn("");
		
		// prebacuje iz tabele sa izabranim pregledima u tabelu sa ponudjenim pregledima
		Action choose = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				
				Vector<Object> v = chosenPreventiveExaminations.getTableModel().getRow(modelRow);
				v.set(1, "Choose");
					
					((DefaultTableModel) table.getModel()).removeRow(modelRow);
					suggestedPreventiveExaminations.insertRow(v);
				}
		};
	
		ButtonColumn buttonColumn = new ButtonColumn(chosenPreventiveExaminations.getTableView(), choose, 1);
	
	}

	public void generateSuggestedPreventiveExaminations(List<String> suggestedPreventiveExList) {
		suggestedPreventiveExaminations.clearTable();
		chosenPreventiveExaminations.clearTable();
		
		for (String examination : suggestedPreventiveExList) {
			Vector v = new Vector();
			v.add(examination);
			v.add("Choose");
					suggestedPreventiveExaminations.insertRow(v);
				}
	}
}


