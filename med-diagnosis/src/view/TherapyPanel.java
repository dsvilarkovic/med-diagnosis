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

import controller.GenerateTherapyAction;

public class TherapyPanel extends JPanel {
	private TableHandler suggestedTherapies;
	private TableHandler chosenTherapies;

	public TherapyPanel() {
		this.suggestedTherapies = new TableHandler(300, 300);
		this.chosenTherapies = new TableHandler(300, 300);
		
		init();
	}
	
	/**
	 * Metoda za inicijalizaciju GUI komponenti panela za terapije
	 */
	private void init() {
		initSuggestedTherapies();
		initChosenTherapies();
		
		//naslov + dugme za generisanje
		JPanel therapyTextPanel = new JPanel();

		JLabel therapyLabel = new JLabel("Therapy");
		therapyLabel.setFont(new Font(therapyLabel.getFont().getFontName(), Font.BOLD,16));
		
		JButton getTherapyButton = new JButton(new GenerateTherapyAction());
				
		therapyTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		therapyTextPanel.add(therapyLabel);
		therapyTextPanel.add(getTherapyButton);
		
		//tabele sa predlozenim i sa izabranim terapijama
		JPanel therapyPanel = new JPanel();
		
		therapyPanel.add(new JScrollPane(suggestedTherapies.getTableView()));
		therapyPanel.add(new JScrollPane(chosenTherapies.getTableView()));
		
		this.setLayout(new BorderLayout(15, 15));
		this.add(therapyTextPanel, BorderLayout.NORTH);
		this.add(therapyPanel, BorderLayout.WEST);
		
	}

	private void initSuggestedTherapies() {
		this.suggestedTherapies.addColumn("Therapies");
		this.suggestedTherapies.addColumn("");
		
		// akcija na klik prebacuje iz tabele sa ponudjenim terapijama u tabelu sa izabranim terapijama
		Action choose = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Vector<Object> v = suggestedTherapies.getTableModel().getRow(modelRow);
				v.set(1, "Remove");
				
				
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
				chosenTherapies.insertRow(v);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(suggestedTherapies.getTableView(), choose, 1);
	}
	
	private void initChosenTherapies() {
		this.chosenTherapies.addColumn("Therapies");
		this.chosenTherapies.addColumn("");

		// prebacuje iz tabele sa izabranim terapijama u tabelu sa ponudjenim terapijama
		Action choose = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				
				Vector<Object> v = chosenTherapies.getTableModel().getRow(modelRow);
				v.remove(2);
				v.set(2, "Choose");
				
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
				suggestedTherapies.insertRow(v);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(chosenTherapies.getTableView(), choose, 1);

	}
	
	public void generateSuggestedTherapies(List<String> suggestedTherapiesList) {
		suggestedTherapies.clearTable();
		chosenTherapies.clearTable();
		
		for (String therapy : suggestedTherapiesList) {
			Vector v = new Vector();
			v.add(therapy);
			v.add("Choose");
			suggestedTherapies.insertRow(v);
		}
	}
}
