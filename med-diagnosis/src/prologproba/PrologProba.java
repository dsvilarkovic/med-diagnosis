package prologproba;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;

import utils.Singleton;

public class PrologProba {

	public PrologProba() {
		// TODO Auto-generated constructor stub
	}
	
	public void prologTestFunction() {
		JIPEngine engine = Singleton.getInstance().getEngine();
		
		try {
			engine.consultFile("data/medications_base.pl");
			
			JIPTermParser termParser = engine.getTermParser();
			
			JIPTerm term = termParser.parseTerm("recommended_medication(dementia, X, Y).");
			
			JIPQuery query = engine.openSynchronousQuery(term);
			
			JIPTerm solution;
			
			System.out.println("recommended_medication(dementia, X, Y).");
			
			while((solution = query.nextSolution()) != null) {
				System.out.println("solution: " + solution);
				
			}
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske!");
		}
				
	}

}
