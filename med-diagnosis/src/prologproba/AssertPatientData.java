package prologproba;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;
import com.ugos.jiprolog.engine.JIPTermParser.TermEnumerator;

import utils.Singleton;
import utils.SymptomFileExtractor;

public class AssertPatientData {
	
	private static JIPEngine engine = Singleton.getInstance().getEngine();
	
	//metoda ubaci cinjenice
	/**
	 * <b>Za sad van funkcije </b> <br/>
	 * Metoda za ubacivanje u radnu memoriju svih simptoma za datog pacijenta. <br/>
	 * Za sada postoji samo id pacijenta, ali bolje je uvesti kao kljuc patientID i sessionId 
	 * @param patientId
	 * @param symptomList
	 */
	public static void assertAnamnesisFacts(Integer patientId, List<String> symptomList) {
		
		JIPTermParser termParser = engine.getTermParser();
		JIPTerm term = null; 
		
		
		String assertString = "";
		for (String symptom : symptomList) {
			symptom = symptom.replace(" ", "_");
			assertString = String.format("symptom('%d', %s)", patientId, symptom);
			
			System.out.println(assertString);
			term = termParser.parseTerm(assertString);
			engine.assertz(term);			
			
		}
		
		
		
		
	}
	
	
	
	private static String createSymptomString(List<String> symptomList) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("[");
		for (String symptom : symptomList) {
			builder.append(symptom + ", ");
		}
		String symptomString = builder.toString();
		//ukloni poslednji zarez
		symptomString = symptomString.substring(0, symptomString.length() - 2);
		symptomString += "]";
		
		System.out.println(symptomString);
		
		
		return symptomString;
	}
	
	
	
	/**
	 * 
	 * <b>Za sad van funkcije </b> <br/>
	 * Metoda izbaci rezultat koju bolest moze imati pacijent
	 * @param patientId - id pacijenta
	 * @return ime bolesti koju je pacijent dobio
	 */
	public static List<String> getDiseaseList(Integer patientId) {
		
		JIPTermParser termParser = engine.getTermParser();
		JIPTerm term = null; 
		
		
		String queryString = String.format("disease('%d', X).", patientId);
		
		System.out.println(queryString);
		term = termParser.parseTerm(queryString);
		System.out.println(term);
		JIPQuery query = engine.openSynchronousQuery(term);		
		JIPTerm solution = null;
		String st = "";
		List<String> diseaseList = new ArrayList<String>();
		while((solution = query.nextSolution()) != null) {
			System.out.println("solution: " + solution);	
			
			
			Pattern pattern = Pattern.compile(",.*\\)");
			Matcher matcher = pattern.matcher(st);
			if(matcher.find() && st.contains("disease")) {	
				//za ekstrakciju drugog parametra koji u disease_base.pl je ustv naziv bolesti
				String foundString = matcher.group(0);
				foundString = foundString.replace(",", "")
										 .replace(")", "");
				
				System.out.println(foundString);	
				if(diseaseList.contains(foundString) == false) {
					diseaseList.add(foundString);
				}
			}
		}
		
		return diseaseList;
	}
	
	/**
	 * Metoda izbaci rezultat koju bolest moze imati pacijent
	 * @param symptomString - lista simptoma u jednom string u obliku [sym1, sym2, sym3]
	 * @return ime bolesti koju je pacijent dobio
	 */
	public static List<String> getDiseaseList(String symptomString){
		List<String> diseaseList = new ArrayList<String>();
		
		JIPTermParser termParser = engine.getTermParser();
		JIPTerm term = null; 
		
		
		String queryString = String.format("possible_diseases(%s, X).", symptomString);
		System.out.println(queryString);
		term = termParser.parseTerm(queryString);
		
		JIPQuery query = engine.openSynchronousQuery(term);		
		JIPTerm solution = null;
		String st = "";
		while((solution = query.nextSolution()) != null) {
			System.out.println("solution: " + solution);	
			
			Pattern pattern = Pattern.compile("\\)\\),.*\\)");
			Matcher matcher = pattern.matcher(solution.toString());
			if(matcher.find()) {	
				//za ekstrakciju drugog parametra koji u disease_base.pl je ustv naziv bolesti
				String foundString = matcher.group(0);
				foundString = foundString.replace(")", "")
										 .replace(",", "");
				
				if(diseaseList.contains(foundString) == false)
					diseaseList.add(foundString);
			}
		}
		
		return diseaseList;
	}
	
	/**
	 * 
	 * @param args
	 * @return 
	 */
	public static List<String> getMedicationList(String diseaseName) {
		List<String> medicationList = new ArrayList<String>();
		
		JIPTermParser termParser = engine.getTermParser();
		JIPTerm term = null; 
		
		
		String queryString = String.format("recommended_medication(%s, X, Y).", diseaseName);
		System.out.println(queryString);
		term = termParser.parseTerm(queryString);
		
		JIPQuery query = engine.openSynchronousQuery(term);		
		JIPTerm solution = null;

		while((solution = query.nextSolution()) != null) {
			System.out.println("solution: " + solution);	
			
			String medication = (solution.getVariables()[0].getValue()).toString();
			
			System.out.println("Ejjj  " + medication);
			if(medicationList.contains(medication) == false)
				medicationList.add(medication);
		
		}
		System.out.println("Izaso?");
		return medicationList;
		
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void getProceduresList(String diseaseName) { 
		
	
	}
	
	
	//test main-om
	public static void main(String[] args) {
		
		try {
			engine = Singleton.getInstance().getEngine();	
			engine.consultFile("data/disease_list_base.pl");
			
			List<String> list = SymptomFileExtractor.extractFile(true);
			Integer patientId = 11;
			
			list = list.subList(0, 2);
			
			
			//AssertPatientData.assertAnamnesisFacts(patientId, list);			
			
			String symptomString = AssertPatientData.createSymptomString(list);
			List<String> diseaseList = AssertPatientData.getDiseaseList(symptomString);
			
			System.out.println(diseaseList);
			
			engine.consultFile("data/medications_base.pl");
			
			Map<String, List<String>> medicationMap = new TreeMap<String, List<String>>();
			
			for (String disease : diseaseList.subList(0, 1)) {
				
				List<String> medicationsList = getMedicationList(disease);
				medicationMap.put(disease, medicationsList);
			}
			
		} catch (JIPSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
