/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.larkc.csparql.readytogopack;

import ie.tcd.cs.nembes.coror.Coror;
import ie.tcd.cs.nembes.coror.graph.Factory;
import ie.tcd.cs.nembes.coror.graph.Graph;
import ie.tcd.cs.nembes.coror.graph.Node;
import ie.tcd.cs.nembes.coror.graph.temporal.TemporalTriple;
import ie.tcd.cs.nembes.coror.reasoner.InfGraph;
import ie.tcd.cs.nembes.coror.reasoner.rulesys.RETERuleInfGraph;
import ie.tcd.cs.nembes.coror.reasoner.rulesys.TemporalRETERuleInfGraph;
import ie.tcd.cs.nembes.coror.util.iterator.ExtendedIterator;

/**
 *
 * @author Colin
 */
public class TemporalTest {
    
    public static void main(String args[]){
        int i=7;
    
    Coror coror = new Coror("C:/Users/Colin/Coror/resources/reasoner.config");
    Graph graph = Factory.createDefaultGraph();
    Node subj = Node.create("asd:Person");
    Node pred = Node.create("http://www.w3.org/2000/01/rdf-schema#subClassOf");
    Node obj = Node.create("asd:livingthing");
    Node subj2 = Node.create("asd:Humanoid"); 
    Node obj3 = Node.create("asd:nice");
    TemporalTriple tt = new TemporalTriple(subj,pred,obj,5);
    TemporalTriple ttnew = new TemporalTriple(subj,pred,obj,4);
    Node subj1 = Node.create("asd:stephen");
    Node pred1 = Node.create("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    Node obj1 = Node.create("asd:Person");
    TemporalTriple tthumanoid = new TemporalTriple(subj2, pred, obj, 9);
    TemporalTriple stephenhumanoid = new TemporalTriple(subj1,pred1,subj2,8);
    TemporalTriple tt1 = new TemporalTriple(subj1,pred1,obj1,6);
    TemporalTriple ttsame = new TemporalTriple(subj1,pred1,obj,7);
    TemporalTriple tt56= new TemporalTriple(subj1,pred1,obj3,20);
    graph.add(tt);
    graph.add(ttnew);
    graph.add(tt1);
    coror.setOntology(graph);
    graph.add(ttsame);
    graph.add(tthumanoid);
    graph.add(stephenhumanoid);
    
    
    
    coror.startReasoner();
        InfGraph infGraph = coror.getInfGraph();
    for(ExtendedIterator it = infGraph.find(Node.ANY, Node.ANY, Node.ANY); it.hasNext();){
            System.err.println(it.next());
        }
    
    graph.add(tt56);
    infGraph.rebind();
    System.out.println("Rebinding");
    //RETERuleInfGraph delgraph =((RETERuleInfGraph)infGraph);
        for(ExtendedIterator it = infGraph.find(Node.ANY, Node.ANY, Node.ANY); it.hasNext();){
            Object next = it.next();
            //System.out.println(next.toString());
            if(next instanceof TemporalTriple){
        TemporalTriple temp = (TemporalTriple) next;
        
            
            //coror.removeTriple(temp);
            //System.out.println("delete "+temp.toString());
            ((RETERuleInfGraph)infGraph).performDeleteTimeTriples(temp,i);
            
            //((TemporalRETERuleInfGraph)infGraph).sweepGraph(0, i);
        
        }
        }
        ((RETERuleInfGraph)infGraph).sweepRete(i);
        
        infGraph.rebind();
        InfGraph infGraph1 = coror.getInfGraph();
    for(ExtendedIterator it = infGraph1.find(Node.ANY, Node.ANY, Node.ANY); it.hasNext();){
            System.err.println(it.next()+" hi");
        }
    
    
        
    
    
    }
    
}
