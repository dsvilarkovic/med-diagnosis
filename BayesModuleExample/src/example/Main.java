package example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Main {

	public static void main(String[] args) throws Exception {
		String[] array = {"weakness", "leg_weakness"};
		List<String> symptomList = Arrays.asList(array);
		BayesNetModule module = new BayesNetModule();
	    module.initBayes("");
		Map<String, Float> mapa = module.getDiseaseListPercentage(symptomList, 0, "male");
		
		for (String mapKey : mapa.keySet()) {
			System.out.println(mapKey + " : " + mapa.get(mapKey));
		}
	}	
	
	

}
