/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author dell
 */
public class Bayes {

    private static ArrayList<Columna> columnas = new ArrayList<Columna>();
    static Columna ultimaColumna;
    static double prob[][];

    static double pp;
    static double npp;
    static double total;

    static int flag = 0;
    static int flag1 = 0;

    static double play_N = 1;
    static double notplay_N = 1;

    private static void setData(File data) {
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(data));
            line = br.readLine();
            String[] names = line.split(",");
            for (String name : names) {
                columnas.add(new Columna(name));
            }
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                for (int i = 0; i < datos.length; i++) {
                    columnas.get(i).datos.add(datos[i]);
                }
            }
        } catch (Exception e) {
        }
    }    
    
    private static void setPP_NPP(){
        ultimaColumna = columnas.get(columnas.size()-1);
        for (String dato : ultimaColumna.datos) {
            if(dato.equalsIgnoreCase("1")){
                pp += 1;
            }else{
                npp += 1;
            }
        }
        pp = pp / ultimaColumna.datos.size();
        npp = npp / ultimaColumna.datos.size();
    }

    static void cal_N(int a) {
        if (a == 1) {
            for (int i = 0; i < prob.length; ++i) {
                play_N *= prob[i][0];
            }
            play_N *= pp;            
        } else {
            for (int i = 0; i < prob.length; ++i) {
                notplay_N *= prob[i][1];
            }
            notplay_N *= npp;            
        }
    }
    
    static double cal_noplay_prob(char ch) {
        double prob = 0;
        double count = 0;
        Columna columna = columnas.get(flag1);
        for (int i = 0; i < columna.datos.size(); i++) {
            String dato = columna.datos.get(i);
            String classi = ultimaColumna.datos.get(i);
            if(dato.compareTo(ch+"")==0 && classi.compareTo("0")==0){
                count++;
            }            
        }
        prob = count / (npp*ultimaColumna.datos.size());
        flag1++;
        return prob;        
    }
    
    static double cal_play_prob(char ch) {
        double prob = 0;
        double count = 0;
        Columna columna = columnas.get(flag);
        for (int i = 0; i < columna.datos.size(); i++) {
            String dato = columna.datos.get(i);
            String classi = ultimaColumna.datos.get(i);
            if(dato.compareTo(ch+"")==0 && classi.compareTo("1")==0){
                count++;
            }            
        }
        prob = count / (pp*ultimaColumna.datos.size());
        flag++;
        return prob;        
    }
    
    public static void printMatrix(Object[][] m) {
        try {
            int rows = m.length;
            int columns = m[0].length;
            String str = "|\t";

            for (int i = 0; i < rows; i++) {
                
                for (int j = 0; j < columns; j++) {
                    if(m[i][j]==null){
                        str += " " + "\t";
                    }else{
                        str += m[i][j].toString() + "\t";
                    }
                }
                System.out.println(str + "|");
                str = "|\t";
            }

        } catch (Exception e) {
            System.out.println("Matrix is empty!!");
        }
    }
    
    
    public static void main(String[] args) {
        JFileChooser fileIn = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT File", "txt");
        fileIn.setFileFilter(filter);
        int returnVal = fileIn.showOpenDialog(fileIn);
        File data = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            data = fileIn.getSelectedFile();
        } else {
            System.out.println("No escogio ningun archivo");
        }
        if (data != null) {
            setData(data);            
            prob = new double[columnas.size()-1][2];
            setPP_NPP();
            System.out.println("Datos: ");
            String[][] datos = new String[ultimaColumna.datos.size()][columnas.size()];
            for(int i = 0 ; i < columnas.size(); i++){
                datos[0][i] = columnas.get(i).name.substring(0, 3);
            }            
            for(int i = 1 ; i < ultimaColumna.datos.size() ; i++){                
                for(int j = 0 ; j < columnas.size(); j++){
                    datos[i][j] = columnas.get(j).datos.get(i);
                }                
            }      
            printMatrix(datos);
            for(int i = 0 ; i < columnas.size(); i++){
                System.out.println(columnas.get(i).name.substring(0, 3) + ": " + columnas.get(i).name);                
            }
            System.out.println("");
            System.out.println("Outlook: Sunny=S Overcast=O Rain=R ");
            System.out.println("Temperature: Hot=H Mild=M Cool=C");
            System.out.println("Humidity: Peak=P Normal=N");
            System.out.println("Windy: True=T False=F");
            System.out.println("class1: Play=P   class2:Not Play=NP");
            System.out.println("\nEnter your input: example. t={rain,hot,peak,false} input will be R,H,P,F");
            Scanner scr = new Scanner(System.in);
            String s = scr.nextLine();            
            char ch;
            int count = 0;
            for (int i = 0; i < s.length() + 1; i+=2) {                
                ch = s.charAt(i);
                prob[count][0] = cal_play_prob(ch);
                prob[count][1] = cal_noplay_prob(ch);
                count++;
            }
            cal_N(1);
            cal_N(2);
            
            double pt = play_N + notplay_N;
            
            double prob_of_play = 0;
            double prob_of_noplay = 0;
            
            prob_of_play = play_N / pt;
            prob_of_noplay = notplay_N / pt;
            
            System.out.println("\nProbability of play " + prob_of_play);
            System.out.println("\nProbability of NO play " + prob_of_noplay);
            
            if (prob_of_play > prob_of_noplay) {
            System.out.println("\nThe new tuple classified under \"PLAY\" category.Hence there will be play!!!");
            } else {
                System.out.println("\nThe new tuple classified under \"NO PLAY\" category.Hence there will be NO play.");
            }
        }
    }

}
