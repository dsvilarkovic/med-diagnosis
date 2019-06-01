package similarity;

import java.util.Set;

import model.Symptom;
import ucm.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

public class ListSimilarity implements LocalSimilarityFunction{
	
	public ListSimilarity() {

	}
	
	@Override
	public double compute(Object value1, Object value2) throws NoApplicableSimilarityFunctionException {
		
		int firstListSize = ((Set<Symptom>)value1).size();
		int secondListSize = ((Set<Symptom>)value2).size();
		
		if(firstListSize == 0) {
			return 1.0;
		}
		
		double sameElementsNumber = 0.0;
		
		for(Symptom s : (Set<Symptom>)value1) {
			if(((Set<Symptom>)value2).contains(s)) {
				sameElementsNumber += 1;
			}
		}	
				
		return sameElementsNumber / (firstListSize+secondListSize);		
	}

	@Override
	public boolean isApplicable(Object value1, Object value2) {
		if (value1 instanceof Set<?> && value2 instanceof Set<?>)
			return true;
		return false;
	}
	
}
