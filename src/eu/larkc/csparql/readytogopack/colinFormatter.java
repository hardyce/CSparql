/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.larkc.csparql.readytogopack;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.tdb.store.QuadTable;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.streams.format.GenericObservable;
import eu.larkc.csparql.core.ResultFormatter;
import java.util.Iterator;

/**
 *
 * @author Colin
 */
public class colinFormatter extends QuadFormatter{

   

    @Override
    public void update(GenericObservable<QuadTable> go, QuadTable t) {
        Iterator<Quad> find = t.find(Node.ANY, Node.ANY, Node.ANY, Node.ANY);
        while(find.hasNext()){
            Quad next = find.next();
            System.out.println(next.toString());
        }
    }
    
}
