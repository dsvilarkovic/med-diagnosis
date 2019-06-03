package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PhysicalExaminationPanel extends JPanel {

	private String filePath;
	private JPanel physicalExamPanel;

	public PhysicalExaminationPanel() {
		filePath = "data/physical_examinations_symptoms.txt";
		physicalExamPanel = new JPanel();
		initGUI();
	}

	private void loadPhysicalExaminations() {
		String detail;
		try {
			BufferedReader empdtil = new BufferedReader(new FileReader(filePath));
			int i = 0;
			while ((detail = empdtil.readLine()) != null) {
				String[] parts = detail.split(";");
				if (parts.length > 1) {
					SinglePhysicalExaminationPanel singlePhysicalExaminationPanel = null;
					String[] parts2 = parts[1].split(",");
					if (parts2.length > 1) {
						singlePhysicalExaminationPanel = new TextPhysicalExaminationPanel(parts[0],parts2);
					} else {
						singlePhysicalExaminationPanel = new SliderPhysicalExaminationPanel(parts[0], parts[1]);
					}
					physicalExamPanel.add(singlePhysicalExaminationPanel);
					i++;
					
				} else if (!detail.equals("")) {
					if(i!= 0 && i%2 == 0) {
						physicalExamPanel.add(Box.createHorizontalStrut(5));
					}
					JLabel label = new JLabel(detail);
					physicalExamPanel.add(label);
					i++;
					physicalExamPanel.add(Box.createHorizontalStrut(5));
				}
			}
			empdtil.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void initGUI() {
		JLabel physicalExaminationLabel = new JLabel("Physical examination");
		physicalExaminationLabel.setFont(new Font(physicalExaminationLabel.getFont().getFontName(), Font.BOLD,16));
		physicalExamPanel.setLayout(new GridLayout(19,0,10,2));
		loadPhysicalExaminations();
		this.setLayout(new BorderLayout(5, 5));
		this.add(physicalExaminationLabel, BorderLayout.NORTH);
		this.add(physicalExamPanel, BorderLayout.WEST);
	}
	
	public List<String> getSymptomsList() {
		List<String> resultList = new ArrayList<String>();
		Component[] components = physicalExamPanel.getComponents();
		for (int i = 0; i < components.length; i++) {
			if(components[i] instanceof SliderPhysicalExaminationPanel) {
				SliderPhysicalExaminationPanel examinationPanel = (SliderPhysicalExaminationPanel) components[i];
				if(examinationPanel.getPhysicalExaminationSymptom() != "") {
					resultList.add(examinationPanel.getPhysicalExaminationSymptom());
				}
			} else if(components[i] instanceof TextPhysicalExaminationPanel) {
				TextPhysicalExaminationPanel examinationPanel = (TextPhysicalExaminationPanel) components[i];
				if(examinationPanel.getPhysicalExaminationSymptom() != "") {
					resultList.add(examinationPanel.getPhysicalExaminationSymptom());
				}
			}
		}
		return resultList;
	}
	
	public Map<String, Integer> getSymptomsMap() {
		Map<String, Integer> resultMap = new HashMap<>();
		Component[] components = physicalExamPanel.getComponents();
		for (int i = 0; i < components.length; i++) {
			if(components[i] instanceof SliderPhysicalExaminationPanel) {
				SliderPhysicalExaminationPanel examinationPanel = (SliderPhysicalExaminationPanel) components[i];
				resultMap.put(examinationPanel.getSymptom(), examinationPanel.getSymptomValue());
			}
		}
		return resultMap;
	}
}
