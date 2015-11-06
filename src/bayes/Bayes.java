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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author dell
 */
public class Bayes {

    private static ArrayList<Columna> columnas = new ArrayList<Columna>();

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

    public static void countNandP() {
        
        for (String row : columnas.get(columnas.size() - 1).toString().toString().split("=")[1].split(" ")) {
            
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
            System.out.println(columnas.get(columnas.size() - 1).toString().split("=")[1].split(" ")[1]);
        }
    }

}
