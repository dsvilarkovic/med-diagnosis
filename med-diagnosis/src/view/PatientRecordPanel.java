package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import cbr.connector.RdfConnector;
import controller.EditPatientRecordAction;
import model.MedicalExamination;
import model.MedicalRecord;
import utils.Singleton;

public class PatientRecordPanel extends JPanel {

	private PatientInformationPanel patientInformationPanel;
	private JLabel patientRecordNumberLabel;
	private TableHandler historyTableHandler;
	private MedicalRecord medicalRecord;
	private List<MedicalExamination> medicalExaminations;

	PatientRecordPanel(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
		patientRecordNumberLabel = new JLabel(this.medicalRecord.getMedicalRecordNumber());
		patientInformationPanel = new PatientInformationPanel(medicalRecord);
		historyTableHandler = new TableHandler(300, 300);
		RdfConnector rdf = new RdfConnector();
		this.medicalExaminations=rdf.getAllMedicalExaminationsByMedicalRecordId(this.medicalRecord.getId());
		initComponents();
		setExaminations();
	}

	private void initComponents() {
		JPanel patientHistoryPanel = new JPanel();
		JPanel contentPanel = new JPanel();
		JPanel textPanel = new JPanel();
		JLabel recordTextLabel = new JLabel("Patient record number: ");
		JLabel historyLabel = new JLabel("Patient history: ");
		JButton addNewAppointmentButton = new JButton("Add new appointment");
		JButton editRecord = new JButton(new EditPatientRecordAction(this.medicalRecord));
		editRecord.setBackground(new Color(64,128,243));
		editRecord.setBorderPainted(true);
		addNewAppointmentButton.setBackground(new Color(64,128,243));
		addNewAppointmentButton.setBorderPainted(true);
		addNewAppointmentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Singleton.getInstance().getMainFrame().setCentralPanel(new MedicalExaminationPanel(medicalRecord));
				
			}
		});
		recordTextLabel.setFont(new java.awt.Font(recordTextLabel.getFont().getFontName(), 0, 24)); 
        historyLabel.setFont(new java.awt.Font(historyLabel.getFont().getFontName(), 0, 18)); 
        patientRecordNumberLabel.setFont(new java.awt.Font(patientRecordNumberLabel.getFont().getFontName(), 0, 24));
        
        
        
        textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        textPanel.add(recordTextLabel);
        textPanel.add(patientRecordNumberLabel);
        textPanel.add(Box.createHorizontalStrut(5));
        textPanel.add(editRecord);
		patientHistoryPanel.setLayout(new BorderLayout(5,5));
		patientHistoryPanel.add(historyLabel, BorderLayout.NORTH);
		initTable();
		patientHistoryPanel.add(new JScrollPane(historyTableHandler.getTableView()));
		patientHistoryPanel.add(addNewAppointmentButton, BorderLayout.SOUTH);
		contentPanel.setLayout(new BorderLayout(5,10));
		contentPanel.add(textPanel, BorderLayout.NORTH);
		contentPanel.add(patientHistoryPanel, BorderLayout.CENTER);
		JPanel margin = new JPanel();
		margin.setLayout(new BorderLayout(5,5));
		margin.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
		margin.add(contentPanel,BorderLayout.CENTER);
		margin.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
		
		this.setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientInformationPanel,
				new JScrollPane(margin));
		this.add(splitPane);

	}
	private void setExaminations() {
		for (int i = 0; i < medicalExaminations.size(); i++)
		{
			Vector<Object> vector = new Vector<Object>();
			vector.add(medicalExaminations.get(i).getId());
			vector.add("show");
			historyTableHandler.insertRow(vector);
		}
	}
	
	private void initTable() {
		historyTableHandler.addColumn("Id");
		historyTableHandler.addColumn("Details");
		
		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Vector<Object> v = historyTableHandler.getTableModel().getRow(modelRow);
				JOptionPane.showMessageDialog(null,new MedicalExaminationInfo(medicalExaminations.get(modelRow)),"Information",JOptionPane.INFORMATION_MESSAGE);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(historyTableHandler.getTableView(), delete, 1);
	}
	
}
