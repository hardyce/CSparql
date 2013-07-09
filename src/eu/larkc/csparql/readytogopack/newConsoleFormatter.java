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
import com.hp.hpl.jena.reasoner.rulesys.impl.RETEEngine;
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
import ie.tcd.cs.nembes.coror.rdf.model.GraphWriter;
import ie.tcd.cs.nembes.coror.reasoner.FGraph;
import ie.tcd.cs.nembes.coror.reasoner.InfGraph;
import ie.tcd.cs.nembes.coror.reasoner.rulesys.RETERuleInfGraph;
import ie.tcd.cs.nembes.coror.util.iterator.ExtendedIterator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class newConsoleFormatter extends ResultFormatter {
   

Coror coror = new Coror("C:/Users/Colin/Coror/resources/reasoner.config");
int i;
Node subj;
Node pred;
Node obj;
Triple trip;
InfGraph infGraph;
ie.tcd.cs.nembes.coror.graph.Graph schemaGraph;
ie.tcd.cs.nembes.coror.graph.Graph Graph = Factory.createDefaultGraph();;
    private GraphWriter graphWriter;
	@Override
	public void update(final GenericObservable<RDFTable> observed, final RDFTable q) {
            
            if(i==0){
                System.out.println("initialization");
                coror.loadOntology();
                coror.loadRules();
                //coror.setSchema();
                
                coror.startReasoner();
                infGraph = coror.getInfGraph();
            }
            long startTime = System.currentTimeMillis();
            
           
            
        ie.tcd.cs.nembes.coror.graph.Graph ontGraph = coror.getOntGraph();
            
            
           System.out.println(q.size()+" Triples in window at time "+i);
           long addstart=System.currentTimeMillis();
           int k=0;
           ((RETERuleInfGraph)infGraph).fadd=new FGraph(Factory.createDefaultGraph());
            for (final RDFTuple t : q) {
                    
                //System.out.println(t.toString());
                //Resource createOntResource = ontModel.createResource(t.get(0));
                    //System.out.println(t.get(0));
                    subj = Node.create(t.get(0));
                //Property createProperty = ontModel.createProperty(t.get(1));
                    //System.out.println(t.get(1));
                    pred = Node.create(t.get(1));
                //Literal createLiteral = ontModel.createLiteral(t.get(2));
                    //System.out.println(t.get(2));
                    obj = Node.create(t.get(2));
                    trip = new TemporalTriple(subj,pred,obj,i);
                //ontModel.addLiteral(createOntResource, createProperty, createLiteral);
                System.out.println(trip.toString());
                   long adds = System.currentTimeMillis();
                ((RETERuleInfGraph)infGraph).addnewTriple(trip);
                
                   long adde = System.currentTimeMillis();
                   //System.out.println("add"+k+" "+(adde-adds));
                //System.out.println(ontModel.size());
            k++;
              }
            //((RETERuleInfGraph)coror.getInfGraph()).runAll();
            long addend=System.currentTimeMillis();
            System.out.println("add time "+(addend-addstart));
        System.out.println("Sweeping Graph");
        //infGraph.prepare();
       // System.out.println(infGraph.size());
        
        
        ExtendedIterator it = infGraph.find(Node.ANY, Node.ANY, Node.ANY);
        
       
        long sweepStart=System.currentTimeMillis();
            while(it.hasNext()){
            Object next = it.next();
            //System.out.println(next.toString());
            if(next instanceof TemporalTriple){
            TemporalTriple temp = (TemporalTriple) next;
        
            

            ((RETERuleInfGraph)infGraph).performDeleteTimeTriples(temp,i);
            
          
        
        }
        }
            long endSweepTime=System.currentTimeMillis();
            long startReteTime = System.currentTimeMillis();
            System.out.println("Sweeping RETE");
        ((RETERuleInfGraph)infGraph).sweepRete(i);
        long endReteTime = System.currentTimeMillis();
System.out.println(infGraph.size());
System.out.println("Rebinding");
long startRebindTime = System.currentTimeMillis();
infGraph.rebind();
long endTime = System.currentTimeMillis();
System.out.println("Total re-reasoning time"+(endTime-startTime));
System.out.println("Rebind time"+(endTime-startRebindTime));
System.out.println("RETE time"+(endReteTime-startReteTime));
System.out.println("sweep time"+(endSweepTime-sweepStart));
i++;

        ExtendedIterator find1 = infGraph.find(Node.ANY, Node.create("http://owl.man.ac.uk/2005/sssw/teams#isMemberOf"), Node.ANY);
        while(find1.hasNext()){
            System.out.println(find1.next().toString());
        }
        ExtendedIterator find = infGraph.find(Node.ANY, Node.create("http://owl.man.ac.uk/2005/sssw/teams#hasMember"), Node.ANY);
        while(find.hasNext()){
            System.out.println(find.next().toString());
        }
     
        
   
	}
}
