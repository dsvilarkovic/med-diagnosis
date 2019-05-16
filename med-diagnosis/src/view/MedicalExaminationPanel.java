package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.SystemComboListener;
import model.TableModel;
import prologproba.AssertPatientData;

public class MedicalExaminationPanel extends JPanel {

	private JPanel patientInformationPanel;

	@SuppressWarnings("rawtypes")
	private JComboBox symptomsComboBox;

	@SuppressWarnings("rawtypes")
	private Vector symptomsVector;

	private TableHandler chosenSympTableHandler;

	private TableHandler diagnosisTableHandler;

	private JTextField diagnosisTextField;

	@SuppressWarnings("rawtypes")
	public MedicalExaminationPanel() {
		this.chosenSympTableHandler = new TableHandler(300, 300);
		this.diagnosisTableHandler = new TableHandler(400, 300);
		this.symptomsVector = new Vector(AssertPatientData.getAllSymptoms());
		init();
		// kada se odabere opcija se brise iz liste ponudjenih i dodaje se novi red u
		// tabeli
		symptomsComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && symptomsComboBox.getSelectedIndex() != -1) {
					symptomsVector.remove(symptomsComboBox.getSelectedItem());
					Vector<Object> vector = new Vector<Object>();
					vector.add(symptomsComboBox.getSelectedItem());
					vector.add("Remove");
					chosenSympTableHandler.insertRow(vector);
				}
			}
		});

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() {
		patientInformationPanel = new PatientInformationPanel();
		JPanel contentPanel = new JPanel();
		JLabel medicalExaminationLabel = new JLabel("Medical examination information");

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

		JPanel anamnesisPanel = createAnamnesisPanel();
		JPanel diagnosisPanel = createDiagnosisPanel();
		JPanel physicalExamPanel = createPhysicalExaminationPanel();
		JPanel therapyPanel = createTherapyPanel();
		JPanel preventiveChecksPanel = createPreventiveChecksPanel();

		medicalExaminationLabel.setFont(new java.awt.Font("Times New Roman", 0, 24));

		this.setLayout(new BorderLayout());
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalStrut(35));
		contentPanel.add(medicalExaminationLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(anamnesisPanel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(new JSeparator());
		contentPanel.add(physicalExamPanel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(new JSeparator());
		contentPanel.add(diagnosisPanel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(new JSeparator());
		contentPanel.add(therapyPanel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(new JSeparator());
		contentPanel.add(preventiveChecksPanel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(new JSeparator());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientInformationPanel,
				new JScrollPane(contentPanel));
		this.add(splitPane, BorderLayout.CENTER);

		initDiagnTable();
	}
	
	// TODO 2: Zavrsiti panel za preventivne preglede
	private JPanel createPreventiveChecksPanel() {
		JPanel resultPanel = new JPanel();
		JPanel preventiveChecksPanel = new JPanel();
		JLabel preventiveChecksLabel = new JLabel("Preventive checks");
	
		
		resultPanel.setLayout(new BorderLayout(15,15));
		resultPanel.add(preventiveChecksLabel,BorderLayout.NORTH);
		resultPanel.add(preventiveChecksPanel, BorderLayout.CENTER);
		
		return resultPanel;
	}
	
	// TODO 1: Zavrsiti panel za terapije
	// Maja Kolosnjaji
	private JPanel createTherapyPanel() {
		JPanel resultPanel = new JPanel();
		JPanel therapyPanel = new JPanel();
		JLabel therapyLabel = new JLabel("Therapy");
		
	
		
		resultPanel.setLayout(new BorderLayout(15,15));
		resultPanel.add(therapyLabel,BorderLayout.NORTH);
		resultPanel.add(therapyPanel, BorderLayout.CENTER);
		
		return resultPanel;
	}

	// TODO 0: Zavrsiti panel za fizikalne preglede
	private JPanel createPhysicalExaminationPanel() {
		JPanel resultPanel = new JPanel();
		JPanel physicalExamPanel = new JPanel();
		JLabel physicalExaminationLabel = new JLabel("Physical examination");
	
		
		resultPanel.setLayout(new BorderLayout(15,15));
		resultPanel.add(physicalExaminationLabel,BorderLayout.NORTH);
		resultPanel.add(physicalExamPanel, BorderLayout.CENTER);
		
		return resultPanel;
	}

	private JPanel createDiagnosisPanel() {
		diagnosisTextField = new JTextField();

		JPanel diagnostisPanel = new JPanel();
		JPanel diagnosisResultsPanel = new JPanel();
		JPanel diagnosisResultTextPanel = new JPanel();
		JPanel emptyPanel = new JPanel();
		JLabel diagnosisLabel = new JLabel("Diagnosis");
		JButton getDiagnosisButton = new JButton("Generate Diagnosis");
		JLabel diagnosisSelected = new JLabel("Diagnosis is: ");
		getDiagnosisButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!chosenSympTableHandler.getContent().isEmpty()) {
					diagnosisTableHandler.insertContent(
							AssertPatientData.getDiseaseList(chosenSympTableHandler.getContent()), "Choose");
				}
			}
		});
		emptyPanel.setLayout(new BorderLayout());
		emptyPanel.add(getDiagnosisButton, BorderLayout.NORTH);
		emptyPanel.add(new JPanel(), BorderLayout.CENTER);

		diagnosisTextField.setPreferredSize(new Dimension(200, 30));
		diagnosisTextField.setMaximumSize(new Dimension(200, 30));
		getDiagnosisButton.setMaximumSize(new Dimension(200, 30));
		diagnosisResultTextPanel.setLayout(new BoxLayout(diagnosisResultTextPanel, BoxLayout.X_AXIS));
		diagnosisResultTextPanel.add(diagnosisSelected);
		diagnosisResultTextPanel.add(diagnosisTextField);

		diagnostisPanel.setLayout(new BorderLayout(15, 15));
		diagnostisPanel.add(diagnosisLabel, BorderLayout.NORTH);
		diagnostisPanel.add(emptyPanel, BorderLayout.WEST);
		diagnostisPanel.add(diagnosisResultTextPanel, BorderLayout.SOUTH);
		diagnosisResultsPanel.add(new JScrollPane(this.diagnosisTableHandler.getTableView()));
		diagnostisPanel.add(diagnosisResultsPanel, BorderLayout.CENTER);
		diagnostisPanel.setMaximumSize(new Dimension(900, 300));
		return diagnostisPanel;
	}

	private JPanel createAnamnesisPanel() {
		JPanel anamnesisPanel = new JPanel();
		JLabel anamesisLabel = new JLabel("Anamnesis");
		JPanel emptyPanel = new JPanel();

		emptyPanel.setLayout(new BorderLayout(15, 15));
		emptyPanel.add(symptomsComboBox, BorderLayout.NORTH);
		emptyPanel.add(new JPanel(), BorderLayout.CENTER);

		anamnesisPanel.setLayout(new BorderLayout(15, 15));
		anamnesisPanel.add(anamesisLabel, BorderLayout.NORTH);
		anamnesisPanel.add(new JScrollPane(this.chosenSympTableHandler.getTableView()), BorderLayout.CENTER);
		anamnesisPanel.add(emptyPanel, BorderLayout.WEST);
		anamnesisPanel.setMaximumSize(new Dimension(900, 300));

		initSympTable();
		return anamnesisPanel;
	}

	private void initSympTable() {
		this.chosenSympTableHandler.addColumn("Symptom");
		this.chosenSympTableHandler.addColumn("Remove");

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

		ButtonColumn buttonColumn = new ButtonColumn(chosenSympTableHandler.getTableView(), delete, 1);

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
