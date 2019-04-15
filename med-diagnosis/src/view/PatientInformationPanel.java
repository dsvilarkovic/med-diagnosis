package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PatientInformationPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel lastNameLabel;
	private JLabel JMBGLabel;
	private JLabel phoneNumberLabel;
	private JLabel addressLabel;

	public PatientInformationPanel() {
		init();
	}

	private void init() {
		JLabel nameTextLabel = new JLabel("Name: ");
		JLabel lastNameTextLabel = new JLabel("Last name: ");
		JLabel JMBGTextLabel = new JLabel("JMBG: ");
		JLabel phoneNumberTextLabel = new JLabel("Phone number: ");
		JLabel addressTextLabel = new JLabel("Address: ");
		

		nameLabel = new JLabel("Vesna");
		lastNameLabel = new JLabel("Milic");
		JMBGLabel = new JLabel("0901997835287");
		phoneNumberLabel = new JLabel("+381638861772");
		addressLabel = new JLabel("Backa 38");
		
		JPanel textLabelsPanel = new JPanel();
		JPanel patientInfoLabelsPanel = new JPanel();
		JPanel contentPanel = new JPanel();
		JPanel emptyPanel = new JPanel();
		
		emptyPanel.setLayout(new BorderLayout());
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		textLabelsPanel.setLayout(new BoxLayout(textLabelsPanel,BoxLayout.Y_AXIS));
		patientInfoLabelsPanel.setLayout(new BoxLayout(patientInfoLabelsPanel,BoxLayout.Y_AXIS));
		
		textLabelsPanel.add(nameTextLabel);
		textLabelsPanel.add(Box.createVerticalStrut(15));
		textLabelsPanel.add(lastNameTextLabel);
		textLabelsPanel.add(Box.createVerticalStrut(15));
		textLabelsPanel.add(JMBGTextLabel);
		textLabelsPanel.add(Box.createVerticalStrut(15));
		textLabelsPanel.add(phoneNumberTextLabel);
		textLabelsPanel.add(Box.createVerticalStrut(15));
		textLabelsPanel.add(addressTextLabel);
		textLabelsPanel.add(Box.createVerticalStrut(15));
		
		patientInfoLabelsPanel.add(nameLabel);
		patientInfoLabelsPanel.add(Box.createVerticalStrut(15));
		patientInfoLabelsPanel.add(lastNameLabel);
		patientInfoLabelsPanel.add(Box.createVerticalStrut(15));
		patientInfoLabelsPanel.add(JMBGLabel);
		patientInfoLabelsPanel.add(Box.createVerticalStrut(15));
		patientInfoLabelsPanel.add(phoneNumberLabel);
		patientInfoLabelsPanel.add(Box.createVerticalStrut(15));
		patientInfoLabelsPanel.add(addressLabel);
		patientInfoLabelsPanel.add(Box.createVerticalStrut(15));
		
		contentPanel.add(textLabelsPanel);
		contentPanel.add(Box.createHorizontalStrut(40));
		contentPanel.add(patientInfoLabelsPanel);
		contentPanel.add(Box.createHorizontalStrut(20));
		
		emptyPanel.add(contentPanel, BorderLayout.NORTH);
		emptyPanel.add(new JPanel(), BorderLayout.CENTER);

		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(15));
		this.add(Box.createVerticalStrut(35));
		this.add(emptyPanel);
		
	}
}
