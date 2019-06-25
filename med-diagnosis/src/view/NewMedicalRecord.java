package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.SavePatientRecord;
import model.Allergy;
import model.MedicalRecord;

public class NewMedicalRecord extends JPanel {
	private boolean edit;
	private MedicalRecord newRecord;
	String[] allergies = { "nsaid", "penicillin", "antibiotic", "antiepileptic_drug", "sulfonamides" };
	private List<String> allAllergiesList;
	private TableHandler allAllergiesTableHandler;
	private TableHandler chosenAllergiesTableHandler;
	private JTextField nameTextField;
	private JTextField lastNameTextField;
	private JTextField ageTextField;
	private JTextField JMBGTextField;
	private JTextField phoneNumberTextField;
	private JTextField addressTextField;
	private JTextField medicalRecordNumField;
	private JRadioButton femaleButton;
	private JRadioButton maleButton;

	public NewMedicalRecord() {
		this.edit = false;
		this.newRecord = new MedicalRecord();
		allAllergiesList = new ArrayList<String>(Arrays.asList(allergies));
		this.newRecord.setAllergies(new HashSet<Allergy>());
		this.nameTextField = new JTextField();
		this.lastNameTextField = new JTextField();
		this.ageTextField = new JTextField();
		this.JMBGTextField = new JTextField();
		this.phoneNumberTextField = new JTextField();
		this.addressTextField = new JTextField();
		this.medicalRecordNumField = new JTextField();
		this.femaleButton = new JRadioButton("Female");
		this.maleButton = new JRadioButton("Male");
		this.allAllergiesTableHandler = new TableHandler(600, 300);
		this.chosenAllergiesTableHandler = new TableHandler(600, 300);
		initGui();

		this.allAllergiesTableHandler.insertContent(this.allAllergiesList, "choose");
	}

	public NewMedicalRecord(MedicalRecord m) {
		this.edit=true;
		this.newRecord = m;
		System.out.println(this.newRecord.getAllergies());
		allAllergiesList = new ArrayList<String>(Arrays.asList(allergies));
		this.nameTextField = new JTextField(newRecord.getFirstName());
		this.lastNameTextField = new JTextField(newRecord.getLastName());
		this.ageTextField = new JTextField(String.valueOf(this.newRecord.getAge()));
		this.femaleButton = new JRadioButton("Female");
		this.maleButton = new JRadioButton("Male");
		this.femaleButton.setSelected(newRecord.isFemale());
		this.maleButton.setSelected(!newRecord.isFemale());
		this.JMBGTextField = new JTextField(newRecord.getJmbg());
		this.phoneNumberTextField = new JTextField(newRecord.getPhoneNumber());
		this.addressTextField = new JTextField(newRecord.getAddress());
		this.medicalRecordNumField = new JTextField(newRecord.getMedicalRecordNumber());
		this.allAllergiesTableHandler = new TableHandler(600, 300);
		this.chosenAllergiesTableHandler = new TableHandler(600, 300);
		initGui();
		List<String> chosen = new ArrayList<String>();

		for (Allergy allergy : this.newRecord.getAllergies()) {
			if (this.allAllergiesList.contains(allergy.getName())) {
				chosen.add(allergy.getName());
				this.allAllergiesList.remove(allergy.getName());
			}
		}
		this.allAllergiesTableHandler.insertContent(this.allAllergiesList, "choose");
		this.chosenAllergiesTableHandler.insertContent(chosen, "remove");

	}

	private void initGui() {
		
		JPanel contentPanel = new JPanel();
		JButton saveButton = new JButton(new SavePatientRecord(this.newRecord));

		JPanel patientInfoPanel = createPatentInformationPanel();
		JPanel allergiesPanel = createAllergiesPanel();

		initAllAlergiesTable();
		initChosenAllergiesTable();

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(contentPanel.add(Box.createVerticalStrut(35)));
		contentPanel.add(patientInfoPanel);
		contentPanel.add(contentPanel.add(Box.createVerticalStrut(15)));
		contentPanel.add(new JSeparator());
		contentPanel.add(allergiesPanel);
		saveButton.setBackground(new Color(64,128,243));
		JPanel all = new JPanel();
		all.setLayout(new BorderLayout(5, 5));
		all.add(contentPanel, BorderLayout.NORTH);
		all.add(saveButton, BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(all,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
	}

	private JPanel createPatentInformationPanel() {
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BorderLayout(0, 5));
		JLabel patientInformationLabel = new JLabel("Patient information");
		JPanel informationContentPanel = new JPanel();
		informationContentPanel.setLayout(new GridLayout(4, 2, 5, 5));
		patientInformationLabel.setFont(new Font(patientInformationLabel.getFont().getFontName(), Font.BOLD, 18));
		JLabel medicalRecordNumberText = new JLabel("Medical record number: ");
		JLabel nameTextLabel = new JLabel("Name: ");
		JLabel lastNameTextLabel = new JLabel("Last name: ");
		JLabel ageTextLabel = new JLabel("Age: ");
		JLabel genderLabelText = new JLabel("Gender: ");
		JLabel JMBGTextLabel = new JLabel("JMBG: ");
		JLabel phoneNumberTextLabel = new JLabel("Phone number: ");
		JLabel addressTextLabel = new JLabel("Address: ");

		this.nameTextField.setPreferredSize(new Dimension(200, 30));
		this.lastNameTextField.setPreferredSize(new Dimension(200, 30));
		this.ageTextField.setPreferredSize(new Dimension(200, 30));
		this.JMBGTextField.setPreferredSize(new Dimension(200, 30));
		this.phoneNumberTextField.setPreferredSize(new Dimension(200, 30));
		this.addressTextField.setPreferredSize(new Dimension(200, 30));

		ButtonGroup group = new ButtonGroup();
		group.add(this.femaleButton);
		group.add(this.maleButton);
		group.setSelected(this.femaleButton.getModel(), true);
		
		JPanel genderPanel = new JPanel();
		genderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		genderPanel.add(this.femaleButton);
		genderPanel.add(this.maleButton);
		informationContentPanel.add(JMBGTextLabel);
		informationContentPanel.add(this.JMBGTextField);
		informationContentPanel.add(medicalRecordNumberText);
		informationContentPanel.add(this.medicalRecordNumField);
		informationContentPanel.add(nameTextLabel);
		informationContentPanel.add(this.nameTextField);
		informationContentPanel.add(lastNameTextLabel);
		informationContentPanel.add(this.lastNameTextField);
		informationContentPanel.add(ageTextLabel);
		informationContentPanel.add(this.ageTextField);
		informationContentPanel.add(genderLabelText);
		informationContentPanel.add(genderPanel);
		informationContentPanel.add(phoneNumberTextLabel);
		informationContentPanel.add(this.phoneNumberTextField);
		informationContentPanel.add(addressTextLabel);
		informationContentPanel.add(this.addressTextField);

		resultPanel.add(patientInformationLabel, BorderLayout.NORTH);
		resultPanel.add(informationContentPanel, BorderLayout.CENTER);
		return resultPanel;
	}

	public JPanel createAllergiesPanel() {
		JPanel resultPanel = new JPanel();
		JPanel allergiesContentPanel = new JPanel();
		JLabel allegriesLabel = new JLabel("Patient allergies");
		allegriesLabel.setFont(new Font(allegriesLabel.getFont().getFontName(), Font.BOLD, 18));
		resultPanel.setLayout(new BorderLayout(0, 5));
		allergiesContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		allergiesContentPanel.add(new JScrollPane(this.allAllergiesTableHandler.getTableView()));
		allergiesContentPanel.add(new JScrollPane(this.chosenAllergiesTableHandler.getTableView()));
		resultPanel.add(allegriesLabel, BorderLayout.NORTH);
		resultPanel.add(allergiesContentPanel, BorderLayout.CENTER);
		return resultPanel;
	}

	private void initAllAlergiesTable() {
		this.allAllergiesTableHandler.addColumn("Allergy");
		this.allAllergiesTableHandler.addColumn("Choose");
		// akcija na klik za brisanje brise iz tabele i dodaje u listu ponudjenih
		Action choose = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Vector<Object> v = allAllergiesTableHandler.getTableModel().getRow(modelRow);
				v.set(1, "remove");
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
				chosenAllergiesTableHandler.insertRow(v);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(allAllergiesTableHandler.getTableView(), choose, 1);
	}

	private void initChosenAllergiesTable() {
		this.chosenAllergiesTableHandler.addColumn("Allergy");
		this.chosenAllergiesTableHandler.addColumn("Remove");
		// akcija na klik za brisanje brise iz tabele i dodaje u listu ponudjenih
		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Vector<Object> v = chosenAllergiesTableHandler.getTableModel().getRow(modelRow);
				v.set(1, "choose");
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
				allAllergiesTableHandler.insertRow(v);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(chosenAllergiesTableHandler.getTableView(), delete, 1);

	}

	public MedicalRecord getMedicalRecord() {
		this.newRecord.setFirstName(this.nameTextField.getText());
		this.newRecord.setLastName(this.lastNameTextField.getText());
		this.newRecord.setAddress(this.addressTextField.getText());
		this.newRecord.setJmbg(this.JMBGTextField.getText());
		this.newRecord.setMedicalRecordNumber(this.medicalRecordNumField.getText());
		this.newRecord.setPhoneNumber(this.phoneNumberTextField.getText());
		this.newRecord.setFemale(this.femaleButton.isSelected());
		this.newRecord.setYearOfBirth(
				Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(this.ageTextField.getText()));
		this.newRecord.getAllergies().clear();
		for (String allergy : this.chosenAllergiesTableHandler.getContentList()) {
			Allergy a = new Allergy();
			a.setName(allergy);
			this.newRecord.getAllergies().add(a);
		}
		System.out.println(this.newRecord.getAllergies());
		return this.newRecord;

	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

}
