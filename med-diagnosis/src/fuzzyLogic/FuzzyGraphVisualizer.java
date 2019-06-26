package fuzzyLogic;

import java.util.List;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;

public class FuzzyGraphVisualizer {

	public static void drawFuzzy(Map<String, Map<String, String>> map) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		DefaultGraph g = new DefaultGraph("Bayes inference graph");
        g.addAttribute("ui.quality");
        g.addAttribute("ui.antialias");
        g.addAttribute("ui.stylesheet","graph { padding: 10px; fill-color: #9C9BA5; }");

        
        for (String fuzzyInput : map.keySet()) {
			g.addNode(fuzzyInput);
			
			Node fuzzyInputNode = g.getNode(fuzzyInput);
			
			fuzzyInputNode.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: cyan;size: 40px; text-alignment: center;"
					+ "shape: rounded-box; fill-color: cyan; stroke-mode: plain; size-mode: fit;");
			fuzzyInputNode.addAttribute("ui.label", fuzzyInput);
			
			//sada idemo na pravila i outpute da dodajemo da dodajemo
			Map<String, String> fuzzyRuleToFuzzyOutput = map.get(fuzzyInput);
			for (String fuzzyRule : fuzzyRuleToFuzzyOutput.keySet()) {
				
				g.addNode(fuzzyRule);
				
				Node fuzzyRuleNode = g.getNode(fuzzyRule);
				
				//TODO: dodati elipsu
				fuzzyRuleNode.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: #FF9C33;size: 40px; text-alignment: center; "
						+ "shape: rounded-box; fill-color: #FFC133; stroke-mode: plain; size-mode: fit; ");
				fuzzyRuleNode.addAttribute("ui.label", fuzzyRule);
				
				//dodaj jednu ivicu
				g.addEdge(fuzzyInput +":" +fuzzyRule, fuzzyInput, fuzzyRule, true);
				Edge edge = g.getEdge(fuzzyInput +":" +fuzzyRule);
				//edge.setAttribute("ui.arrow-style", "arrow");
				//TODO: izmeniti ivice
				edge.addAttribute("ui.style", "shape:blob; size: 12px; fill-color: #E7E7EA;");
				
				//sad i za fuzzyOutput
				String fuzzyOutput = fuzzyRuleToFuzzyOutput.get(fuzzyRule);
				
				if(g.getNode(fuzzyOutput) == null) {
					g.addNode(fuzzyOutput);
					
					Node fuzzyOutputNode = g.getNode(fuzzyOutput);
					fuzzyOutputNode.addAttribute("ui.style", "text-style:bold; shape:circle;fill-color: #B6FF33;size: 40px; text-alignment: center;"
							+ "shape: rounded-box; fill-color:  #B6FF33; stroke-mode: plain; size-mode: fit;");
					fuzzyOutputNode.addAttribute("ui.label", fuzzyOutput);
				}
				
				g.addEdge(fuzzyRule +":" +fuzzyOutput, fuzzyRule, fuzzyOutput, true);
				Edge newEdge = g.getEdge(fuzzyRule +":" +fuzzyOutput);
				//TODO: izmeniti ivice
				newEdge.addAttribute("ui.style", "shape:blob; size: 12px; fill-color: #E7E7EA;");
				
				
			}
			
		}
        		
	    Viewer viewer = g.display();
	    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
	}
}
