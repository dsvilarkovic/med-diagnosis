package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchRecordPanel extends JPanel {

	private JTextField recordNumberTextField;
	
	public SearchRecordPanel() {
		JLabel recordNumberLabel = new JLabel();
        recordNumberTextField = new JTextField();
        JButton jButton1 = new JButton("Search");

        recordNumberLabel.setFont(new java.awt.Font("Ariel", 0, 24));  // NOI18N
        recordNumberLabel.setText("Enter patient record number:");


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(recordNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(recordNumberTextField))
                .addGap(34, 34, 34)
                .addComponent(jButton1)
                .addContainerGap(113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(recordNumberLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recordNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(162, Short.MAX_VALUE))
        );
		
	}
	
	
	public String getRecordNumber() {
		return this.recordNumberTextField.getText();
	}
}
