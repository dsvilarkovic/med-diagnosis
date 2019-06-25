package prolog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPVariable;

import utils.Singleton;

public class PrologModule {
		
	private JIPEngine engine;
	
	public PrologModule() {
		engine = new JIPEngine();
	}
		
	public List<String> getTherapies(String diagnosis, List<String> allergies) {
		List<String> therapiesList = new ArrayList<String>();
		
		JIPTerm term = null;
		
		try {
			engine.consultFile("data/medications_base.pl");
			engine.consultFile("data/allergies.pl");
			
			String allergiesString = getListString(allergies);
			
			//TODO: popraviti primer sa alergijama
			//String termString = "suggested_treatment('" + diagnosis + "', " + allergiesString + ", Medication).";
			
			String termString = "reccommended_medication_therapies('" + diagnosis + "', Medication).";
			System.out.println("Getting therapies: \n" + termString);
						
			term = engine.getTermParser().parseTerm(termString);
			
			JIPQuery query = engine.openSynchronousQuery(term);
			
			JIPTerm solution;
			
			//primer sa vezbi
			/*while((solution = query.nextSolution()) != null){
				System.out.println(solution);

	            for (JIPVariable var : solution.getVariables()){
	                if (var.getName().equalsIgnoreCase("Medication")){
	                	System.out.println(var.getName() + " = " + var.getValue().toString() + " ");
			         	
			         	therapiesList.add(var.getValue().toString());
	                }
	            }
			}*/
			
			//primer sa github repozitorijuma jiprologa
			while (query.hasMoreChoicePoints()){
				solution = query.nextSolution();
				
				if(solution != null) {
					System.out.println(solution);

			    	JIPVariable[] vars = solution.getVariables();
			   	    for (JIPVariable var : vars) {
			   	    	if (!var.isAnonymous()) {
				         	System.out.println(var.getName() + " = " + var.getValue().toString() + " ");
				         	
				         	therapiesList.add(var.getValue().toString());
			   	    	}
			   	    }				
				}
			}
			
		} catch (JIPSyntaxErrorException e) {
			JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "An error occurred while generating therapies. Please try again.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "An error occurred while generating therapies. Please try again.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		} finally {
			engine.closeAllQueries();
			
			engine.unconsultFile("data/medications_base.pl");
			engine.unconsultFile("data/allergies.pl");
		}
		
		return therapiesList;
	}

	public List<String> getPreventiveExaminations(String diagnosis, List<String> therapies) {		
		List<String> preventiveList = new ArrayList<String>();
		
		try {
			engine.consultFile("data/preventive_checkups.pl");
			
			String therapiesString = getListString(therapies); 
			
			String termString = "recommended_preventive_examinations("+ diagnosis + ", " + therapiesString + ", Result).";			
			System.out.println(termString);

			JIPTerm term = engine.getTermParser().parseTerm(termString);
						
			JIPQuery query = engine.openSynchronousQuery(term);
			
			JIPTerm solution;
			
			while (query.hasMoreChoicePoints()){
				solution = query.nextSolution();
		    	System.out.println(solution);

		    	JIPVariable[] vars = solution.getVariables();
		   	    for (JIPVariable var : vars) {
		   	    	if (!var.isAnonymous()) {
			         	System.out.println(var.getName() + " = " + var.getValue().toString() + " ");
			         	
			         	preventiveList.add(var.getValue().toString());
		   	    	}
		   	    }
		    }
			
		} catch (JIPSyntaxErrorException e) {
			JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "An error occurred while generating preventive examinations. Please try again.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "An error occurred while generating preventive examinations. Please try again.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
		finally {
			engine.closeAllQueries();
			engine.unconsultFile("data/preventive_checkups.pl");
		}
		
		return preventiveList;
	}
	
	private String getListString(List<String> allergies) {
		
		String resultString = "[";
		
		if(allergies.size() > 0) {
			for(int i = 0; i < allergies.size()-1; i++) {
				resultString += allergies.get(i);
				resultString += ", ";
			}
			
			resultString += allergies.get(allergies.size()-1);
		}
		
		resultString += "]";
		
		return resultString;
	}

}
