package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.SystemComboListener;
import prolog.AssertPatientData;

/**
 * Panel za unos anamneze pacijenta
 * 
 * @author Vesna Milic
 */
public class AnamnesisPanel extends JPanel {

	private JComboBox<String> symptomsComboBox;

	private JComboBox<Integer> symptomsValue;

	private Vector<Object> symptomsVector;

	private TableHandler chosenSympTableHandler;

	public AnamnesisPanel() {
		this.chosenSympTableHandler = new TableHandler(300, 300);
		this.symptomsVector = new Vector<Object>(AssertPatientData.getAllSymptoms());
		Integer[] symValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		symptomsValue = new JComboBox<Integer>(symValues);
		init();
		// dodavanje u tabelu reda iz comboboxa
		symptomsComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && symptomsComboBox.getSelectedIndex() != -1) {
					symptomsVector.remove(symptomsComboBox.getSelectedItem());
					Vector<Object> vector = new Vector<Object>();
					vector.add(symptomsComboBox.getSelectedItem());
					vector.add(1);
					vector.add("Remove");
					chosenSympTableHandler.insertRow(vector);
				}
			}
		});
	}

	/**
	 * Metoda za inicijalizaciju GUI komponenti panela anamneze
	 */
	private void init() {
		symptomsComboBox = new JComboBox<String>();
		JLabel anamesisLabel = new JLabel("Anamnesis");
		
		JPanel emptyPanel = new JPanel();

		emptyPanel.setLayout(new BorderLayout(15, 15));
		emptyPanel.add(symptomsComboBox, BorderLayout.NORTH);
		emptyPanel.add(new JPanel(), BorderLayout.CENTER);

		// podesavanja comboBoxa
		symptomsComboBox.setModel(new DefaultComboBoxModel(symptomsVector));
		symptomsComboBox.setSelectedIndex(-1);
		symptomsComboBox.setEditable(true);
		JTextField text = (JTextField) symptomsComboBox.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.setText("");
		text.addKeyListener(new SystemComboListener(symptomsComboBox, symptomsVector));

		symptomsComboBox.setBounds(144, 56, 165, 24);
		anamesisLabel.setFont(new Font(anamesisLabel.getFont().getFontName(), Font.BOLD,18));
		this.setLayout(new BorderLayout(15, 15));
		this.add(anamesisLabel, BorderLayout.NORTH);
		this.add(new JScrollPane(this.chosenSympTableHandler.getTableView()), BorderLayout.CENTER);
		this.add(emptyPanel, BorderLayout.WEST);
		initSympTable();
	}

	/**
	 * Metoda za inicijalizaciju tabele simbola
	 */
	private void initSympTable() {
		// Postavljanje comboBoxa za drugu kolonu

		this.chosenSympTableHandler.addColumn("Symptom");
		this.chosenSympTableHandler.addColumn("Value");
		this.chosenSympTableHandler.addColumn("Remove");
		TableColumn tableCol = this.chosenSympTableHandler.getTableView().getColumnModel().getColumn(1);
		tableCol.setCellEditor(new DefaultCellEditor(symptomsValue));
		// akcija na klik za brisanje brise iz tabele i dodaje u listu ponudjenih
		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Object object = chosenSympTableHandler.getTableModel().getValueAt(modelRow, 0);
				symptomsVector.add(object);
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(chosenSympTableHandler.getTableView(), delete, 2);

	}

	/**
	 * Metoda za preduzimanje odabranih simptoma
	 * 
	 * @return mapa sa nazivima simptoma kao kljucevima i vrednostima jacine simptoma
	 * @see Map
	 */
	public Map<String, Integer> getChosenSympMap() {
		return chosenSympTableHandler.getContentMap();
	}

	/**
	 * Metoda za preuzimanje odabranih simptoma u obliku liste
	 * 
	 * @return listu naziva odabranih simptoma
	 * @see List
	 */ 
	public List<String> getChosenSympList() {
		return chosenSympTableHandler.getContentList();
	}
}
