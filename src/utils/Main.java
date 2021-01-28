package utils;

import Grammar.ManagerGrammar;
import Grammar.WorkerGrammar;

public class Main {

    public static void main(String[] args) {
        if(Validator.isValid(args)){

            Manager manager = new Manager(args[0]);

            Worker encoder = new Worker(manager.getParameters().get(ManagerGrammar.encode_cfg));
            MyReader reader1 = new MyReader(manager.getParameters().get(ManagerGrammar.input), manager.getBuffer_size());
            MyWriter writer1 = new MyWriter(encoder.getParameters().get(WorkerGrammar.output));
            manager.manageExecuting(reader1, encoder, writer1);
            reader1.closeMyReader();
            writer1.closeMyWriter();

            Worker decoder = new Worker(manager.getParameters().get(ManagerGrammar.decode_cfg));
            MyReader reader2 = new MyReader(encoder.getParameters().get(WorkerGrammar.output), manager.getBuffer_size());
            MyWriter writer2 = new MyWriter(decoder.getParameters().get(WorkerGrammar.output));
            manager.manageExecuting(reader2, decoder, writer2);
            reader2.closeMyReader();
            writer2.closeMyWriter();
        }
    }
}
