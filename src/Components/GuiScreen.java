package Components;

import java.util.ArrayList;

public class GuiScreen {
    public static ArrayList<String> output = new ArrayList<>();

    public static void println(String string) {
        System.out.println(string);

        output.add(string);
    }
}
