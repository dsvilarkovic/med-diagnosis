package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cbr.connector.RdfConnector;
import model.MedicalRecord;
import utils.Singleton;

public class SearchRecordPanel extends JPanel {

	private JTextField recordNumberTextField;

	private TableHandler recordsTableHandler;

	private RdfConnector rdf;

	public SearchRecordPanel() {
		recordNumberTextField = new JTextField();
		recordsTableHandler = new TableHandler(600, 300);
		initRecordTable();
		rdf = new RdfConnector();
		List<MedicalRecord> medicalRecords = rdf.getAllMedicalRecords();
		System.out.println(medicalRecords);
		initData(medicalRecords);
		initGUI();
	}

	private void initGUI() {
		JLabel recordNumberLabel = new JLabel();
		JButton jButton1 = new JButton("Search");
		jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String, String> map = new HashMap<>();
				map.put("Number", recordNumberTextField.getText());
				recordsTableHandler.addSearchFilter(map);
				

			}
		});
		recordNumberTextField.setPreferredSize(new Dimension(100, 24));
		recordNumberLabel.setFont(new java.awt.Font(recordNumberLabel.getFont().getFontName(), 0, 20)); // NOI18N
		recordNumberLabel.setText("Enter patient record number:");
		JPanel recordTextPanel = new JPanel();
		recordTextPanel.setLayout(new BorderLayout(5, 0));
		recordTextPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
		recordTextPanel.add(recordNumberLabel, BorderLayout.WEST);
		recordTextPanel.add(recordNumberTextField, BorderLayout.CENTER);
		recordTextPanel.add(jButton1, BorderLayout.EAST);

		this.setLayout(new BorderLayout(10, 10));
		this.add(recordTextPanel, BorderLayout.NORTH);
		this.add(new JScrollPane(recordsTableHandler.getTableView()), BorderLayout.CENTER);
		this.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
		this.add(Box.createHorizontalStrut(20), BorderLayout.EAST);
		this.add(Box.createVerticalStrut(50), BorderLayout.SOUTH);
	}

	private void initRecordTable() {
		recordsTableHandler.addColumn("Id");
		recordsTableHandler.addColumn("Number");
		recordsTableHandler.addColumn("Patient");
		recordsTableHandler.addColumn("See Patient Record");
		Action see = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				Integer recordNumber = (Integer) recordsTableHandler.getTableModel().getValueAt(modelRow, 0);
				MedicalRecord medicalRecord = rdf.getMedicalRecordById(recordNumber);
				Singleton.getInstance().getMainFrame().setCentralPanel(new PatientRecordPanel(medicalRecord));

			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(recordsTableHandler.getTableView(), see, 3);

	}

	private void initData(List<MedicalRecord> medicalRecords) {
		for (MedicalRecord medicalRecord : medicalRecords) {
			Vector<Object> v = new Vector<Object>();
			v.add(medicalRecord.getId());
			v.add(medicalRecord.getMedicalRecordNumber());
			v.add(medicalRecord.getFirstName() + medicalRecord.getLastName());
			v.add("see");
			recordsTableHandler.insertRow(v);
		}
	}

	public String getRecordNumber() {
		return this.recordNumberTextField.getText();
	}

}
