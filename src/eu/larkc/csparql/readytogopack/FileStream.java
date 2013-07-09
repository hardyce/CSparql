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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Colin
 */
public class FileStream {
    static String ontoLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\";
    static String devLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\devices\\";
    static String paramLocation = "C:\\Users\\Colin\\DataGen\\DataGenerator\\ont\\data\\";
    static int noOfDevices = 4;
    static int noOfBatches = 20;
    static boolean keepRunning = true;
                                
                                public static void main(String args[]){
                                    Pattern pattern = Pattern.compile(" ");
java.text.DecimalFormat nft = new java.text.DecimalFormat("#00.###");  
nft.setDecimalSeparatorAlwaysShown(false);
        try {
            ServerSocket serversocket = new ServerSocket(5003);
            Socket accept = serversocket.accept();
            OutputStream outputStream = accept.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            String[] triparray;
            while(keepRunning){
                
                
            for(int i=1;i<noOfBatches;i++){
                        FileInputStream fs;
                    for(int j =1;j<=noOfDevices;j++){
        try {
            fs = new FileInputStream(paramLocation+"TestFemto0"+nft.format(j)+"_data_batch_0"+nft.format(i));
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
                            try {
                String readLine;
                while( (readLine = br.readLine())!=null){
                
                readLine = readLine.replaceAll("<", "");
                readLine = readLine.replaceAll(">", "");
                triparray = pattern.split(readLine,0);
                oos.writeObject(triparray);
                
                                                      }
                //System.out.println(readLine);
                                
                            } catch (IOException ex) {
                                Logger.getLogger(FileStream.class.getName()).log(Level.SEVERE, null, ex);
                            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileStream.class.getName()).log(Level.SEVERE, null, ex);
        }

                    }
                    oos.reset();
                              }
            System.out.println("generating new data");
            DataGenerator dg = new DataGenerator();
            dg.generateData();
            }
            System.out.println("done");
            oos.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(FileStream.class.getName()).log(Level.SEVERE, null, ex);
        }


                                }
}
