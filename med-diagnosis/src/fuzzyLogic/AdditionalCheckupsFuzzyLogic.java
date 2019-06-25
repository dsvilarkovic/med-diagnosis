package fuzzyLogic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class AdditionalCheckupsFuzzyLogic {

	private List<String> inputVarNames;
	private List<String> outputVarNames;
	private FIS fis;

	public AdditionalCheckupsFuzzyLogic(String path) {
		fis = FIS.load(path, true);

		if (fis == null) { // Error while loading?
			System.err.println("Can't load file: '" + path + "'");
			return;
		}

		inputVarNames = Arrays.asList("abnormal_involuntary_movements", "ache_all_over", "anxiety_and_nervousness",
				"back_cramps_or_spasms", "back_pain", "bleeding_from_ear", "blindness", "chills", "cough",
				"delusions_or_hallucinations", "depression", "depressive_or_psychotic_symptoms",
				"difficulty_in_swallowing", "difficulty_speaking", "diminished_vision", "disturbance_of_memory",
				"disturbance_of_smell_or_taste", "dizziness", "eye_burns_or_stings", "eye_moves_abnormally", "fatigue",
				"fears_and_phobias", "fever", "focal_weakness", "hand_or_finger_weakness", "headache",
				"hostile_behavior", "insomnia", "irregular_appearing_scalp", "lack_of_growth", "leg_cramps_or_spasms",
				"leg_pain", "leg_stiffness_or_tightness", "leg_weakness", "long_menstrual_periods", "loss_of_sensation",
				"low_back_pain", "muscle_cramps_contractures_or_spasms", "muscle_stiffness_or_tightness",
				"muscle_weakness", "nausea", "neck_pain", "neck_stiffness_or_tightness", "painful_menstruation",
				"painful_sinuses", "paresthesia", "problems_with_movement", "restlessness", "seizures",
				"spots_or_clouds_in_vision", "stiffness_all_over", "symptoms_of_the_face", "vomiting", "weakness",
				"disturbance_of_smell", "pupillary_abnormalities", "disturbance_of_facial_sensation",
				"disturbance_of_motor_facial_functions", "hearing_problems", "disturbance_of_taste",
				"problems_with_head_side_to_side_movement", "tongue_movement_problems", "spasticity",
				"upper_extremity_weakness", "lower_extremity_weakness", "disturbance_of_upper_extremity_sensation",
				"disturbance_of_lower_extremity_sensation", "balance_problems",
				"dysmetria");

		outputVarNames = Arrays.asList("magnetic_resonance_imaging", "lumbar_puncture_fluid_analysis",
				"psychologic_analysis", "ct_scan", "hematologic_tests", "emng", "eeg");
		initValues();
	}

	public void initValues() {
		for (String varName : inputVarNames) {
			fis.setVariable(varName, 0);
		}
		fis.evaluate();
	}

	public void setValues(Map<String, Integer> symptoms) {
		System.out.println(symptoms);
		for (Map.Entry<String, Integer> entry : symptoms.entrySet()) {
			fis.setVariable(entry.getKey(), entry.getValue());
		}
		fis.evaluate();

	}

	public Map<String, String> getAdditionalCheckupsMap() {
		Map<String, String> resultMap = new HashMap<>();
		for (String varName : outputVarNames) {
			Variable variable = fis.getVariable(varName);
			String term = getTerm(variable.getValue());
			if (term != null) {
				resultMap.put(varName, term);
			}
		}
		return resultMap;
	}

	public String getTerm(double value) {
		if (value > 1 && value < 3.5) {
			return "low";
		} else if (value >= 3.5 && value <= 4) {
			return "between medium and low";
		} else if (value > 4 && value < 6) {
			return "medium";
		} else if (value >= 6 && value <= 7.5) {
			return "between medium and high";
		} else if (value > 7.5 && value <= 10) {
			return "high";
		}

		return null;
	}
	
	public Map<String, Map<String,String>> getData() {
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String,String>>();
		for( Rule r : fis.getFunctionBlock("additional_procedures").getFuzzyRuleBlock("blok1").getRules() ) {
		      if(r.getDegreeOfSupport() > 0) {
		    	  String cause = r.getAntecedents().toString();
		    	  String consequence = r.getConsequents().get(0).toString();
		    	  String[] parts2 = String.valueOf(r).split(" ");
		    	  String rule = parts2[1] + " " + parts2[2] + " " + parts2[3] + " " +  parts2[4] + " " +  parts2[5] + " " + parts2[6] + " " + parts2[7] ;
		    	  if(!resultMap.containsKey(cause)) {
		    		  Map<String,String> map = new HashMap<String,String>();
		    		  map.put(rule, consequence);
		    		  resultMap.put(cause,map);
		    	  }else {
		    		  Map<String,String> map  = resultMap.get(cause);
		    		  map.put(rule, consequence);
		    	  }
		    	  
		    	  
		      }
		}
		System.out.println(resultMap);
		return resultMap;
	}
}
