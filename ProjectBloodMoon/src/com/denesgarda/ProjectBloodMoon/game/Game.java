package com.denesgarda.ProjectBloodMoon.game;

import com.denesgarda.ProjectBloodMoon.Main;
import com.denesgarda.ProjectBloodMoon.game.data.PasswordField;
import com.denesgarda.ProjectBloodMoon.game.data.Stats;
import com.denesgarda.ProjectBloodMoon.game.data.Strings;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    public static String username;
    public static Stats stats;
    public static int progress;
    public static Random random = new Random();

    public static void game() throws IOException, SQLException {
        loginSignup:
        while(true) {
            System.out.println("""

                    Login / Signup
                    1) Login
                    2) Signup
                    3) Quit
                    4) Forgot password""");
            String loginSignupInput = Main.consoleInput.readLine();
            if(loginSignupInput.equalsIgnoreCase("1")) {
                username = login();
                if(username != null) {
                    mainMenuLoop:
                    while(true) {
                        Main.properties.load(new FileInputStream("properties.properties"));
                        if(Boolean.parseBoolean(Main.properties.getProperty("vwi"))) {
                            System.out.println("""
        
                            Welcome to Project: Blood Moon
                            ==============================
                            1) Play
                            2) Quit
                            3) How to play (Important!)
                            4) Log out
                            5) Account options
                            ps. Type "/exit" at any time to save and exit.""");
                        }
                        else {
                            System.out.println("""
        
                            Welcome to Project: Blood Moon
                            ==============================
                            1) Play
                            2) Quit
                            3) How to play
                            4) Log out
                            5) Account options
                            ps. Type "/exit" at any time to save and exit.""");
                        }
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
                                            System.out.println("You will lose all of your progress if you continue. Enter your password to continue.");
                                            //String cont = Main.consoleInput.readLine();
                                            String cont = PasswordField.readPassword("Password: ");
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
                                System.out.println("Loading...\n");
                                stats = new Stats(username);
                                progress = Integer.parseInt(getProgress(username));

                                gameLoop:
                                while(true) {
                                    if (progress == 0) {
                                        stats.setHP(100);
                                        if(Strings.println("You wake up...\n(Press [ENTER] to continue)")) break;
                                        if(Strings.println("\"Come now, breakfast is ready!\" Your mom calls you out to the living room for breakfast.")) break;
                                        if(Strings.println("While eating breakfast, you hear the radio, \"It's such a beautiful day in Caerleon with sunny skies in the morning and cloudy skies in the afternoon and nigh---....\"")) break;
                                        if(Strings.println("The radio cuts out and all you hear is static.")) break;
                                        if(Strings.println("\"Not again!\" your mom exclaims.")) break;
                                        if(Strings.println("You finish eating breakfast...")) break;
                                        int goOutsideInput = Strings.dialogue("Why don't you go outside and meet up with a few of your friends?", new String[]{"Go outside", "Go to your bedroom"});
                                        if (goOutsideInput == 2) {
                                            Strings.println("You go back to your bedroom and take out a book to read. You don't really have anything better to do, after all.");
                                            Strings.println("You read for about an hour, when your mom calls to you...");
                                            Strings.println("\"Go outside and play with your friends! You've been inside for too long now.\"");
                                            Strings.println("Reluctantly, you do what you're told.");
                                        } else if (goOutsideInput == 0) {
                                            break;
                                        }
                                        if(Strings.println("You go outside and meet up with a few of your friends.")) break;
                                        if(Strings.println("Your neighbor suggests that the group go into an unexplored part of the forest.")) break;
                                        int goToUnexplored = Strings.dialogue("Do you want to go with your friends to explore the new part of the forest?", new String[]{"Yes, go exploring with them", "No, refuse to go"});
                                        if (goToUnexplored == 2) {
                                            Strings.println("\"Come on, you promised to go with us yesterday!\" Your friends all exclaim.");
                                            Strings.println("After a little bit of persuasion, you finally give in and go with them.");
                                        } else if (goToUnexplored == 0) {
                                            break;
                                        }
                                        if(Strings.println("You start to head off into the depths of the dark oak forest with your group of friends.")) break;
                                        progress = 1;
                                        saveAtCheckpoint();
                                    }
                                    if (progress == 1) {
                                        stats.retrieveAndSetHp();
                                        if(Strings.println("You walk with the group for a while.")) break;
                                        if(Strings.println("The sun gets higher and higher in the sky.")) break;
                                        if(Strings.println("You're still familiar with where you are. You've explored so much of this place, that you still recognize everything around you even this far out.")) break;
                                        if(Strings.println("You feel the temperature getting colder and colder.")) break;
                                        if(Strings.println("You sense something's wrong...")) break;
                                        if(Strings.println("You hear rustling around you.")) break;
                                        if(Strings.println("The entire group turns to where you heard the rustling.")) break;
                                        if(Strings.println("Suddenly, a bear jumps out at you!")) break;
                                        long start = System.currentTimeMillis();
                                        if(Strings.println("QUICK! Press [ENTER] to dodge it!")) break;
                                        long finish = System.currentTimeMillis();
                                        long timeElapsed = finish - start;
                                        if(timeElapsed <= 300) {
                                            if(Strings.println("You doge the bear and hit the ground hard!")) break;
                                            if(stats.minusHP(random.nextInt(3) + 7)) continue;
                                        }
                                        else if(timeElapsed <= 600) {
                                            if(Strings.println("The bear slightly hits you but you manage to dodge it!")) break;
                                            if(stats.minusHP(random.nextInt(6) + 10)) continue;
                                        }
                                        else if(timeElapsed <= 1000) {
                                            if(Strings.println("The bear hits you but it could've been worse!")) break;
                                            if(stats.minusHP(random.nextInt(8) + 14)) continue;
                                        }
                                        else if(timeElapsed <= 1500) {
                                            if(Strings.println("The bear hits you fairly hard!")) break;
                                            if(stats.minusHP(random.nextInt(12) + 20)) continue;
                                        }
                                        else if(timeElapsed <= 2500) {
                                            if(Strings.println("The bear hits you head on, but you manage to just escape!")) break;
                                            if(stats.minusHP(random.nextInt(20) + 35)) continue;
                                        }
                                        else {
                                            if(Strings.println("The bear takes you to the ground and mauls you to death...")) break;
                                            if(stats.minusHP(100)) continue;
                                        }
                                        if(Strings.println("The group disperses, and you run for your life!")) break;
                                        if(Strings.println("You run, run, and run...")) break;
                                        if(Strings.println("WATCH OUT! There's a branch in front of you, but you can't stop running, or the bear will catch up!")) break;
                                        long start2 = System.currentTimeMillis();
                                        if(Strings.println("QUICK! Press [ENTER] to duck under it!")) break;
                                        long finish2 = System.currentTimeMillis();
                                        long timeElapsed2 = finish2 - start2;
                                        if(timeElapsed2 <= 250) {
                                            if(Strings.println("You cleanly duck underneath the branch unscathed.")) break;
                                        }
                                        else if(timeElapsed2 <= 500) {
                                            if(Strings.println("The branch slightly scrapes your head, but you're mostly fine.")) break;
                                            if(stats.minusHP((double) random.nextInt(25) / 10)) continue;
                                        }
                                        else if(timeElapsed2 <= 750) {
                                            if(Strings.println("The branch hits your forehead hard, and you have a headache. This also slows you down.")) break;
                                            if(stats.minusHP(random.nextInt(7) + 7)) continue;
                                        }
                                        else if(timeElapsed2 <= 1000) {
                                            if(Strings.println("The branch hits your head and you get whiplash. This slows you down a lot.")) break;
                                            if(stats.minusHP(random.nextInt(10) + 20)) continue;
                                        }
                                        else {
                                            if(Strings.println("The branch hits your chest. This really hurts and also slows you down a lot.")) break;
                                            if(stats.minusHP(random.nextInt(23) + 25)) continue;
                                        }
                                        if(Strings.println("You continue running through the woods...")) break;
                                        if(Strings.println("Until you think you finally lost the bear.")) break;
                                        if(Strings.println("You're out of breath...")) break;
                                        if(Strings.println("You calm down a little bit. There is a cave up ahead.")) break;
                                        int goIntoCave = Strings.dialogue("Do you enter the cave?", new String[]{"Go in", "Go past"});
                                        if(goIntoCave == 2) {
                                            if(Strings.println("You walk past the cave to find better shelter.")) break;
                                            if(Strings.println("You step on a loud branch on accident.")) break;
                                            if(Strings.println("This noise alerts the bear, and the bear sprints towards you.")) break;
                                            long start3 = System.currentTimeMillis();
                                            if(Strings.println("RUN BACK INTO THE CAVE, QUICK! Press [ENTER] to sprint!")) break;
                                            long finish3 = System.currentTimeMillis();
                                            long timeElapsed3 = finish3 - start3;
                                            if(timeElapsed3 > 1000) {
                                                if(Strings.println("The bear got you.")) break;
                                                if(stats.minusHP(100)) continue;
                                            }
                                        }
                                        else if(goIntoCave == 0) {
                                            break;
                                        }
                                        if(Strings.println("You make it into the cave. It's dark, but you figure this is a safe place to rest.")) break;
                                        int goFarther = Strings.dialogue("You still may be visible to the bear outside of the cave. Do you want to go farther in?", new String[]{"Go farther in", "Stay where you are"});
                                        if(goFarther == 2) {
                                            if(Strings.println("The bear runs up to the cave.")) break;
                                            long start3 = System.currentTimeMillis();
                                            if(Strings.println("QUICK! Press [ENTER] to hide!")) break;
                                            long finish3 = System.currentTimeMillis();
                                            long timeElapsed3 = finish3 - start3;
                                            if(timeElapsed3 > 900) {
                                                if(Strings.println("The bear got you.")) break;
                                                if(stats.minusHP(100)) continue;
                                            }
                                            else {
                                                if(Strings.println("You hide deeper in the cave...")) break;
                                            }
                                        }
                                        else if(goFarther == 0) {
                                            break;
                                        }
                                        if(Strings.println("You walk deeper and deeper into the cave.")) break;
                                        if(Strings.println("You suddenly feel a force. You feel rejuvenated. All of your cuts and scratches heal up, almost like it's magic...")) break;
                                        stats.resetHP();
                                        progress = 2;
                                        saveAtCheckpoint();
                                    }
                                    if(progress == 2) {
                                        stats.retrieveAndSetHp();
                                        if(Strings.println("You continue deeper and deeper into the cave...")) break;
                                        if(Strings.println("You see light up ahead.")) break;
                                        if(Strings.println("You find a fountain with glowing liquid flowing down.")) break;
                                        if(Strings.println("You are tempted to touch the liquid.")) break;
                                        if(Strings.println("It's almost like a force is pulling you in...")) break;
                                        if(Strings.println("All noise cuts out around you.")) break;
                                        if(Strings.println("You're getting pulled in by the temptation...")) break;
                                        if(Strings.println("You touch the liquid and you make a wish.")) break;
                                        if(Strings.println("You're wish is to protect your mother at all costs.")) break;
                                        break;
                                    }
                                }
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
                            "/stats" - View your character's stats
                            "/inventory" - Check your inventory
                            
                            (Press [ENTER] to continue)""");
                            Main.consoleInput.readLine();
                            Main.updateProperty("vwi", "false");
                        }
                        else if(mainMenuInput.equalsIgnoreCase("4")) {
                            System.out.println("Logging out...");
                            username = "";
                            break;
                        }
                        else if(mainMenuInput.equals("5")) {
                            accountOptionsLoop:
                            while(true) {
                                System.out.println("""
                                                                            
                                        Account Options
                                        ===============
                                        1) Change email
                                        2) Change username
                                        3) Change password
                                        4) Delete account""");
                                String accountOptionsInput = Main.consoleInput.readLine();
                                if(accountOptionsInput.equalsIgnoreCase("1")) {
                                    System.out.print("Enter new email: ");
                                    String email = Main.consoleInput.readLine();

                                    Statement stmt = Main.conn.createStatement();
                                    String query = "SELECT * FROM pbm.accounts WHERE email = \"" + email + "\"";
                                    ResultSet rs = stmt.executeQuery(query);
                                    if (rs.next()) {
                                        System.out.println("Email is taken! Please try again.");
                                    } else {
                                        System.out.println("Loading...");
                                        int code = emailCode(email);
                                        System.out.print("A verification code was sent to your new email. Enter it here to verify it's you.\nEnter verification code: ");
                                        String enteredCode = Main.consoleInput.readLine();
                                        try {
                                            int enteredCodeInt = Integer.parseInt(enteredCode);
                                            if (code != enteredCodeInt) {
                                                System.out.println("Code is incorrect! Please try again.");
                                            }
                                            else {
                                                System.out.println("Updating email...");
                                                String query3 = "UPDATE pbm.accounts SET email = \"" + email + "\" WHERE username = \"" + username + "\"";
                                                PreparedStatement stmt3 = Main.conn.prepareStatement(query3);
                                                stmt3.executeUpdate();
                                                System.out.println("Email has been updated!");
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Code is incorrect! Please try again.");
                                        }
                                    }
                                }
                                else if(accountOptionsInput.equalsIgnoreCase("2")) {
                                    System.out.print("Enter new username: ");
                                    String newUsername = Main.consoleInput.readLine();

                                    Statement stmt = Main.conn.createStatement();
                                    String query = "SELECT * FROM pbm.accounts WHERE username = \"" + newUsername + "\"";
                                    ResultSet rs = stmt.executeQuery(query);
                                    if (rs.next()) {
                                        System.out.println("Username is taken! Please try again.");
                                    }
                                    else {
                                        System.out.println("Updating username...");
                                        String query3 = "UPDATE pbm.accounts SET username = \"" + newUsername + "\" WHERE username = \"" + username + "\"";
                                        PreparedStatement stmt3 = Main.conn.prepareStatement(query3);
                                        stmt3.executeUpdate();
                                        username = newUsername;
                                        System.out.println("Username has been updated!");
                                    }
                                }
                                else if(accountOptionsInput.equalsIgnoreCase("3")) {
                                    String newPassword = PasswordField.readPassword("New password: ");
                                    String confirm = PasswordField.readPassword("Confirm password: ");
                                    if(newPassword.equals(confirm)) {
                                        System.out.println("Updating password...");
                                        String query = "UPDATE pbm.accounts SET password = \"" + newPassword + "\" WHERE username = \"" + username + "\"";
                                        PreparedStatement stmt = Main.conn.prepareStatement(query);
                                        stmt.executeUpdate();
                                        System.out.println("Password has been updated!");
                                    }
                                    else {
                                        System.out.println("Passwords do not match! Please try again.");
                                    }
                                }
                                else if(accountOptionsInput.equalsIgnoreCase("4")) {
                                    System.out.println("If you delete your account, everything will be lost!");
                                    String enteredPassword = PasswordField.readPassword("Enter you password to continue: ");
                                    if (enteredPassword.equalsIgnoreCase(getPassword(username))) {
                                        System.out.println("Deleting account...");

                                        String query = "DELETE FROM pbm.accounts WHERE username = \"" + username + "\"";
                                        PreparedStatement stmt = Main.conn.prepareStatement(query);
                                        stmt.executeUpdate();

                                        username = "";

                                        System.out.println("Account deleted!");
                                        break mainMenuLoop;
                                    }
                                    else {
                                        System.out.println("Incorrect password.");
                                    }
                                }
                                else if(accountOptionsInput.equalsIgnoreCase("/exit")) {
                                    exit();
                                }
                                else if(accountOptionsInput.equalsIgnoreCase("/quit")) {
                                    break;
                                }
                                else if(accountOptionsInput.equalsIgnoreCase("/stats")) {
                                    System.out.println("Cannot view stats because you are not in a game.");
                                }
                                else if(accountOptionsInput.equalsIgnoreCase("/inventory")) {
                                    System.out.println("Cannot view inventory because you are not in a game.");
                                }
                                else invalid();
                            }
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/exit")) {
                            exit();
                        }
                        else if(mainMenuInput.equalsIgnoreCase("/quit")) {}
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
            else if(loginSignupInput.equalsIgnoreCase("4")) {
                System.out.print("Password reset\nEmail: ");
                String email = Main.consoleInput.readLine();
                if(checkIfEmailExists(email)) {
                    System.out.println("Sending code...");
                    int code = emailCode(email);
                    System.out.print("Enter verification code: ");
                    String enteredString = Main.consoleInput.readLine();
                    try {
                        int entered = Integer.parseInt(enteredString);
                        if(entered == code) {
                            //System.out.print("New password: ");
                            //String newPassword = Main.consoleInput.readLine();
                            String newPassword = PasswordField.readPassword("New password: ");
                            String confirm = PasswordField.readPassword("Confirm password: ");
                            if(newPassword.equals(confirm)) {
                                String query = "UPDATE pbm.accounts SET password = \"" + newPassword + "\" WHERE email = \"" + email + "\"";
                                PreparedStatement stmt = Main.conn.prepareStatement(query);
                                stmt.executeUpdate();
                                System.out.println("Password reset! Please log in.");
                            }
                            else {
                                System.out.println("Passwords do not match! Please try again.");
                            }
                        }
                        else {
                            System.out.println("Code is incorrect! Please try again.");
                        }
                    }
                    catch(Exception e) {
                        System.out.println("That is not a valid number! Please try again.");
                    }
                }
                else {
                    System.out.println("That email is not found in our system.");
                }
            }
            else if(loginSignupInput.equalsIgnoreCase("/exit")) {
                exit();
            }
            else if(loginSignupInput.equalsIgnoreCase("/quit")) {
                System.out.println("Cannot quit to main menu.");
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
    public static void saveAtCheckpoint() {
        System.out.println("Checkpoint reached!\nSaving progress! Please wait...");
        try {
            String query = "UPDATE pbm.accounts SET progress = \"" + progress + "\"";
            PreparedStatement stmt = Main.conn.prepareStatement(query);
            stmt.executeUpdate();
            stats.saveStats();
            System.out.println("Progress saved!\n");
        }
        catch(Exception e) {
            System.out.println("WARNING: Unable to save game");
        }
    }

    public static String login() throws IOException, SQLException {
        System.out.print("Username: ");
        String username = Main.consoleInput.readLine();
        //System.out.print("Password: ");
        //String password = Main.consoleInput.readLine();
        String password = PasswordField.readPassword("Password: ");
        System.out.println("Logging in...");

        Statement stmt = Main.conn.createStatement();
        String query = "SELECT password FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            if(rs.getString("password").equals(password)) {
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
        //System.out.print("Password: ");
        //String password = Main.consoleInput.readLine();
        String password = PasswordField.readPassword("Password: ");
        String confirm = PasswordField.readPassword("Confirm password: ");
        if(password.equals(confirm)) {
            Statement stmt = Main.conn.createStatement();
            String query = "SELECT * FROM pbm.accounts WHERE email = \"" + email + "\"";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                System.out.println("Email is taken! Please try again.");
            } else {
                Statement stmt2 = Main.conn.createStatement();
                String query2 = "SELECT * FROM pbm.accounts WHERE username = \"" + username + "\"";
                ResultSet rs2 = stmt2.executeQuery(query2);
                if (rs2.next()) {
                    System.out.println("Username is taken! Please try again.");
                } else {
                    System.out.println("Loading...");
                    int code = emailCode(email);
                    System.out.print("A verification code was sent to your email. Enter it here to verify it's you.\nEnter verification code: ");
                    String enteredCode = Main.consoleInput.readLine();
                    try {
                        int enteredCodeInt = Integer.parseInt(enteredCode);
                        if (code != enteredCodeInt) {
                            System.out.println("Code is incorrect! Please try again.");
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println("Code is incorrect! Please try again.");
                        return;
                    }
                    while (true) {
                        System.out.println("Pick your character's gender\n1) Male\n2) Female");
                        String gender = Main.consoleInput.readLine();
                        System.out.println("""
                                Pick your character's race. (This cannot be changed later in the game!!!)
                                1) Human
                                2) Elf
                                3) Fairy
                                4) Beastmen""");
                        String race = Main.consoleInput.readLine();
                        if ((gender.equalsIgnoreCase("1") || gender.equalsIgnoreCase("2")) && (race.equalsIgnoreCase("1") || race.equalsIgnoreCase("2") || race.equalsIgnoreCase("3") || race.equalsIgnoreCase("4"))) {
                            String query3 = "INSERT INTO pbm.accounts (username, password, email, gender, race, progress, hp) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement stmt3 = Main.conn.prepareStatement(query3);
                            stmt3.setString(1, username);
                            stmt3.setString(2, password);
                            stmt3.setString(3, email);
                            stmt3.setString(6, "0");
                            stmt3.setString(7, "100");
                            if (gender.equalsIgnoreCase("1")) {
                                stmt3.setString(4, "male");
                            } else if (gender.equalsIgnoreCase("2")) {
                                stmt3.setString(4, "female");
                            }
                            if (race.equalsIgnoreCase("1")) {
                                stmt3.setString(5, "Human");
                            } else if (race.equalsIgnoreCase("2")) {
                                stmt3.setString(5, "Elf");
                            } else if (race.equalsIgnoreCase("3")) {
                                stmt3.setString(5, "Fairy");
                            } else if (race.equalsIgnoreCase("4")) {
                                stmt3.setString(5, "Beastmen");
                            }
                            try {
                                stmt3.executeUpdate();
                                System.out.println("Account created! Please log in.");
                                break;
                            } catch (Exception e) {
                                System.out.println("Username cannot be longer than 12 characters! Please try again.");
                                break;
                            }
                        } else {
                            System.out.println("Either the gender or the race is invalid! Please try again.");
                        }
                    }
                }
            }
        }
        else {
            System.out.println("Passwords do not match! Please try again.");
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
        String query = "UPDATE pbm.accounts SET progress = \"0\" WHERE username = \"" + username + "\"";
        PreparedStatement stmt = Main.conn.prepareStatement(query);
        stmt.executeUpdate();
    }

    public static boolean checkIfEmailExists(String email) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT * FROM pbm.accounts WHERE email = \"" + email + "\"";
        return stmt.executeQuery(query).next();
    }

    public static int emailCode(String email) {
        Random random = new Random();
        int number = Integer.parseInt(String.format("%06d", random.nextInt(999999)));

        String from = "projectbloodmoon.services";
        String pass = "dpassgmail";
        String[] to = { email }; // list of recipient email addresses
        String subject = "Project: Blood Moon verification code";
        String body = "Your verification code is: " + number;

        sendFromGMail(from, pass, to, subject, body);

        return number;
    }

    public static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch(Exception ignore) {}
    }
}
