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
        loginSignup:
        while(true) {
            System.out.println("""

                    Login / Signup
                    1) Login
                    2) Signup
                    3) Quit""");
            String loginSignupInput = Main.consoleInput.readLine();
            if(loginSignupInput.equalsIgnoreCase("1")) {
                username = login();
                if(username != null) {
                    mainMenuLoop:
                    while(true) {
                        System.out.println("""

                    Welcome to Project: Blood Moon
                    ==============================
                    1) Play
                    2) Quit
                    3) How to play (Important)
                    4) Log out
                    ps. Type "/exit" at any time to save and exit.""");
                        String mainMenuInput = Main.consoleInput.readLine();
                        if (mainMenuInput.equalsIgnoreCase("1")) {
                            boolean continueToGame = false;
                            infoLoop:
                            while(true) {
                                System.out.println("Fetching game info...");
                                String progress = getProgress(username);
                                if (progress.equalsIgnoreCase("0")) {
                                    while (true) {
                                        System.out.println("""
                                                                                        
                                                Choose an option to start:
                                                1) Start new game""");
                                        String startOption = Main.consoleInput.readLine();
                                        if (startOption.equalsIgnoreCase("1")) {
                                            System.out.println("Starting game...");
                                            continueToGame = true;
                                            break;
                                        }
                                        else if (startOption.equalsIgnoreCase("/exit")) {
                                            exit();
                                        }
                                        else if (startOption.equalsIgnoreCase("/quit")) {
                                            break infoLoop;
                                        }
                                        else if(startOption.equalsIgnoreCase("/save")) {
                                            System.out.println("Cannot save progress because you are not in a game.");
                                        }
                                        else if(startOption.equalsIgnoreCase("/stats")) {
                                            System.out.println("Cannot view stats because you are not in a game.");
                                        }
                                        else if(startOption.equalsIgnoreCase("/inventory")) {
                                            System.out.println("Cannot view inventory because you are not in a game.");
                                        }
                                        else invalid();
                                    }
                                }
                                else {
                                    while(true) {
                                        System.out.println("""
                                                                                        
                                                Choose an option to start:
                                                1) Continue game
                                                2) Reset progress""");
                                        String startOption = Main.consoleInput.readLine();
                                        if (startOption.equalsIgnoreCase("1")) {
                                            System.out.println("Loading game...");
                                            continueToGame = true;
                                            break;
                                        }
                                        else if (startOption.equalsIgnoreCase("2")) {
                                            System.out.print("You will lose all of your progress if you continue. Enter your password to continue.\nPassword: ");
                                            String cont = Main.consoleInput.readLine();
                                            if (cont.equalsIgnoreCase(getPassword(username))) {
                                                System.out.println("Resetting progress...");
                                                resetProgress(username);
                                                break;
                                            }
                                            else {
                                                System.out.println("Incorrect password.");
                                            }
                                        }
                                        else if (startOption.equalsIgnoreCase("/exit")) {
                                            exit();
                                        }
                                        else if (startOption.equalsIgnoreCase("/quit")) {
                                            break infoLoop;
                                        }
                                        else if(startOption.equalsIgnoreCase("/save")) {
                                            System.out.println("Cannot save progress because you are not in a game.");
                                        }
                                        else if(startOption.equalsIgnoreCase("/stats")) {
                                            System.out.println("Cannot view stats because you are not in a game.");
                                        }
                                        else if(startOption.equalsIgnoreCase("/inventory")) {
                                            System.out.println("Cannot view inventory because you are not in a game.");
                                        }
                                        else invalid();
                                    }
                                }
                                if(continueToGame) {
                                    break;
                                }
                            }
                            if(continueToGame) {
                                System.out.println("THE GAME IS NOT YET AVAILABLE");
                                exit();
                            }
                        }
                        else if(mainMenuInput.equalsIgnoreCase("2")) {
                            exit();
                        }
                        else if(mainMenuInput.equals("3")) {
                            System.out.println("""

                        How to play
                        ===========
                        The game will tell you how to progress the farther you get.
                        ===========
                        There are also keywords, which you can run any time in the game, other than login/signup input. These are the keywords:
                        "/exit" - Save and exit the game
                        "/quit" - Quits to main menu
                        "/save" - Saves progress
                        "/stats" - View your character's stats
                        "/inventory" - Check your inventory
                        
                        (Press [ENTER] to continue)""");
                            Main.consoleInput.readLine();
                        }
                        else if(mainMenuInput.equalsIgnoreCase("4")) {
                            System.out.println("Logging out...");
                            break;
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/exit")) {
                            exit();
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/quit")) {}
                        else if(mainMenuInput.equalsIgnoreCase("/save")) {
                            System.out.println("Cannot save progress because you are not in a game.");
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/stats")) {
                            System.out.println("Cannot view stats because you are not in a game.");
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/inventory")) {
                            System.out.println("Cannot view inventory because you are not in a game.");
                        }
                        else invalid();
                    }
                }
            }
            else if(loginSignupInput.equalsIgnoreCase("2")) {
                signup();
            }
            else if(loginSignupInput.equalsIgnoreCase("3")) {
                exit();
            }
            else if(loginSignupInput.equalsIgnoreCase("/exit")) {
                exit();
            }
            else if(loginSignupInput.equalsIgnoreCase("/quit")) {
                System.out.println("Cannot quit to main menu.");
            }
            else if(loginSignupInput.equalsIgnoreCase("/save")) {
                System.out.println("Cannot save progress because you are not in a game.");
            }
            else if(loginSignupInput.equalsIgnoreCase("/stats")) {
                System.out.println("Cannot view stats because you are not in a game.");
            }
            else if(loginSignupInput.equalsIgnoreCase("/inventory")) {
                System.out.println("Cannot view inventory because you are not in a game.");
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
                return null;
            }
        }
        else {
            System.out.println("Username not found! Please try again.");
            return null;
        }
    }
    public static void signup() throws IOException, SQLException {
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

    public static String getProgress(String username) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT progress FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        return rs.getString("progress");
    }

    public static String getPassword(String username) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT password FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        return rs.getString("password");
    }

    public static void resetProgress(String username) throws SQLException {
        String query = "UPDATE pbm.accounts SET progress = \"0\"";
        PreparedStatement stmt = Main.conn.prepareStatement(query);
        stmt.executeUpdate();
    }
}
