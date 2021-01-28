package utils;

public class Validator {

    public static boolean isValid(String[] args){
        if (args == null || args.length == 0) {
            System.out.println("Please, add arguments (path for config)!");
            return false;
        }
        return true;
    }
}
