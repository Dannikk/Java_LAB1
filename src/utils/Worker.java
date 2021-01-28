package utils;

import Grammar.WorkerGrammar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Worker {

    private Boolean mode;                                //True if encoding, False if decoding
    private final Map<String, String> parameters;
    private final Map<Byte, Byte> sub_table = new HashMap<>();
    private final int sub_table_size = 256;
    private File sub_table_file;

    public Worker(String config_path) {
        parameters = SyntaxParser.Parse(config_path);
        if (!SemanticParse())
            System.out.println("Error!!!");
        MakeSubtableMap();
    }

    private boolean SemanticParse(){
        //checking for existing necessary parameters
        for(String param: WorkerGrammar.parameters_array){
            if (!parameters.containsKey(param)){
                System.out.println("Not enough parameters in executor config!");
                return false;
            }
        }
        //check for existing mode
        if (parameters.get(WorkerGrammar.mode) != null)
            this.mode = Boolean.parseBoolean(parameters.get(WorkerGrammar.mode));
        else {
            System.out.println("Mode not specified!");
            return false;
        }
        //check for existing sub_table_file
        if (parameters.get(WorkerGrammar.sub_table) != null){
            sub_table_file = new File(parameters.get(WorkerGrammar.sub_table));
            if (!(sub_table_file.exists() & sub_table_file.canRead())) {
                System.out.println("Cannot read the file or file is not exists!");
                return false;
            }
        }
        else {
            System.out.println("Subtable file not specified!");
            return false;
        }

        if (parameters.get(WorkerGrammar.output) != null) {
            File output_file = new File(parameters.get(WorkerGrammar.output));
            if (!(output_file.exists() & output_file.canWrite())) {
                System.out.println("Cannot read the file or file is not exists!");
                return false;
            }
        }
        else {
            System.out.println("Output file not specified!");
            return false;
        }
        return true;
    }

    private void MakeSubtableMap() {
        byte EOF = -1;
        int counter = 0;
        try (FileInputStream in_stream = new FileInputStream(sub_table_file)){
            int encoded, value;
            while((encoded = in_stream.read()) != EOF & (value = in_stream.read()) != EOF){
                counter++;
                if (mode){
                    sub_table.put((byte)encoded, (byte)value);
                }
                else{
                    sub_table.put((byte)value, (byte)encoded);
                }
            }
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        if (counter != sub_table_size) {
            System.out.println("Subtable does not fit!");
            System.out.println("counter = " + counter);
        }
    }

    public byte[] executeTranslation(byte[] input){

        byte[] output = new byte[input.length];
        for (int i = 0; i < input.length; i++){
            output[i] = sub_table.get(input[i]);
        }

        return output;
    }

    public Map<String, String> getParameters(){return parameters;}

}
