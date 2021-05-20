package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("This happens\n(Press [ENTER] to continue)");
        consoleInput.readLine();
        System.out.println("Then this happens\n(Press [ENTER] to continue)");
        consoleInput.readLine();
        System.out.println("Do you want to help?\n1) Help them!\n2) Continue running...");
        consoleInput.readLine();
    }
}
