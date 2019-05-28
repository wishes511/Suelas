/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author GATEWAY1- 14/05/2019
 */
public class Copiaarchivo extends Thread {

    @Override
    public void run() {
        try {
            File carpeta = new File("h:\\");
            if(!carpeta.exists()){// valida si la unidad de red existe
                // si la unidad no existe se intentara crear mediante el metodo con el string ya definido con usuario y contraseña
                nuevaunidad("net use H: \\\\192.168.6.14\\general /USER:Workgroup\\general general");
                // si da falso se intentara crear la unidad pero sin grupo de usuario y contraseña    
                    nuevaunidad("net use H: \\\\192.168.6.14\\general ");
            }
//          ****** Comienza copia de carpetas y archivos *******           

            File fichero,fichero2;                
                //si lo que encontro con lo de la bd son diferentes
                    fichero = new File("h:\\sistemas\\dist\\comisiones\\reportes");
                    fichero2 = new File("c:\\af\\comision");
                    if(fichero.exists() && fichero2.exists()){
                    data();
                    }else{
                    fichero.mkdir();
                    fichero2.mkdir();
                    data();
                    }
                    
                    
        } catch (Exception ex) {
            Logger.getLogger(Copiaarchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void data() {
        try {
            int i = 0;// contador principal
            ArrayList<Directorio> arr = new ArrayList<>();// arreglo de directorio
            Directorio d;
            d = new Directorio();
            d.setDirectorio("");//nombre de directorio
            d.setDireccion("h:\\sistemas\\dist\\comisiones\\reportes");//direccion de ruta origen
            d.setDireccionc("c:\\af\\comision");//direccion disco local
            d.setPosicion(i);//posicion en que se encuentra
            arr.add(d);// añadir directorio al arreglo
            File base = new File("c:\\af\\comision");// directorio base
            while (i < arr.size()) {
                File archivo = new File(arr.get(i).getDireccionc() + arr.get(i).getDirectorio());
                //verifica desde el arreglo la ruta del discolocal (C:\tsmanager)
                File f = new File(arr.get(i).getDireccion());// archivo origen (h:\sistemas\tsmanager)
                File[] fi = f.listFiles();//lista el numero de archivos encontrados
                if (f.listFiles() == null) {//si no hay nada en el directorio solo salta en 1 el contador principal
                    i++;
                } else {
                    if (!base.exists()) {// si el archivo base no se encuentra, lo crea
                        base.mkdir();
                    }
                    for (int j = 0; j < fi.length; j++) {// el numero total de archivos que encontro en el directorio
                        String cadena = fi[j].toString();// tomando el registro en la posicion respecto a j
                        String cadena2 = "";// variable que almacenara el nombre del archivo
//obtendremos solo el nombre del archivo iniciando la cadena desde el final almacenando caracter por caracter,
//y si encuentra un '\\' detendra el ciclo.
                        for (int x = cadena.length() - 1; x > 0; x--) {
                            if (cadena.charAt(x) == '\\') {
                                x = 0;
                            } else {
                                cadena2 = cadena.charAt(x) + cadena2;
                            }
                        }
                        if (fi[j].isDirectory()) {// si es un directorio
//asignaremos a archivo la ruta de Direccionc+directorio+nombrearchivo, (c:\+tsmanager\+conf.data)                            
                            archivo = new File(arr.get(i).getDireccionc() + arr.get(i).getDirectorio() + "\\" + cadena2);
//se crea un nuevo objeto de tipo directorio para seguir con la copia de archivos y almacenar temporalmente lo que es el directorio
//en el arreglo de objetos. Directorio es el nombre del archivo, direccion la ruta de origen, direccion c la ruta por default en local
                            d = new Directorio();
                            d.setDirectorio(cadena2);
                            d.setDireccion(fi[j].toString());
                            d.setDireccionc(arr.get(i).getDireccionc() + arr.get(i).getDirectorio() + "\\");
                            d.setPosicion(i);
                            arr.add(d);
                            archivo.mkdir();// se crea el archivo con la ruta ya especificada
                        } else {
//si no es un directorio, se hara una se asignara la ruta del archivo origen que viene desde el arreglo de archivos(fi), hacia el destino que
//es la ruta Direccionc+directorio+nombrearchivo, (c:\+tsmanager\+conf.data)
                            Path origen = FileSystems.getDefault().getPath(fi[j].toString());
                            Path salida = FileSystems.getDefault().getPath(arr.get(i).getDireccionc() + arr.get(i).getDirectorio() + "\\" + cadena2);
                            Files.copy(origen, salida, StandardCopyOption.REPLACE_EXISTING);// se realiza la copia de el archivo
                        }
                    }
                    i++;//incremento de contador principal
                }
            }
//            JOptionPane.showMessageDialog(null, "Copiado de archivos completo!", "ATHLETIC", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo copiar debido a:\n" + e, "ATHLETIC", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(null, "Unidades para dar de alta: \n H:/sistemas/dist/reloj \n q:/reloj/casmovre", "ATHLETIC", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    
    private boolean nuevaunidad(String comando){
        try {
            Process process = Runtime.getRuntime().exec(comando);
            InputStream br = process.getInputStream();
            int i = br.read();
            String out = "";
            while (i != -1) {
                out = out + (char) i;
                //System.out.print((char) i);
                i = br.read();
            }
            return !out.contains("The command completed successfully") || !out.contains("Se ha completado el comando correctamente");
        } catch (IOException ex) {
            Logger.getLogger(Copiaarchivo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
