package utils;

import java.io.FileOutputStream;
import java.io.IOException;

public class MyWriter {

    private FileOutputStream outputStream;

    public MyWriter(String filepath){
        try{
            outputStream = new FileOutputStream(filepath);
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void writeByteArray(byte[] slice){
        if (slice != null) {
            try {
                outputStream.write(slice);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
        else
            System.out.println("Writing error!");
    }

    public void closeMyWriter(){
        try {
            outputStream.close();
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
