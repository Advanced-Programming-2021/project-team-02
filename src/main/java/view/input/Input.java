package view.input;

import java.util.Scanner;

public class Input {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getInput() {
        return SCANNER.nextLine();
    }
}
