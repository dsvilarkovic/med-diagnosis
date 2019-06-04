package prologproba;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPVariable;

public class PrologModule {
	
	private  JIPEngine engine;
	
	public PrologModule() {
		engine = new JIPEngine();
	}
	
	public List<String> getTherapies() {
		List<String> therapiesList = new ArrayList<String>();
		try {
			engine.consultFile("data/medications_base.pl");
			engine.consultFile("data/allergies.pl");
						
			JIPTerm term = engine.getTermParser().parseTerm("suggested_treatment('migraine', [], Medication).");
			
			JIPQuery query = engine.openSynchronousQuery(term);
			
			JIPTerm solution;
			
			while(query.hasMoreChoicePoints()) {
				 solution = query.nextSolution();
				 
				 
				 JIPVariable[] vars = solution.getVariables();
		         for (JIPVariable var : vars) {
		        	 if (!var.isAnonymous()) {
		        		 
		        		 therapiesList.add(var.toString(engine));
		             }
		         } 
			}

			
		} catch (JIPSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return therapiesList;
	}

	public List<String> getPreventiveExaminations() {
		List<String> therapiesList = new ArrayList<String>();
		try {
			engine.consultFile("data/preventive_checkups.pl");
						
			JIPTerm term = engine.getTermParser().parseTerm("preventive_examinations('dementia', [], Result).");
			
			JIPQuery query = engine.openSynchronousQuery(term);
			
			JIPTerm solution;
			
			while(query.hasMoreChoicePoints()) {
				 solution = query.nextSolution();
				 
				 
				 JIPVariable[] vars = solution.getVariables();

				 for (JIPVariable var : vars) {
		        	 if (!var.isAnonymous()) {
		        		 
		        		 therapiesList.add(var.toString(engine));
		             }
		         } 
			}

			
		} catch (JIPSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return therapiesList;
	}

}
