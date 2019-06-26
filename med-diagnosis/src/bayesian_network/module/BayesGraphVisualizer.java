package bayesian_network.module;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;


public class BayesGraphVisualizer {
	
	
	/**
	 * Prosledi unutar stringa za bolest procenat i kod aktiviranih simptoma moze 100%
	 * @param mapSymptomToDisease
	 */
	public static void drawBayes(Map<String, List<String>> mapSymptomToDisease) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		DefaultGraph g = new DefaultGraph("Bayes inference graph");
        g.addAttribute("ui.quality");
        g.addAttribute("ui.antialias");
        g.addAttribute("ui.stylesheet","graph { padding: 10px; fill-color: #9C9BA5; }");

		for (String symptom: mapSymptomToDisease.keySet()) {
		
			g.addNode(symptom);
			Node symptomNode = g.getNode(symptom);
			
			
			symptomNode.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: cyan;size: 30px; text-alignment: center;");
			symptomNode.addAttribute("ui.label", symptom);
			
			List<String> relatedDiseases = mapSymptomToDisease.get(symptom);
			for (String disease : relatedDiseases) {
				if(g.getNode(disease) == null) {
					g.addNode(disease);
					Node diseaseNode = g.getNode(disease);
					diseaseNode.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: yellow;size: 75px; text-alignment: center;");
					diseaseNode.addAttribute("ui.label", disease);
				}	
				else {
					System.out.println("Nekad i nadjem");
				}
				g.addEdge(symptom + "->" + disease, symptom, disease);		
				Edge edge = g.getEdge(symptom + "->" + disease);
				//edge.setAttribute("ui.arrow-style", "arrow");
				edge.addAttribute("ui.style", "shape:blob; size: 12px; fill-color: #E7E7EA;");
			}
		}
			
		
//		g.display();
	    Viewer viewer = g.display();
	    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
		
	}
	
	public static boolean saveGraph(DefaultGraph graph, String imageFilePath) {
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);
		pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		try {
			pic.writeAll(graph, imageFilePath);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}
