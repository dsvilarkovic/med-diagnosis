package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class PatientRecordPanel extends JPanel {

	PatientInformationPanel patientInformationPanel;
	private JPanel patientHistoryPanel;
	private JLabel patientRecordNumberLabel;
	private JTable jTable1;

	PatientRecordPanel() {
		initComponents();
	}

	private void initComponents() {
		patientRecordNumberLabel = new JLabel();
		patientHistoryPanel = new JPanel();
		patientInformationPanel = new PatientInformationPanel();
		jTable1 = new JTable();
		JLabel jLabel8 = new JLabel("Patient record number: ");
		JLabel jLabel9 = new JLabel("Patient history: ");
		JButton addNewAppointmentButton = new JButton("Add new appointment");
		jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 24)); 
        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); 
		jTable1.setModel(
				new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Date", "Doctor", "Status", "Edit" }));

		

	
		patientHistoryPanel.setLayout(new BoxLayout(patientHistoryPanel, BoxLayout.Y_AXIS));
		patientHistoryPanel.add(jLabel8);
		patientHistoryPanel.add(Box.createVerticalStrut(30));
		patientHistoryPanel.add(jLabel9);
		patientHistoryPanel.add(Box.createVerticalStrut(20));
		patientHistoryPanel.add(addNewAppointmentButton);
		this.setLayout(new BorderLayout());
		this.add(patientInformationPanel,BorderLayout.LINE_START);
		this.add(patientHistoryPanel, BorderLayout.CENTER);

	}
	
}
