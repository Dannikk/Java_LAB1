package utils;

import java.io.FileInputStream;
import java.io.IOException;

public class MyReader {

    private FileInputStream inputStream;
    private int bufferSize;

    public MyReader(String filepath, int bufferSize){
        this.bufferSize = bufferSize;
        try {
            inputStream = new FileInputStream(filepath);
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    public int readByteArray(byte[] slice){
        try{
            if (inputStream.available() >= bufferSize) {
                return inputStream.read(slice);
            }
            else{
                int length = inputStream.available();
                return inputStream.read(slice);
            }
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
        }
        return 0;
    }

    public int available(){
        int available_bytes = 0;
        try {
            available_bytes = inputStream.available();
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        return available_bytes;
    }

    public void closeMyReader(){
        try{
            inputStream.close();
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
