package eu.larkc.csparql.readytogopack;
/*
 * @(#)TestGenerator.java   1.0   18/set/2009
 *
 * Copyright 2009-2009 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */


import com.hp.hpl.jena.util.FileUtils;


import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class StreamCollector extends RdfStream implements Runnable {

	/** The logger. */
	

	private int c = 1;
        int i=0;
	private boolean keepRunning = false;
        String ontoLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\";
    String devLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\devices\\";
    String paramLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\";
    int noOfDevices = 4;
    int noOfBatches = 20;
    Pattern pattern = Pattern.compile(" ");

	public StreamCollector(final String iri) {
		super(iri);
	}

	public void pleaseStop() {
		keepRunning = false; 
	}
        
        public static void main(String args[]){
        
            RDFStreamGen rdfsg = new RDFStreamGen("test");
            rdfsg.run();
        
        }

	@Override
	public void run() {
            java.text.DecimalFormat nft = new  
java.text.DecimalFormat("#00.###");  
nft.setDecimalSeparatorAlwaysShown(false);
try {
            Socket socket = new Socket("localhost",5003);
            InputStream inputStream = socket.getInputStream();
                             
            ObjectInputStream ois = new ObjectInputStream(inputStream);
		keepRunning = true;
		String[] readObject=null;
                String triple;
                String[] triparray;
                Object obj;
                
                    while(keepRunning){
                            
                            
                            triparray=(String[])ois.readObject();
                            
                            try{
                                while(!(triparray==null)){
                                    
                                    //System.out.println(readObject);
                                
                           
                                 final RdfQuadruple q = new RdfQuadruple(triparray[0],
					triparray[1], triparray[2], 
					System.currentTimeMillis());
                                 i=i+1000000;
                                        //System.out.println(q.toString());
                                        this.put(q);
                                        //System.out.println(q.toString());
                                        obj = ois.readObject();
                                        triparray = (String[]) obj;
                                        
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(StreamCollector.class.getName()).log(Level.SEVERE, null, ex);
                                         }
                               
                                        this.c++;
                                        
                                       
                                        
                                        
                                        
                                }
        }
                            catch(EOFException e){System.out.println("end of stream");}
                    }
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(RDFStreamGen.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    
                    
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(RDFStreamGen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                         
			

			System.out.println("Stream Exhausted");

			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		
                 } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StreamCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            java.util.logging.Logger.getLogger(StreamCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(StreamCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
