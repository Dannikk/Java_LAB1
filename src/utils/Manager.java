package utils;

import Grammar.ManagerGrammar;

import java.util.Map;

public class Manager {

    private final Map<String, String> parameters;
    private int buffer_size;

    public Manager(String config_path){
        parameters = SyntaxParser.Parse(config_path);
        if(semanticParse()){
            buffer_size = Integer.parseInt(parameters.get(ManagerGrammar.buffer_size));
        }
    }

    private boolean semanticParse(){
        for (String param: ManagerGrammar.param_array) {
            if (!parameters.containsKey(param)){
                System.out.println("Not enough parameters in main config!");
                return false;
            }
        }
        return true;
    }

    public Map<String, String> getParameters(){
        return parameters;
    }

    public void manageExecuting(MyReader reader, Worker worker, MyWriter writer){
        byte[] buffer;
        do {
            if (reader.available() >= buffer_size)
                buffer = new byte[buffer_size];
            else
                buffer = new byte[reader.available()];
            reader.readByteArray(buffer);

            writer.writeByteArray(worker.executeTranslation(buffer));
        } while (reader.available() > 0);
    }

    public int getBuffer_size(){
        return buffer_size;
    }

}
