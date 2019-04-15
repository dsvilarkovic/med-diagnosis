package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.SystemComboListener;
import model.TableModel;

public class MedicalExaminationPanel extends JPanel {

	private JPanel patientInformationPanel;
	
	@SuppressWarnings("rawtypes")
	private JComboBox symptomsComboBox;
	
	private List<String> symptomsList;
	
	@SuppressWarnings("rawtypes")
	private Vector symptomsVector;

	private TableModel tableModel;
	
	private JTable chosenSymptomsTable;
	
	@SuppressWarnings("rawtypes")
	public MedicalExaminationPanel() {
		this.symptomsVector = new Vector();
		this.symptomsList = new ArrayList<String>();
		this.symptomsList.add("clan");
		//problematicni deo koda
		//this.symptomsList = AssertPatientData.getAllSymptoms();
		init();
		// kada se odabere opcija se brise iz liste ponudjenih i dodaje se novi red u tabeli
		symptomsComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && symptomsComboBox.getSelectedIndex() != -1) {
					symptomsVector.remove(symptomsComboBox.getSelectedItem());
					Vector vector = new Vector();
					vector.add(symptomsComboBox.getSelectedItem());
					vector.add("Remove");
					tableModel.addRow(vector);

				}

			}
		});

		initSymptoms();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() {
		patientInformationPanel = new PatientInformationPanel();
		tableModel = new TableModel();
		chosenSymptomsTable = new JTable(tableModel);
		JPanel contentPanel = new JPanel();
		JPanel anamnesisPanel = new JPanel();
		JPanel symptomsPanel = new JPanel();
		JPanel emptyPanel = new JPanel();
		JLabel medicalExaminationLabel = new JLabel("Medical examination information");
		JLabel anamesisLabel = new JLabel("Anamnesis");
		JLabel physicalExaminationLabel = new JLabel("Physical examination");
		JLabel diagnosisLabel = new JLabel("Diagnosis");
		JLabel therapyLabel = new JLabel("Therapy");
		JLabel preventiveChecksLabel = new JLabel("Preventive checks");

		// podesavanje za combobox 
		symptomsComboBox = new JComboBox();
		symptomsComboBox.setModel(new DefaultComboBoxModel(symptomsVector));
		symptomsComboBox.setSelectedIndex(-1);
		symptomsComboBox.setEditable(true);
		JTextField text = (JTextField) symptomsComboBox.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.setText("");
		text.addKeyListener(new SystemComboListener(symptomsComboBox, symptomsVector));
		symptomsComboBox.setBounds(144, 56, 165, 24);
		
		
		emptyPanel.setLayout(new BorderLayout());
		emptyPanel.add(symptomsComboBox, BorderLayout.NORTH);
		emptyPanel.add(new JPanel(), BorderLayout.CENTER);
		
		symptomsPanel.setLayout(new BorderLayout());
		symptomsPanel.add(emptyPanel, BorderLayout.LINE_START);
		symptomsPanel.add(new JScrollPane(chosenSymptomsTable), BorderLayout.CENTER);

		anamnesisPanel.setLayout(new BorderLayout());
		anamnesisPanel.add(anamesisLabel, BorderLayout.NORTH);
		anamnesisPanel.add(symptomsPanel, BorderLayout.CENTER);
		anamnesisPanel.setMaximumSize(new Dimension(800, 300));

		medicalExaminationLabel.setFont(new java.awt.Font("Times New Roman", 0, 24));

		this.setLayout(new BorderLayout());
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalStrut(35));
		contentPanel.add(medicalExaminationLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(anamnesisPanel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(physicalExaminationLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(diagnosisLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(therapyLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(preventiveChecksLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientInformationPanel, contentPanel);
		this.add(splitPane, BorderLayout.CENTER);
		initTable();
	}

	private void initTable() {
		tableModel.addColumn("Symptom");
		tableModel.addColumn("Remove");
		chosenSymptomsTable.setPreferredScrollableViewportSize(new Dimension(300, 400));
		chosenSymptomsTable.setFillsViewportHeight(true);
		// akcija na klik za brisanje brise iz tabele i dodaje u listu ponudjenih
		Action delete = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        Object object = tableModel.getValueAt(modelRow, 0);
		        symptomsVector.add(object);
		        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
		    }
		};
		 
		ButtonColumn buttonColumn = new ButtonColumn(chosenSymptomsTable, delete, 1);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
	}

	@SuppressWarnings("unchecked")
	public void initSymptoms() {
		for (int i = 0; i < symptomsList.size(); i++) {
			symptomsVector.add(symptomsList.get(i));
		}

	}
}
