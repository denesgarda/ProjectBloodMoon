package com.denesgarda.ProjectBloodMoon.game;

import com.denesgarda.ProjectBloodMoon.Main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Game {
    public static String username;

    public static void game() throws IOException, SQLException {
        mainMenuLoop:
        while(true) {
            System.out.println("\nWelcome to Project: Blood Moon\n==============================\n1) Play\n2) Quit\nps. Type \"exit\" at any time to save and exit.");
            String mainMenuInput = Main.consoleInput.readLine();
            if (mainMenuInput.equalsIgnoreCase("1")) {
                loginSignup:
                while(true) {
                    System.out.println("\nLogin / Signup\n1) Login\n2) Signup");
                    String loginSignupInput = Main.consoleInput.readLine();
                    if(loginSignupInput.equalsIgnoreCase("1")) {
                        username = login();
                        break;
                    }
                    else if(loginSignupInput.equalsIgnoreCase("2")) {
                        System.out.println("This is not yet available");
                    }
                    else if(loginSignupInput.equalsIgnoreCase("exit")) {
                        exit();
                    }
                    else invalid();
                }
                System.out.println("GAME");
                exit();
            }
            else if(mainMenuInput.equalsIgnoreCase("2")) {
                exit();
            }
            else if(mainMenuInput.equalsIgnoreCase("exit")) {
                exit();
            }
            else invalid();
        }
    }

    public static void invalid() {
        System.out.println("That is not a valid input! Please try again.");
    }

    public static void exit() {
        System.out.println("Thank you for playing! Bye...");
        System.exit(0);
    }
    public static void saveAndExit() {
        //save
        System.out.println("Thank you for playing! Bye...");
        System.exit(0);
    }

    public static String login() throws IOException, SQLException {
        while(true) {
            System.out.print("Username: ");
            String username = Main.consoleInput.readLine();
            System.out.print("Password: ");
            String password = Main.consoleInput.readLine();

            Statement stmt = Main.conn.createStatement();
            String query = "SELECT password FROM pbm.accounts WHERE username = \"" + username + "\"";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                if(rs.getString("password").equals(password)) {
                    System.out.println("Logging in...");
                    return username;
                }
                else {
                    System.out.println("Wrong password! Please try again.");
                }
            }
            else {
                System.out.println("Username not found! Please try again.");
            }
        }
    }
}
