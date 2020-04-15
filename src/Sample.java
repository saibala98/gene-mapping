
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author project
 */
public class Sample {
    public static void main(String args[]) throws FileNotFoundException, IOException{
        FileInputStream fin=new FileInputStream("D:\\temp\\a.txt");
        FileOutputStream fout=new FileOutputStream("D:\\temp\\b.txt");
        byte b[]=new byte[fin.available()];
        fin.read(b);
        
        String data=new String(b);
        fin.close();
        fout.write(data.getBytes());
        fout.close();
    }
    
}
