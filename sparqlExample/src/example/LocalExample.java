package example;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class LocalExample {

	public static void main(String[] args) {
		System.out.println("ALOOOO");
		Model model = ModelFactory.createDefaultModel();
		try {
			InputStream is = new FileInputStream("data/inzenjering_znanja.ttl");
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ALLLLLL");

		// INSERT
		String insertString = ""
				+ "PREFIX iz: <http://www.ftn.uns.ac.rs/iz#> "
				+ "INSERT DATA {"
				+ "    iz:XYZ a iz:Predmet. "
				+ "}";
		UpdateAction.parseExecute(insertString, model);

		// DELETE
        String deleteString = ""
        		+ "PREFIX iz: <http://www.ftn.uns.ac.rs/iz#> "
        		+ "DELETE "
        		+ "WHERE {"
        		+ "    iz:XYZ ?x ?y ." 
        		+ "}";
        UpdateAction.parseExecute(deleteString, model);
        
		// SELECT
		String queryString = ""
				+ "PREFIX iz: <http://www.ftn.uns.ac.rs/iz#> "
				+ "SELECT ?profesor ?imeProfesora "
				+ "WHERE {"
				+ "    ?predmet a iz:Predmet ;"
				+ "        iz:profesor ?profesor ."
				+ "    ?profesor a iz:Osoba ;"
				+ "        iz:ime ?imeProfesora ."
				+ "}";
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution() ;
			Resource resource = solution.getResource("profesor");
			Literal literal = solution.getLiteral("imeProfesora");
			System.out.println(resource.getURI());
			System.out.println(literal.getString());
		}
	}

}
