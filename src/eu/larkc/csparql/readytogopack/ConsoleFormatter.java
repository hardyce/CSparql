package eu.larkc.csparql.readytogopack;
/*
 * @(#)CounterFormatter.java   1.0   01/ott/2009
 *
 * Copyright 2009-2009 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */



import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.common.streams.format.GenericObservable;
import eu.larkc.csparql.core.ResultFormatter;
import ie.tcd.cs.nembes.coror.Coror;
import ie.tcd.cs.nembes.coror.graph.Factory;
import ie.tcd.cs.nembes.coror.graph.Node;
import ie.tcd.cs.nembes.coror.graph.Triple;
import ie.tcd.cs.nembes.coror.graph.temporal.TemporalTriple;
import ie.tcd.cs.nembes.coror.util.iterator.ExtendedIterator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleFormatter extends ResultFormatter {

int i=0;
Coror coror;
Model ontModel;
ie.tcd.cs.nembes.coror.graph.Graph Graph;
Graph graph;
String s;
String p;
String o;
Node subj;
Node pred;
Node obj;
Triple trip;
Runtime rt;
com.hp.hpl.jena.graph.Triple next;
com.hp.hpl.jena.graph.Node subject;
com.hp.hpl.jena.graph.Node predicate;
com.hp.hpl.jena.graph.Node object;

	@Override
	public void update(final GenericObservable<RDFTable> observed, final RDFTable q) {
           
//Coror reasoner = new Coror("D:/working/NetBeansProjects/Coror/resources/reasoner.config");


//Reasoner reasoner =  ReasonerRegistry.getOWLReasoner();
coror = new Coror("D:/working/NetBeansProjects/Coror/resources/reasoner.config");

//Model schema=ModelFactory.createDefaultModel();
ontModel = ModelFactory.createDefaultModel();
Graph = Factory.createDefaultGraph();


//InputStream inStream = FileManager.get().open( "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\ADMttl_v2.owl");
//schema.read(inStream,"C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\ADMttl_v2.owl","TURTLE");
//ontModel.read(inStream,"C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\ADMttl_v2.owl","TURTLE");
//ontModel.write(System.out,"N-TRIPLE");
//reasoner.bindSchema(schema);
//ontModel.read(in,"C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\ADMttl_v2.owl","N-TRIPLE");
for(int i=1;i<=4; i++){
    InputStream in = FileManager.get().open( "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\devices\\TestFemto00"+i );
ontModel.read(in,"C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\devices\\TestFemto00"+i,"N-TRIPLE");
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ConsoleFormatter.class.getName()).log(Level.SEVERE, null, ex);
                }
}

        graph = ontModel.getGraph();
        //ontModel.close();
        com.hp.hpl.jena.util.iterator.ExtendedIterator<com.hp.hpl.jena.graph.Triple> find = graph.find(com.hp.hpl.jena.graph.Node.ANY, com.hp.hpl.jena.graph.Node.ANY, com.hp.hpl.jena.graph.Node.ANY);
        while(find.hasNext()){
            
                next = find.next();
                ///System.out.println(next.getSubject().toString());
                //System.out.println(next.getPredicate().toString());
                // System.out.println(next.getObject().toString());
                //System.out.println(next.getSubject().isBlank());
                 subject = next.getSubject();
                 predicate = next.getPredicate();
                 object = next.getObject();
                
                s = subject.toString();
                p = predicate.toString();
                o = object.toString();
                //System.out.println(o);
                if(subject.isBlank()){s="_"+s;}
                if(predicate.isBlank()){p="_"+p;}
                if(object.isBlank()){o="_"+o;}
                subj= Node.create(s);
                
                pred= Node.create(p);
                
                obj= Node.create(o);
                
                trip = new Triple(subj,pred,obj);
                //System.out.println(trip.toString());
                Graph.add(trip);
        }
        find.close();
        graph.close();
        ontModel.close();
		System.out.println("-------"+ q.size() + " results at SystemTime=["+System.currentTimeMillis()+"]--------");
                
		for (final RDFTuple t : q) {
                    
			//System.out.println(t.toString());
                //Resource createOntResource = ontModel.createResource(t.get(0));
                    //System.out.println(t.get(0));
                    subj = Node.create(t.get(0));
                //Property createProperty = ontModel.createProperty(t.get(1));
                    //System.out.println(t.get(1));
                    pred = Node.create(t.get(1));
                //Literal createLiteral = ontModel.createLiteral(t.get(2));
                   // System.out.println(t.get(2));
                    obj = Node.create(t.get(2));
                    trip = new Triple(subj,pred,obj);
                //ontModel.addLiteral(createOntResource, createProperty, createLiteral);
                
                Graph.add(trip);
                //System.out.println(ontModel.size());
             
              }
                
               coror.setOntology(Graph);
               
               //Graph.close();
        //long startTime = System.currentTimeMillis();
        //Graph graph = ontModel.getGraph();
        //reason.setOntGraph(graph);
        //InfModel infmod = ModelFactory.createInfModel(reasoner,ontModel);
        //infmod.write(System.out,"N-TRIPLE");
        coror.startReasoner();
        Graph.close();
        //long endTime = System.currentTimeMillis();
        //long compTime = endTime-startTime;
        //System.out.println("Reasoning time: "+compTime);
        rt = Runtime.getRuntime();
        System.out.println("Memory used:"+rt.totalMemory());
        
                //FileOutputStream fop;
                //for(ExtendedIterator it = coror.getInfGraph().find(Node.ANY, Node.ANY, Node.ANY); it.hasNext();){
            //System.err.println(it.next());
        //}
                /*
        try {
            
            fop = new FileOutputStream("puppy"+i+".rdf");
            i++;
            //infmod.write(fop,"N-TRIPLE");
           
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsoleFormatter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
		System.out.println();
*/
	}
}
