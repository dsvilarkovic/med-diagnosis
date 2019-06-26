package prolog;

import java.util.List;

import javax.swing.JOptionPane;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;

import utils.Singleton;

public class PrologTherapyGraphVizualizer {

	public static void drawPrologTherapies(String disease, List<String> allergies, List<String> chosenTherapies) {
		// TODO Auto-generated method stub
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		DefaultGraph g = new DefaultGraph("Bayes inference graph");
        g.addAttribute("ui.quality");
        g.addAttribute("ui.antialias");
        g.addAttribute("ui.stylesheet","graph { padding: 10px; fill-color: #9C9BA5; }");
        
        g.addNode(disease);
        
        Node diseaseNode = g.getNode(disease);
        diseaseNode.addAttribute("ui.label", disease);
        diseaseNode.addAttribute("ui.style",  "shape: rounded-box; size: 80px;"
        + "     padding: 5px; "
        + "     fill-color: white; "
        + "     stroke-mode: plain; "
        + "     size-mode: fit; "
        + "");
        
        
        for (int i = 0; i < chosenTherapies.size(); i++) {
	        	
	    		String chosenTherapy = chosenTherapies.get(i);
	    		String chosenTherapy2 = chosenTherapies.get((i + 1) % chosenTherapies.size());
	    		
	
				
				
				Node therapyNode = g.getNode(chosenTherapy);
				
				if(therapyNode == null) {
					g.addNode(chosenTherapy);
					therapyNode = g.getNode(chosenTherapy);
					therapyNode.addAttribute("ui.label", chosenTherapy);
				}
				Node therapyNode2 = g.getNode(chosenTherapy2);

				if(therapyNode2 == null) {
					g.addNode(chosenTherapy2);
					therapyNode2 = g.getNode(chosenTherapy2);
					therapyNode2.addAttribute("ui.label", chosenTherapy2);
				}
		
	
				therapyNode.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: #FFFFE0; size: 50px; text-alignment: center;");
				therapyNode2.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: #FFFFE0; size: 50px; text-alignment: center;");
	
				if(chosenTherapies.size() > 2) {
					g.addEdge(chosenTherapy +":" +chosenTherapy2, chosenTherapy, chosenTherapy2, false);
					Edge edge = g.getEdge(chosenTherapy +":" +chosenTherapy2);
					edge.addAttribute("ui.style", "shape:blob; size: 12px; fill-color: #E7E7EA;");
				}
				
				g.addEdge(chosenTherapy +":" + disease, chosenTherapy, disease, true);
				Edge diseaseEdge = g.getEdge(chosenTherapy +":" +disease);
				diseaseEdge.addAttribute("ui.style", "shape: freeplane;");
		}
        
        for (int i = 0; i < allergies.size(); i++) {
        	
    		String allergy = allergies.get(i);
    		String allergy2 = allergies.get((i + 1) % allergies.size());
    		

			
			
			Node allergyNode = g.getNode(allergy);

			
			if(allergyNode == null) {
				g.addNode(allergy);
				allergyNode = g.getNode(allergy);
				allergyNode.addAttribute("ui.label", allergy);
			}
			
			Node allergyNode2 = g.getNode(allergy2);
			if(allergyNode2 == null) {
				
				g.addNode(allergy2);
				
				allergyNode2 = g.getNode(allergy2);
				allergyNode2.addAttribute("ui.label", allergy2);
			}
	

			allergyNode.addAttribute("ui.style","text-style:bold; shape:circle;fill-color: #33FFCC;size: 70px; text-alignment: center;");
			allergyNode2.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: #33FFCC; size: 70px; text-alignment: center;");

			if(allergies.size() > 2) {
				g.addEdge(allergy +":" +allergy2, allergy, allergy2, false);
				Edge edge = g.getEdge(allergy +":" +allergy2);
				edge.addAttribute("ui.style", "shape:blob; size: 12px; fill-color: #E7E7EA;");
			}
			
			g.addEdge(allergy +":" + disease,   allergy, disease, true);
			Edge diseaseEdge = g.getEdge(allergy +":" +disease);
			diseaseEdge.addAttribute("ui.style", "shape: freeplane;");

		}
	        
        
        
				
		Viewer viewer = g.display();
		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
		
	}
}
