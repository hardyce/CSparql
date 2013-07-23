/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.larkc.csparql.readytogopack;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.impl.LiteralLabel;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import eu.larkc.csparql.engine.CsparqlEngine;
import eu.larkc.csparql.engine.CsparqlEngineImpl;
import eu.larkc.csparql.engine.CsparqlQueryResultProxy;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Colin
 */
public class SR {
    String ontoLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\";
    String devLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\devices\\";
    String paramLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\";
   
    int noOfDevices = 4;
    int noOfBatches = 20;
    
    public static void main(String args[]) throws ParseException{
        String query ="REGISTER QUERY StreamQuery AS "
                                        + "PREFIX ex: <http://localhost/default/tr069#> "
                                        + "PREFIX f: <http://larkc.eu/csparql/sparql/jena/ext#> "
					+ "SELECT ?s ?p ?o (MAX(f:timestamp(?s,?p,?o)) AS ?ts)"
					+ "FROM STREAM <http://localhost/default/tr069> [RANGE 5s STEP 1s] "
					+ "WHERE { ?s ?p ?o . "
                                        + " ?s ?p ?o1 . "
                                        + "FILTER(f:timestamp(?s,?p,?o)=?ts)} GROUP BY ?s ?p " 
                                        
                                        ;
        
        String query1 ="REGISTER QUERY StreamQuery AS "
                                        + "PREFIX ex: <http://localhost/default/tr069#> "
                                        + "PREFIX f: <http://larkc.eu/csparql/sparql/jena/ext#> "
					+ "SELECT DISTINCT ?s ?p ?o ?ts "
					+ "FROM STREAM <http://localhost/default/tr069> [RANGE 2s STEP 1s] "
					+ "WHERE { ?s ?p ?o . "
                                        + "{SELECT ?s ?p (MAX(f:timestamp(?s,?p,?o)) AS ?ts) WHERE{?s ?p ?o} GROUP BY ?s ?p}"
                                        + " FILTER(f:timestamp(?s,?p,?o)=?ts) } " 
                                        ;
        
        String query3 ="REGISTER QUERY StreamQuery COMPUTED EVERY 1s AS "
                                        + "PREFIX ex: <http://localhost/default/tr069#> "
                                        + "PREFIX f: <http://larkc.eu/csparql/sparql/jena/ext#> "
					+ "SELECT ?s ?p ?o (f:timestamp(?s,?p,?o) AS ?ts)"
					+ "FROM STREAM <http://localhost/default/tr069> [RANGE 5s step 1s] "
					+ "WHERE { ?s ?p ?o }"
                                        ;
        
        String query2 ="REGISTER QUERY StreamQuery AS "
                                        + "PREFIX ex: <http://localhost/default/tr069#> "
                                        + "PREFIX f: <http://larkc.eu/csparql/sparql/jena/ext#> "
					+ "SELECT DISTINCT ?s ?p ?o "
					+ "FROM STREAM <http://localhost/default/tr069> [RANGE 5s STEP 1s] "
                                        + "FROM <file:TestFemto002> "
					+ "WHERE { ?s ?p ?o . "
                                        + "{SELECT ?s ?p (MAX(f:timestamp(?s,?p,?o)) AS ?ts) WHERE{?s ?p ?o} GROUP BY ?s ?p}"
                                        + " FILTER(f:timestamp(?s,?p,?o)=?ts) } " ; 
        
        StreamCollector rd= new StreamCollector("http://localhost/default/tr069");
        
        
        final Thread t = new Thread((Runnable) rd);
        System.out.println("Starting Stream cCollector");
        t.start();
       

        CsparqlEngine engine = new CsparqlEngineImpl();
        
        engine.initialize(true);
        System.out.println("Registering Stream");
        engine.registerStream(rd);
        
       
        System.out.println("Registering Query");
        CsparqlQueryResultProxy c1 = engine.registerQuery(query3);
        System.out.println("Adding Observer");
        c1.addObserver(new newConsoleFormatter());
        
       
        
    }
 public void prepOntology(){
     
        
     Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
OntModel ontModel = ModelFactory.createOntologyModel();
System.out.println("Getting TR069 ontology");

reasoner.bindSchema(ontModel);
System.out.println("Getting generated Devices");

for(int i=1;i<noOfDevices;i++){
    InputStream in = FileManager.get().open( devLocation+"TestFemto00"+i );
    
          
        if (in == null) {
            throw new IllegalArgumentException( devLocation+"TestFemto00"+i + " not found");
}
        
        ontModel.read(in,devLocation+"TestFemto00"+i,"N-TRIPLE" );
}
System.out.println("Getting Parameter Values");
 
java.text.DecimalFormat nft = new  
java.text.DecimalFormat("#00.###");  
nft.setDecimalSeparatorAlwaysShown(false);
for(int i=1;i<noOfBatches;i++){
for(int j =1;j<=noOfDevices;j++)
{
    InputStream in = FileManager.get().open( paramLocation+"TestFemto0"+nft.format(j)+"_data_batch_0"+nft.format(i) );
        if (in == null) {
            throw new IllegalArgumentException(devLocation+"TestFemto0"+nft.format(i) + " not found");
}
        
        ontModel.read(in, paramLocation+"TestFemto0"+nft.format(j)+"_data_batch_0"+nft.format(i),"N-TRIPLE");
        Graph graph = ontModel.getGraph();
        
        StmtIterator listStatements = ontModel.listStatements();
        ExtendedIterator<Triple> find = graph.find(Node.ANY, Node.ANY, Node.ANY);
        List<Quad> mylist = new ArrayList<Quad>();
        while(find.hasNext()){
            int k=0;
        Triple next = find.next();
        Node node = Node.createLiteral(String.valueOf(k));
        Quad q = new Quad(node,next);
        
        mylist.add(q);
        
        }
        int time = 0;
        int window = 10;
        for(int l =0;l<mylist.size();l++){
            
        Quad get = mylist.get(l);
        Node n = get.getGraph();
        LiteralLabel literal = n.getLiteral();
        int parseInt = Integer.parseInt(literal.toString());
        if(parseInt<time+window&&parseInt>time){
            
        }
        }
        
        
        OntModel ontts = ModelFactory.createOntologyModel();
        
        InfModel infmod = ModelFactory.createInfModel(reasoner,ontModel);
        Model deductionsModel = infmod.getDeductionsModel();
        
           FileOutputStream fop;
        try {
            fop = new FileOutputStream("naive"+j+i+".rdf");
            deductionsModel.write(fop);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
}
}
}   
}
