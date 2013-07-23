/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.larkc.csparql.readytogopack;

import datagenerator.DataGenerator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Colin
 */
public class newFileStream {

    static int teams = 1;
    static int koalas = 2;
    static int university = 3;
    static int beer = 4;
    static int mindswapper = 5;
    static int mad_cows = 6;
    static int biopax = 7;
    static int food = 8;
    static int isIn=9;
    
    static int mode = food;
    static String subj;
    static String pred;
    static String obj;
    static int i = 0;

    public static void main(String args[]) throws InterruptedException {
        List<String[]> myList = new ArrayList<String[]>();

        try {

            boolean keepRunning = true;
            ServerSocket serversocket = new ServerSocket(5003);
            Socket accept = serversocket.accept();
            OutputStream outputStream = accept.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);


            while (keepRunning) {
                // if(i%5==0){
                //   System.out.println("sleeping"+i);
                //Thread.sleep(2000);
                //}
                //Thread.sleep(2000);
                //System.out.println(myList.size());
                myList = setUp(mode, myList);
                //System.out.println(myList.size());
                Thread.sleep(200);
                for (int j = 0; j < myList.size(); j++) {
                    //System.out.println(myList.get(j));
                    oos.writeObject(myList.get(j));
                }
                myList.clear();
                //System.out.println(subj+pred+obj);
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(newFileStream.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List setUp(int mode, List list) {
        String subject;
        String predicate;
        String object;
        String subject1;
        String predicate1;
        String object1;
        String subject2;
        String predicate2;
        String object2;
        String subject3;
        String predicate3;
        String object3;
        String subject4;
        String predicate4;
        String object4;
        String subject5;
        String predicate5;
        String object5;
        
        String[] triparray1 = new String[4];
        String[] triparray2 = new String[4];
        String[] triparray3 = new String[4];
        String[] triparray4 = new String[4];
        String[] triparray5 = new String[4];
        String[] triparray6 = new String[4];
        switch (mode) {
            case 1:
                subject = "http://owl.man.ac.uk/2005/sssw/teams#john" + i;
                subject1 = "http://owl.man.ac.uk/2005/sssw/teams#jane" + i;
                predicate = "http://owl.man.ac.uk/2005/sssw/teams#isMemberOf";
                object = "http://owl.man.ac.uk/2005/sssw/teams#OntologyFC";
                triparray1[0] = subject;
                //System.out.println(subject);
                triparray1[1] = predicate;
                triparray1[2] = object;
                triparray2[0] = subject1;
                triparray2[1] = predicate;
                triparray2[2] = object;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                break;
            case 2:
                subject = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#student" + i;
                subject1 = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#koala" + i;
                predicate = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object1 = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#Marsupials";
                object = "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#GraduateStudent";
                triparray1[0] = subject;
                triparray1[1] = predicate;
                triparray1[2] = object;
                triparray2[0] = subject1;
                triparray2[1] = predicate;
                triparray2[2] = object1;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                break;
            case 3:
                subject = "http://www.mindswap.org/ontologies/debugging/university.owl#professor" + i;
                predicate = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object = "http://www.mindswap.org/ontologies/debugging/university.owl#Professor";
                subject1 = "http://www.mindswap.org/ontologies/debugging/university.owl#universiry" + i;
                object1 = "http://www.mindswap.org/ontologies/debugging/university.owl#University";
                triparray1[0] = subject;
                triparray1[1] = predicate;
                triparray1[2] = object;
                triparray2[0] = subject1;
                triparray2[1] = predicate;
                triparray2[2] = object1;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                break;
            case 4:
                subject = "http://www.purl.org/net/ontology/beer#beer" + i;
                predicate = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object = "http://www.purl.org/net/ontology/beer#Beer";
                predicate1 = "http://www.purl.org/net/ontology/beer#hasAlcoholicContent";
                object1 = "5";
                predicate2 = "http://www.purl.org/net/ontology/beer#brewedBy";
                object2 = "http://www.purl.org/net/ontology/beer#brewery" + i;
                triparray1[0] = subject;
                triparray1[1] = predicate;
                triparray1[2] = object;
                triparray2[0] = subject;
                triparray2[1] = predicate1;
                triparray2[2] = object1;
                triparray3[0] = subject;
                triparray3[1] = predicate2;
                triparray3[2] = object2;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                triparray3[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                list.add(triparray3);
                break;
            case 5:
                subject = "http://www.mindswap.org/2003/owl/mindswap#affiliate" + i;
                predicate = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object = "http://www.mindswap.org/2003/owl/mindswap#Affiliate";
                subject1 = "http://www.mindswap.org/2003/owl/mindswap#graduatestudent" + i;
                object1 = "http://www.mindswap.org/2003/owl/mindswap#GraduateStudent";
                triparray1[0] = subject;
                triparray1[1] = predicate;
                triparray1[2] = object;
                triparray2[0] = subject1;
                triparray2[1] = predicate;
                triparray2[2] = object1;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                triparray3[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                break;
            case 6:
                subject = "http://cohse.semanticweb.org/ontologies/people#person" + i;
                predicate = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object = "http://cohse.semanticweb.org/ontologies/people#person";
                subject1 = "http://cohse.semanticweb.org/ontologies/people#adult" + i;
                object1 = "http://cohse.semanticweb.org/ontologies/people#adult";
                predicate1 = "http://cohse.semanticweb.org/ontologies/people#eats";
                object2 = "http://cohse.semanticweb.org/ontologies/people#pie";
                predicate2 = "http://cohse.semanticweb.org/ontologies/people#has+mother";
                triparray1[0] = subject;
                triparray1[1] = predicate;
                triparray1[2] = object;
                triparray2[0] = subject1;
                triparray2[1] = predicate;
                triparray2[2] = object1;
                triparray3[0] = subject;
                triparray3[1] = predicate1;
                triparray3[2] = object2;
                triparray4[0] = subject;
                triparray4[1] = predicate2;
                triparray4[2] = subject1;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                triparray3[3] = String.valueOf(System.currentTimeMillis());
                triparray4[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                list.add(triparray3);
                list.add(triparray4);
                break;
            case 7:
                subject = "http://www.biopax.org/release/biopax-level1.owl#control" + i;
                predicate = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object = "http://www.biopax.org/release/biopax-level1.owl#control";
                predicate1 = "http://www.biopax.org/release/biopax-level1.owl#CONTROLLER";
                subject1 = "http://www.biopax.org/release/biopax-level1.owl#physicalEntityParticipant" + i;
                object1 = "http://www.biopax.org/release/biopax-level1.owl#physicalEntityParticipant";
                triparray1[0] = subject;
                triparray1[1] = predicate;
                triparray1[2] = object;
                triparray2[0] = subject1;
                triparray2[1] = predicate;
                triparray2[2] = object1;
                triparray3[0] = subject;
                triparray3[1] = predicate1;
                triparray3[2] = subject1;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                triparray3[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                list.add(triparray3);
                break;
            case 8:
                subject = "http://www.w3.org/2001/sw/WebOnt/guide-src/food#MealCourse" + i;
                predicate = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object = "http://www.w3.org/2001/sw/WebOnt/guide-src/food#MealCourse";
                subject1 = "http://www.w3.org/2001/sw/WebOnt/guide-src/food#SweetFruit" + i;
                object1 = "http://www.w3.org/2001/sw/WebOnt/guide-src/food#SweetFruit";
                subject2 = "http://www.w3.org/2001/sw/WebOnt/guide-src/food#CheeseNutsDessert" + i;
                object2 = "http://www.w3.org/2001/sw/WebOnt/guide-src/food#CheeseNutsDessert";
                triparray1[0] = subject;
                triparray1[1] = predicate;
                triparray1[2] = object;
                triparray2[0] = subject1;
                triparray2[1] = predicate;
                triparray2[2] = object1;
                triparray3[0] = subject2;
                triparray3[1] = predicate;
                triparray3[2] = object2;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                triparray3[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                list.add(triparray3);
                break;
                
            case 9:
                subject="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#A"+i;
                predicate="http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#A";
                subject1="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#B"+i;
                predicate1="http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object1="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#B";
                subject2="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#C"+i;
                predicate2="http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
                object2="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#C";
                triparray1[0]=subject;
                triparray1[1]=predicate;
                triparray1[2]=object;
                triparray2[0]=subject1;
                triparray2[1]=predicate1;
                triparray2[2]=object1;
                triparray3[0]=subject2;
                triparray3[1]=predicate2;
                triparray3[2]=object2;
                triparray1[3] = String.valueOf(System.currentTimeMillis());
                triparray2[3] = String.valueOf(System.currentTimeMillis());
                triparray3[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray1);
                list.add(triparray2);
                list.add(triparray3);
                
                if(i>0){
                subject3="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#A"+i;
                predicate3="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#isIn";
                object3="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#A"+(i-1);
                subject4="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#B"+i;
                predicate4="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#isIn";
                object4="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#B"+(i-1);
                subject5="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#C"+i;
                predicate5="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#isIn";
                object5="http://www.semanticweb.org/colin/ontologies/2013/6/untitled-ontology-6#C"+(i-1); 
                triparray4[0]=subject3;
                triparray4[1]=predicate3;
                triparray4[2]=object3;
                triparray5[0]=subject4;
                triparray5[1]=predicate4;
                triparray5[2]=object4;
                triparray6[0]=subject5;
                triparray6[1]=predicate5;
                triparray6[2]=object5;
                triparray4[3] = String.valueOf(System.currentTimeMillis());
                triparray5[3] = String.valueOf(System.currentTimeMillis());
                triparray6[3] = String.valueOf(System.currentTimeMillis());
                list.add(triparray4);
                list.add(triparray5);
                list.add(triparray6);
                }

        }
        return list;
    }
}
