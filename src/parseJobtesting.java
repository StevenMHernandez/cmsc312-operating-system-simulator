import java.util.*;
import java.io.*;

public class parseJobtesting {

    private String inputfile = "input.txt";
    private LinkedList<String> queue = new LinkedList<String>();
    private File file = new File(inputfile);
    private Scanner input;

    public parseJobtesting(String filename){
        this.inputfile = filename;
        parse();
    }

    public parseJobtesting() {

    }

    public void setFile(String filename){
        this.inputfile = filename;
        parse();
    }

    private void parse() {
        try {
            input = new Scanner(file);
            while(input.hasNext()) {
                queue.add(input.next());
            }
        } catch (Exception e) { System.out.println("uh what do i put here"); }
        input.close();
    }

    public void testprint() {

        while(queue.peek() != null) {
            System.out.println(queue.poll());
        }
    }
}