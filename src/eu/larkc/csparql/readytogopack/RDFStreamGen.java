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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;


public class RDFStreamGen extends RdfStream implements Runnable {

	/** The logger. */
	protected final Logger logger = LoggerFactory
			.getLogger(BasicRDFStreamTestGenerator.class);	

	private int c = 1;
	private boolean keepRunning = false;
        String ontoLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\";
    String devLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\devices\\";
    String paramLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\";
    int noOfDevices = 4;
    int noOfBatches = 20;

	public RDFStreamGen(final String iri) {
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
		keepRunning = true;
		while (keepRunning) {
                    for(int i=1;i<noOfBatches;i++){
                        
                    for(int j =1;j<=noOfDevices;j++){
                            try {
                                FileInputStream fs= new FileInputStream(paramLocation+"TestFemto0"+nft.format(j)+"_data_batch_0"+nft.format(i));
                                BufferedReader br = new BufferedReader(new InputStreamReader(fs));
                            try {
                                
                                while(br.ready()){
                                String triple = br.readLine();
                                triple = triple.replaceAll("<", "");
                                triple = triple.replaceAll(">", "");
                                String[] triparray = triple.split(" ");
                           
                                final RdfQuadruple q = new RdfQuadruple(triparray[0],
					triparray[1], triparray[2], 
					System.currentTimeMillis());
                                        //System.out.println(q.toString());
                                        this.put(q);
                                        
                                }
                            } catch (IOException ex) {
                                java.util.logging.Logger.getLogger(BasicRDFStreamTestGenerator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            } catch (FileNotFoundException ex) {
                                java.util.logging.Logger.getLogger(BasicRDFStreamTestGenerator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(RDFStreamGen.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    
                    }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(RDFStreamGen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                         }
			

			System.out.println("Stream Exhausted");

			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.c++;
		}
	}
}
