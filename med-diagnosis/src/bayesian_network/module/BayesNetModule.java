package bayesian_network.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

import unbbayes.io.NetIO;
import unbbayes.prs.Edge;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

/**
 * Modul koji sluzi za prosledjivanje simptoma i dobijanje najverovatnijih bolesti
 * @author Dusan
 *
 */
public class BayesNetModule {
	
	private static ProbabilisticNetwork net = null;
	private static DefaultGraph graph = null;
	
	/**
	 * Ako se navede "" ili null, onda ce naci difoltnu fajl adresu
	 * @param filePath
	 */
	public void initBayes(String filePath){
		if(filePath == null || filePath.equals("")) {
			try {
				net = (ProbabilisticNetwork)new NetIO().load(new File("data/zvanicna_mreza.net"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * Vraca mapu  &lt;ImeBolesti, VerovatnocaBolesti&gt;
	 * Prosledjuju mu se lista simptoma u obliku stringova.
	 * Stavice 100% za te simptome, a 0% za sve ostale simptome
	 * @param symptomList - lista simptoma koji su uoceni
	 * @param age - broj godina pacijenta
	 * @param gender - pol pacijenta
	 * @return
	 */
	public Map<String, Float> getDiseaseListPercentage(List<String> symptomList, Integer age, String gender){
		Map<String, Float> diseaseListPercentage = new TreeMap<String, Float>();
		List<Node> nodeList = net.getNodes();
		IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
		algorithm.setNetwork(net);
		algorithm.run();
		
		List<String> listaSvihSimptoma = getAllSymptoms();
		for (String symptom : listaSvihSimptoma) {
			ProbabilisticNode factNode = (ProbabilisticNode)net.getNode(symptom);
			int stateIndex = 1; 
			factNode.addFinding(stateIndex);
		
		}
		
		//za svaki simptom nadjeni, stavi "Da"
		for (String symptom : symptomList) {
			ProbabilisticNode factNode = (ProbabilisticNode)net.getNode(symptom);
			int stateIndex = 0; 
			factNode.addFinding(stateIndex);
		}
		
		
		
		//stavi godiste
		Integer ageRange = findAgeRange(age);
		ProbabilisticNode factNode = (ProbabilisticNode)net.getNode("age");
		int stateIndex = ageRange; // index of state "green"
		factNode.addFinding(stateIndex);
		
		//stavi pol
		factNode = (ProbabilisticNode)net.getNode("gender");
		stateIndex = gender.equals("male")? 0 : 1; // index of state "green"
		factNode.addFinding(stateIndex);
		
	
		
		// propagation
		try {
        	net.updateEvidences();
        } catch (Exception e) {
        	System.out.println(e.getMessage());               	
        }
		
//        // states overview after propagation
//		for (Node node : nodeList) {
//			System.out.println(node.getName());
//			for (int i = 0; i < node.getStatesSize(); i++) {
//				System.out.println(node.getStateAt(i) + ": " + ((ProbabilisticNode)node).getMarginalAt(i));
//			}
//		}
		diseaseListPercentage = getSortedDiseaseList(net);		
		return diseaseListPercentage;
	}
	
	/**
	 * Izvuci sve simptome
	 * @throws Exception 
	 */
	private List<String> getAllSymptoms() {
		//izvuci sve simptome iz fajla
		List<String> listaSvihSimptoma = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"data/svi_simptomi.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				listaSvihSimptoma.add(line.trim().replace(" ", "_"));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listaSvihSimptoma;
	}
	
	/**
	 * Stavi godine, da bi dobio odgovarajucu vrednost u rasponu
	 * @param age
	 * @return
	 * @throws Exception 
	 */
	private Integer findAgeRange(Integer age)  {
		if(age < 1)
			return 0;
		if(inRange(age, 1, 4))
			return 1;
		if(inRange(age, 5, 14))
			return 2;
		if(inRange(age, 15, 29))
			return 3;
		if(inRange(age, 30, 44))
			return 4;
		if(inRange(age, 45, 59))
			return 5;
		if(inRange(age, 60, 74))
			return 6;
		return 7;
	}
	
	private Boolean inRange(Integer element, Integer bottom, Integer top) {
		return element >= bottom && element <= top;
	}
	
	/**
	 * Nadje sve bolesti i redom ih raspodeli i ubaci u mapu za konacnu listu
	 */
	private Map<String, Float> getSortedDiseaseList(ProbabilisticNetwork net) {
		//izvuci sve simptome iz fajla
		Map<String, Float> mapaSvihSimptoma = new TreeMap<String, Float>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"data/sve_bolesti.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				ProbabilisticNode factNode = (ProbabilisticNode)net.getNode(line.trim());
				Float probability = factNode.getMarginalAt(0);
				mapaSvihSimptoma.put(line.trim(), probability);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Float> sortedMap = 
			    mapaSvihSimptoma.entrySet().stream().
			    sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).
			    collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			                             (e1, e2) -> e1, LinkedHashMap::new));
		return sortedMap;
	}
	
	
	public void visualizeBayesianNetwork(List<String> symptoms) {
		Map<String, List<String>> mapSymptomToDisease = getMapSymptomToDiseaseMap(symptoms);
		BayesGraphVisualizer.drawBayes(mapSymptomToDisease);
	}
	
	public boolean saveGraph(String imageFilePath) {
		return BayesGraphVisualizer.saveGraph(graph, imageFilePath);
	}
	
	private Map<String, List<String>> getMapSymptomToDiseaseMap(List<String> symptoms){
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		//uzmi sve bolesti sa procentima
		Map<String, Float> diseasePercentage = getSortedDiseaseList(this.net);
		
		//za svaki simptom napravi listu i nadji njegovu vezu u net-u sa bolescu
		List<Edge> symptomToDiseaseEdges =  net.getEdges();
		
		for (String symptom : symptoms) {
			
			List<String> diseaseList = new ArrayList<String>();
			//kad nadjes bolest dodaj u njegovu kolekciju, zajedno sa procentom
			Node symptomNode = net.getNode(symptom);
			for (Edge edge : symptomToDiseaseEdges) {
				if(edge.getDestinationNode() == symptomNode) {
					Node foundDisease = edge.getOriginNode();
					String disease = foundDisease.getName();
					diseaseList.add(disease + " : " + diseasePercentage.get(disease));
				}
			}
			map.put(symptom, diseaseList);			
		}
		
		return map;
	}
	
	

	

}
