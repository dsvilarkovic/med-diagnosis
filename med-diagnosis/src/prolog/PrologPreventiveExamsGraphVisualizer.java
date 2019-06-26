package prolog;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;

import utils.Singleton;

public class PrologPreventiveExamsGraphVisualizer {




	public static void drawPrologExams(String disease, List<String> chosenTherapies, List<String> preventiveList) {
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
        
        
        if(chosenTherapies.size() > 2) {
	        for (int i = 0; i < chosenTherapies.size(); i++) {
	        	
	    		String chosenTherapy = chosenTherapies.get(i);
	    		String chosenTherapy2 = chosenTherapies.get((i + 1) % chosenTherapies.size());
	    		
	
				
				
				Node therapyNode = g.getNode(chosenTherapy);
				Node therapyNode2 = g.getNode(chosenTherapy2);
				
				if(therapyNode == null) {
					g.addNode(chosenTherapy);
					therapyNode = g.getNode(chosenTherapy);
					therapyNode.addAttribute("ui.label", chosenTherapy);
				}
				if(therapyNode2 == null) {
					g.addNode(chosenTherapy2);
					therapyNode2 = g.getNode(chosenTherapy2);
					therapyNode2.addAttribute("ui.label", chosenTherapy2);
				}
		
	
				therapyNode.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: #BF33FF; size: 50px; text-alignment: center;");
				therapyNode2.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: #BF33FF; size: 50px; text-alignment: center;");
	
				g.addEdge(chosenTherapy +":" +chosenTherapy2, chosenTherapy, chosenTherapy2, false);
				Edge edge = g.getEdge(chosenTherapy +":" +chosenTherapy2);
				edge.addAttribute("ui.style", "shape:blob; size: 12px; fill-color: #E7E7EA;");
				
				g.addEdge(chosenTherapy +":" + disease, chosenTherapy, disease, true);
				Edge diseaseEdge = g.getEdge(chosenTherapy +":" +disease);
				diseaseEdge.addAttribute("ui.style", "shape: freeplane;");
			}
	        
	        
        }
        else {
        	JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "There is no point in generating for small data",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
        	return;
        }
        
        
        if(preventiveList.size() > 2) {
	        for (int i = 0; i < preventiveList.size(); i++) {
	        	
	    		String preventive = preventiveList.get(i);
	    		String preventive2 = preventiveList.get((i + 1) % preventiveList.size());
	    		
	
				
				
				Node preventiveNode = g.getNode(preventive);
				Node preventiveNode2 = g.getNode(preventive2);
				
				if(preventiveNode == null) {
					g.addNode(preventive);
					preventiveNode = g.getNode(preventive);
					preventiveNode.addAttribute("ui.label", preventive);
				}
				if(preventiveNode2 == null) {
					
					g.addNode(preventive2);
					
					preventiveNode2 = g.getNode(preventive2);
					preventiveNode2.addAttribute("ui.label", preventive2);
				}
		
	
				preventiveNode.addAttribute("ui.style","text-style:bold; shape:circle;fill-color: #33FFCC;size: 50px; text-alignment: center;");
				preventiveNode2.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: #33FFCC; size: 50px; text-alignment: center;");
	
				g.addEdge(preventive +":" +preventive2, preventive, preventive2, false);
				Edge edge = g.getEdge(preventive +":" +preventive2);
				edge.addAttribute("ui.style", "shape:blob; size: 12px; fill-color: #E7E7EA;");
				
				g.addEdge(preventive +":" + disease,   disease, preventive, true);
				Edge diseaseEdge = g.getEdge(preventive +":" +disease);
				diseaseEdge.addAttribute("ui.style", "shape: freeplane;");
	
			}
        
        }
        else {
        	JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "There is no point in generating for small data",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
        	return;
        }
        
        
				
		Viewer viewer = g.display();
		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
		
	}
}
