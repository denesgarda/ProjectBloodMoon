package com.denesgarda.ProjectBloodMoon.game;

import com.denesgarda.ProjectBloodMoon.Main;

import java.io.IOException;

public class Game {
    public static void game() throws IOException {
        mainMenuLoop:
        while(true) {
            System.out.println("\nWelcome to Project: Blood Moon\n==============================\n1) Play\n2) Quit\nps. Type \"exit\" at any time to save and exit.");
            String mainMenuInput = Main.consoleInput.readLine();
            if (mainMenuInput.equals("1")) {
                System.out.println("login/signup");
            }
            else if(mainMenuInput.equals("2")) {
                exit();
            }
            else if(mainMenuInput.equals("exit")) {
                exit();
            }
            else {
                System.out.println("That is not a valid input! Please try again.");
            }
        }
    }

    public static void exit() {
        System.out.println("Thank you for playing! Bye...");
        System.exit(0);
    }
}
