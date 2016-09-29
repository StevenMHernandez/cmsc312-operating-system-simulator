import java.util.*;
import java.io.*;

public class parseJobtesting {
  
  private String inputfile = "input.txt";
  private LinkedList<String> queue = new LinkedList<String>();
  
  public parseJobtesting(String inputfile) {
    this.inputfile = inputfile;
  }
  
  public parseJobtesting() {
    
  }
  
  public void parse() throws IOException {
  
    File file = new File(inputfile);
    Scanner input = new Scanner(file);
  
    while(input.hasNext()) {
      queue.add(input.next());
    }
    input.close();
  }
  
  public void testprint() {
    
    while(queue.peek() != null) {
      System.out.println(queue.poll());
    }
  }
}