package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import cbr.connector.RdfConnector;
import model.MedicalExamination;
import model.MedicalRecord;
import utils.Singleton;

public class PatientRecordPanel extends JPanel {

	private PatientInformationPanel patientInformationPanel;
	private JLabel patientRecordNumberLabel;
	private TableHandler historyTableHandler;
	private MedicalRecord medicalRecord;

	PatientRecordPanel(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
		patientRecordNumberLabel = new JLabel(this.medicalRecord.getMedicalRecordNumber());
		patientInformationPanel = new PatientInformationPanel(medicalRecord);
		historyTableHandler = new TableHandler(300, 300);
		RdfConnector rdf = new RdfConnector();
		initComponents();
		setExaminations(rdf.getAllMedicalExaminationsByMedicalRecordId(this.medicalRecord.getId()));
	}
	
	private void initComponents() {
		JPanel patientHistoryPanel = new JPanel();
		JPanel contentPanel = new JPanel();
		JPanel textPanel = new JPanel();
		JLabel recordTextLabel = new JLabel("Patient record number: ");
		JLabel historyLabel = new JLabel("Patient history: ");
		JButton addNewAppointmentButton = new JButton("Add new appointment");
		addNewAppointmentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Singleton.getInstance().getMainFrame().setCentralPanel(new MedicalExaminationPanel(medicalRecord));
				
			}
		});
		recordTextLabel.setFont(new java.awt.Font(recordTextLabel.getFont().getFontName(), 0, 24)); 
        historyLabel.setFont(new java.awt.Font(historyLabel.getFont().getFontName(), 0, 18)); 
        
        textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        textPanel.add(recordTextLabel);
        textPanel.add(patientRecordNumberLabel);
		patientHistoryPanel.setLayout(new BorderLayout(5,5));
		patientHistoryPanel.add(historyLabel, BorderLayout.NORTH);
		initTable();
		patientHistoryPanel.add(new JScrollPane(historyTableHandler.getTableView()));
		patientHistoryPanel.add(addNewAppointmentButton, BorderLayout.SOUTH);
		contentPanel.setLayout(new BorderLayout());
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
	private void setExaminations(List<MedicalExamination> medicalExaminations) {
		for(MedicalExamination medicalExamination : medicalExaminations) {
			Vector<Object> vector = new Vector<Object>();
			vector.add(medicalExamination.getId());
			historyTableHandler.insertRow(vector);
		}
	}
	
	private void initTable() {
		historyTableHandler.addColumn("Id");
	}
	
}
