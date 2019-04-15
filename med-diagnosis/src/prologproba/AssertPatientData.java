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
import com.ugos.jiprolog.engine.JIPVariable;
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
	
	
	/**
	 * Metoda koja od liste simptoma pravi oblik pogodan za upit na JIProlog
	 * @param symptomList
	 * @return
	 */
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
	 * @param symptomList - lista simptoma 
	 * @return ime bolesti koju je pacijent dobio
	 */
	public static List<String> getDiseaseList(List<String> symptomList){
		String symptomString = AssertPatientData.createSymptomString(symptomList);
		
		List<String> diseaseList = new ArrayList<String>();
		
		JIPTermParser termParser = engine.getTermParser();
		JIPTerm term = null; 
		
		
		String queryString = String.format("possible_diseases(%s, X).", symptomString);
		System.out.println(queryString);
		term = termParser.parseTerm(queryString);
		
		JIPQuery query = engine.openSynchronousQuery(term);		
		JIPTerm solution = null;
		
		while((solution = query.nextSolution()) != null) {
			Pattern pattern = Pattern.compile("\\)\\),.*\\)");
			Matcher matcher = pattern.matcher(solution.toString());
			if(matcher.find()) {	
				//za ekstrakciju drugog parametra koji u disease_base.pl je ustv naziv bolesti
				String foundString = matcher.group(0);
				foundString = foundString.replace(")", "")
										 .replace(",", "");
				
				System.out.println(foundString);
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
		term = termParser.parseTerm(queryString);
		
		JIPQuery query = engine.openSynchronousQuery(term);		
		JIPTerm solution = null;

		while((solution = query.nextSolution()) != null) {
			System.out.println("solution: " + solution);	
			
			
			
			Pattern pattern = Pattern.compile("\\(.*\\)");
			Matcher matcher = pattern.matcher(solution.toString());
			if(matcher.find()) {	
				String foundString = matcher.group(0);
				
				String[] tokens = foundString.split(",");
				
				foundString = tokens[1];
				
				System.out.println("Medication: " + foundString);
				if(medicationList.contains(foundString) == false) {
					medicationList.add(foundString);
				}
			}
			
		
		}
		return medicationList;
		
	}
	
	/**
	 * 
	 * @param args
	 * @return 
	 */
	public static List<String> getProceduresList(String diseaseName) { 
		List<String> proceduresList = new ArrayList<String>();
		
		JIPTermParser termParser = engine.getTermParser();
		JIPTerm term = null; 
		
		
		String queryString = String.format("recommended_procedure(%s, X, Y).", diseaseName);
		term = termParser.parseTerm(queryString);
		
		JIPQuery query = engine.openSynchronousQuery(term);		
		JIPTerm solution = null;

		while((solution = query.nextSolution()) != null) {
			System.out.println("solution: " + solution);	
			
			
			
			Pattern pattern = Pattern.compile("\\(.*\\)");
			Matcher matcher = pattern.matcher(solution.toString());
			if(matcher.find()) {	
				String foundString = matcher.group(0);
				
				String[] tokens = foundString.split(",");
				
				foundString = tokens[1];
				
				System.out.println("Procedure: " + foundString);
				if(proceduresList.contains(foundString) == false) {
					System.out.println("ee");
					proceduresList.add(foundString);
				}
			}
			
		
		}
		
		
		return proceduresList;	
	}
	
	
	//test main-om
	public static void main(String[] args) {
		
		try {
			engine = Singleton.getInstance().getEngine();	
			engine.consultFile("data/disease_list_base.pl");
			
			List<String> symptomList = SymptomFileExtractor.extractFile(true);	
			
			
			//Symptom lista, uzet samo podskup od svih nadjenih simptoma
			symptomList = symptomList.subList(0, 2);
			
			
			
			List<String> diseaseList = AssertPatientData.getDiseaseList(symptomList);
			
			engine.consultFile("data/medications_base.pl");
			
			Map<String, List<String>> medicationMap = new TreeMap<String, List<String>>();
			
			for (String disease : diseaseList.subList(0, 1)) {
				
				List<String> medicationsList = getMedicationList(disease);
				medicationMap.put(disease, medicationsList);
			}
			
			System.out.println(medicationMap);
			
			
			engine.consultFile("data/procedures_base.pl");
			
			Map<String, List<String>> proceduresMap = new TreeMap<String, List<String>>();
			
			for (String disease : diseaseList.subList(0, 1)) {
				
				List<String> proceduresList = getProceduresList(disease);
				proceduresMap.put(disease, proceduresList);
			}
			
			System.out.println(proceduresMap);
			
			
		} catch (JIPSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
