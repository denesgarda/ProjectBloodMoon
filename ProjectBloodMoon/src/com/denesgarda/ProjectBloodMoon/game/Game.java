package com.denesgarda.ProjectBloodMoon.game;

import com.denesgarda.ProjectBloodMoon.Main;

import java.io.IOException;
import java.sql.PreparedStatement;
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
                        signup();
                    }
                    else if(loginSignupInput.equalsIgnoreCase("exit")) {
                        exit();
                    }
                    else invalid();
                }
                System.out.println("Game is not yet implemented.");
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
    public static void signup() throws IOException, SQLException {
        mainLoop:
        while(true) {
            System.out.print("Email: ");
            String email = Main.consoleInput.readLine();
            System.out.print("Username: ");
            String username = Main.consoleInput.readLine();
            System.out.print("Password: ");
            String password = Main.consoleInput.readLine();

            Statement stmt = Main.conn.createStatement();
            String query = "SELECT * FROM pbm.accounts WHERE email = \"" + email + "\"";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                System.out.println("Email is taken! Please try again.");
            }
            else {
                Statement stmt2 = Main.conn.createStatement();
                String query2 = "SELECT * FROM pbm.accounts WHERE username = \"" + username + "\"";
                ResultSet rs2 = stmt2.executeQuery(query2);
                if(rs2.next()) {
                    System.out.println("Username is taken! Please try again.");
                }
                else {
                    while(true) {
                        System.out.println("Pick your character's gender\n1) Male\n2) Female");
                        String gender = Main.consoleInput.readLine();
                        System.out.println("Pick your character's race\n1) Test Race");
                        String race = Main.consoleInput.readLine();
                        if((gender.equalsIgnoreCase("1") || gender.equalsIgnoreCase("2")) && (race.equalsIgnoreCase("1"))) {
                            String query3 = "INSERT INTO pbm.accounts (username, password, email, gender, race) VALUES (?, ?, ?, ?, ?)";
                            PreparedStatement stmt3 = Main.conn.prepareStatement(query3);
                            stmt3.setString(1, username);
                            stmt3.setString(2, password);
                            stmt3.setString(3, email);
                            if(gender.equalsIgnoreCase("1")) {
                                stmt3.setString(4, "male");
                            }
                            else if(gender.equalsIgnoreCase("2")) {
                                stmt3.setString(4, "female");
                            }
                            if(race.equalsIgnoreCase("1")) {
                                stmt3.setString(5, "test race");
                            }
                            try {
                                stmt3.executeUpdate();
                                System.out.println("Account created! Please log in.");
                                break mainLoop;
                            }
                            catch(Exception e) {
                                System.out.println("Username cannot be longer than 12 characters! Please try again.");
                                break;
                            }
                        }
                        else {
                            System.out.println("Either the gender or the race is invalid! Please try again.");
                        }
                    }
                }
            }
        }
    }
}
