package utils;

import Grammar.SyntaxGrammar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SyntaxParser {

    //private static final String DELIMITER = "=|(\\s+)";

    static final Map<String, String> Parse(String file_path){

        final Map<String, String> parameters = new HashMap<>();

        try{
            BufferedReader buf = new BufferedReader(new FileReader(file_path));
            String string;
            while ((string = buf.readLine()) != null){
                String[] params = string.split(SyntaxGrammar.DELIMITER);
                if (params.length == 2)
                    parameters.put(params[0], params[1]);
                else
                    System.out.println("Config reading error!");
            }
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        return parameters;
    }
}
