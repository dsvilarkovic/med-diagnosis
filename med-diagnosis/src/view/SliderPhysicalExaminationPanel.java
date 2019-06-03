package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;

public class SliderPhysicalExaminationPanel extends SinglePhysicalExaminationPanel {
		
		private String symptom;
		private JSlider physicalExaminationSlider;
		
		public SliderPhysicalExaminationPanel(String name, String symptomName) {
			symptom = symptomName;
			physicalExaminationLabel = new JLabel(name);
			physicalExaminationSlider = new JSlider(JSlider.HORIZONTAL,0, 10, 0);
			physicalExaminationSlider.setMajorTickSpacing(1);
			physicalExaminationSlider.setPaintLabels(true);
			physicalExaminationSlider.setToolTipText("0-normal 10-severe damage");
			this.setLayout(new BorderLayout(2,2));
			this.setMaximumSize(new Dimension(600, 50));
			this.setPreferredSize(new Dimension(600, 50));
			this.add(physicalExaminationLabel,BorderLayout.LINE_START);
			this.add(physicalExaminationSlider,BorderLayout.LINE_END);
		}
		
		/**
		 * Metoda koja vraca simptom koji nastaje kao rezultat fizikalnog pregleda
		 * Simptom je prisutan jedino ako je na slajderu vrednost pomerena sa 0
		 */
		public String getPhysicalExaminationSymptom() {
			if(physicalExaminationSlider.getValue() != 0) {
				return symptom.trim();
			}
			return "";
		}
		
		public String getSymptom() {
			return symptom.trim();
		}
		
		public Integer getSymptomValue() {
			return physicalExaminationSlider.getValue();
		}
}
