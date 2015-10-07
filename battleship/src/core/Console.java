package core;

import java.util.Scanner;

public class Console {

    private final Scanner scanner;

    public Console() {

        scanner = new Scanner(System.in);
    }

    public String readLine() {

        return scanner.nextLine();
    }    

    public void writeLine(final String line) {

        System.out.print(line);
    }
}
