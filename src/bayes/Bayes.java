/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bayes;

import static bayes.btheo.flag;
import static bayes.btheo.play_N;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author dell
 */
public class Bayes {

    private static ArrayList<Columna> columnas = new ArrayList<Columna>();
    static double prob[][] = new double[4][2];

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

    public static void countNandP() { // cuenta cuentas N y P y el total de datos que hay...
        double[] values = new double[3]; //pos1 contiene PP pos2 contiene NP y pos3 contiene total
        for (String row : columnas.get(columnas.size() - 1).toString().split("=")[1].trim().split(" ")) {
            System.out.println(row);
            if (row.equalsIgnoreCase("1")) {
                values[0] += 1;
            } else if (row.equalsIgnoreCase("0")) {
                values[1] += 1;
            }
            values[2] += 1;
        }
        pp = values[0] / values[2];
        npp = values[1] / values[2];
    }

    static void cal_N(int a) {
        if (a == 1) {
            for (int i = 0; i < 4; ++i) {
                play_N *= prob[i][0];
            }

            play_N *= pp;
            //System.out.println("\nValue of N of play \n"+play_N);
        } else {
            for (int i = 0; i < 4; ++i) {
                notplay_N *= prob[i][1];
            }

            notplay_N *= npp;
            //System.out.println("\nValue of N of No play \n"+notplay_N);
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
            System.out.println(columnas.get(columnas.size() - 1).toString().split("=")[1]);

        }
    }

}
