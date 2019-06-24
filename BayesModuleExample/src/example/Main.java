package example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.demo.DirectedGraphDemo;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;


public class Main {

	public static void main(String[] args) throws Exception {
		String[] array = {"weakness", "leg_weakness"};
		List<String> symptomList = Arrays.asList(array);
		BayesNetModule module = new BayesNetModule();
	    module.initBayes("");
		Map<String, Float> mapa = module.getDiseaseListPercentage(symptomList, 0, "male");
		
		
		Graph<String, DefaultEdge> simpleGraph = new SimpleGraph<>(DefaultEdge.class);
		
		
		for (String mapaKey : mapa.keySet()) {
			simpleGraph.addVertex(mapaKey +  ": " + mapa.get(mapaKey));
		}
		
		
		for (String symptom : symptomList) {
			simpleGraph.addVertex(symptom);
			
			for (String mapaKey : mapa.keySet()) {
				simpleGraph.addEdge(mapaKey, symptom);
			}
		}
			
		
	}	
	
	public void createCompleteGraph(Graph<String, DefaultEdge> completeGraph) {
	    CompleteGraphGenerator<String, DefaultEdge> completeGenerator 
	      = new CompleteGraphGenerator<>(1);
	    VertexFactory<String> vFactory = new VertexFactory<String>() {
	        private int id = 0;
	        public String createVertex() {
	            return "v" + id++;
	        }
	    };
	    completeGenerator.generateGraph(completeGraph, vFactory, null);
	}
	
	

}
