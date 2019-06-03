package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class SinglePhysicalExaminationPanel extends JPanel {
	
	protected JLabel physicalExaminationLabel;

	public String getPhysicalExaminationName() {
		return physicalExaminationLabel.getText();
	}

	public abstract String getPhysicalExaminationSymptom();
}
