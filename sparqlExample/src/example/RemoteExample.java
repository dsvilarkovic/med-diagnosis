package example;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class RemoteExample {

	private static final String QUERY_URL = "http://localhost:3030/dataset_name/sparql";
	private static final String UPDATE_URL = "http://localhost:3030/dataset_name/update";
	
	public static void main(String[] args) {
		// INSERT
		String insertString = ""
				+ "PREFIX iz: <http://www.ftn.uns.ac.rs/iz#> "
				+ "INSERT DATA {"
				+ "    iz:XYZ a iz:Predmet. "
				+ "}";
		UpdateRequest updateRequest = UpdateFactory.create(insertString);
        UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, UPDATE_URL);
        updateProcessor.execute();

        // DELETE
        String deleteString = ""
        		+ "PREFIX iz: <http://www.ftn.uns.ac.rs/iz#> "
        		+ "DELETE "
        		+ "WHERE {"
        		+ "    iz:XYZ ?x ?y ." 
        		+ "}";
		UpdateRequest updateRequest2 = UpdateFactory.create(deleteString);
        UpdateProcessor updateProcessor2 = UpdateExecutionFactory.createRemote(updateRequest2, UPDATE_URL);
        updateProcessor2.execute();
        
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
		try {
			QueryExecution qexec = QueryExecutionFactory.sparqlService(QUERY_URL, query);

			ResultSet results = qexec.execSelect() ;
			while (results.hasNext()) {
				QuerySolution solution = results.nextSolution() ;
				Resource resource = solution.getResource("profesor");
				Literal literal = solution.getLiteral("imeProfesora");
				System.out.println(resource.getURI());
				System.out.println(literal.getString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
