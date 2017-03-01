package ca.mcgill.ecse223.tileo.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistenceObjectStream {

    public static void serialize(String filename, Object object) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            throw new RuntimeException("Could not save data to file'" + filename + ".");
        }
        }

        public static Object deserialize(String filename){
            Object o = null;
            ObjectInputStream in;
            try{
                FileInputStream fileIn = new FileInputStream(filename);
                in = new ObjectInputStream(fileIn);
                o = in.readObject();
                in.close();
                fileIn.close();
            }catch(Exception e){
                o=null;
            }
        return o;
        }

        }
