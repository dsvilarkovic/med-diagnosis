package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasa za izvodjenje simptoma iz disease_base fajla
 * @author Dusan 
 *
 */
public class SymptomFileExtractor {

	
	/**
	 * Funkcija koja vadi sve simptome u jedinstvenu listu. <br>
	 * Za combobox koristiti
	 * @return listu simptoma u obliku {@code String}
	 */
	public static List<String> extractFile() {
		File file = new File("data/disease_base.pl"); 
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			System.err.println("No file found");
		} 
		
		List<String> symptomList = new ArrayList<String>();
		
		String st; 
		try {
			while ((st = br.readLine()) != null) {
				//System.out.println(st); 
				Pattern pattern = Pattern.compile(",.*\\)");
				Matcher matcher = pattern.matcher(st);
				if(matcher.find() && st.contains("symptom")) {	
					String foundString = matcher.group(0);
					foundString = foundString.replace(",", "")
											 .replace(")", "")
											 .replace(" ", "")
											 .replace("_", " ");
					
					System.out.println(foundString);	
					if(symptomList.contains(foundString) == false) {
						symptomList.add(foundString);
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		return symptomList;
	}
	
	/**
	 * Za testiranje modula
	 * @param args
	 */
	public static void main(String[] args) {
		SymptomFileExtractor.extractFile();
	}
}
