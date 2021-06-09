package com.denesgarda.ProjectBloodMoon.game;

import com.denesgarda.JarData.data.statics.Serialization;
import com.denesgarda.ProjectBloodMoon.Main;
import com.denesgarda.ProjectBloodMoon.game.data.PasswordField;
import com.denesgarda.ProjectBloodMoon.game.data.Stats;
import com.denesgarda.ProjectBloodMoon.game.data.Strings;
import com.denesgarda.ProjectBloodMoon.utility.ArrayModification;
import com.denesgarda.ProjectBloodMoon.utility.Utility;

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
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    public static String username;
    public static Stats stats;
    public static Random random = new Random();

    public static void game() throws IOException, SQLException, InterruptedException {
        loginSignup:
        while(true) {
            System.out.println("""
                    
                    Login / Signup
                    1) Login
                    2) Signup
                    3) Quit
                    4) Forgot password
                    5) Forgot username
                    6) Forgot email""");
            String loginSignupInput = Main.consoleInput.readLine();
            if(loginSignupInput.equalsIgnoreCase("1")) {
                username = login();
                if(username != null) {
                    if(username.equals("admin")) {
                        System.out.println("CANNOT LOG IN TO ADMIN ACCOUNT THIS WAY");
                    }
                    else {
                        mainMenuLoop:
                        while (true) {
                            System.out.println("""
                                        
                                    Welcome to Project: Blood Moon
                                    ==============================
                                    1) Play
                                    2) Quit
                                    3) How to play (Important!)
                                    4) Log out
                                    5) Account options
                                    ps. Type "/quit" at any time to quit to the main menu.""");
                            String mainMenuInput = Main.consoleInput.readLine();
                            if (mainMenuInput.equalsIgnoreCase("1")) {
                                boolean continueToGame = false;
                                infoLoop:
                                while (true) {
                                    System.out.println("Fetching game info...");
                                    String progress = String.valueOf(stats.getProgress());
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
                                            } else if (startOption.equalsIgnoreCase("/exit")) {
                                                exit();
                                            } else if (startOption.equalsIgnoreCase("/quit")) {
                                                break infoLoop;
                                            } else if (startOption.equalsIgnoreCase("/stats")) {
                                                System.out.println("Cannot view stats because you are not in a game.");
                                            } else if (startOption.equalsIgnoreCase("/inventory")) {
                                                System.out.println("Cannot view inventory because you are not in a game.");
                                            } else invalid();
                                        }
                                    } else {
                                        while (true) {
                                            System.out.println("""
                                                                                            
                                                    Choose an option to start:
                                                    1) Continue game
                                                    2) Reset progress""");
                                            String startOption = Main.consoleInput.readLine();
                                            if (startOption.equalsIgnoreCase("1")) {
                                                System.out.println("Loading game...");
                                                continueToGame = true;
                                                break;
                                            } else if (startOption.equalsIgnoreCase("2")) {
                                                System.out.println("You will lose all of your progress if you continue. Enter your password to continue.");
                                                //String cont = Main.consoleInput.readLine();
                                                String cont = PasswordField.readPassword("Password: ");
                                                if (cont.equalsIgnoreCase(getPassword(username))) {
                                                    System.out.println("Resetting progress...");
                                                    resetProgress();
                                                    break;
                                                } else {
                                                    System.out.println("Incorrect password.");
                                                }
                                            } else if (startOption.equalsIgnoreCase("/exit")) {
                                                exit();
                                            } else if (startOption.equalsIgnoreCase("/quit")) {
                                                break infoLoop;
                                            } else if (startOption.equalsIgnoreCase("/stats")) {
                                                System.out.println("Cannot view stats because you are not in a game.");
                                            } else if (startOption.equalsIgnoreCase("/inventory")) {
                                                System.out.println("Cannot view inventory because you are not in a game.");
                                            } else invalid();
                                        }
                                    }
                                    if (continueToGame) {
                                        break;
                                    }
                                }
                                if (continueToGame) {
                                    System.out.println("Loading...\n");

                                    gameLoop:
                                    while (true) {
                                        if (stats.getProgress() == 0) {
                                            stats.setHP(100);
                                            if (Strings.println("You wake up...\n(Press [ENTER] to continue)")) break;
                                            if (Strings.println("\"Come now, breakfast is ready!\" Your mom calls you out to the living room for breakfast."))
                                                break;
                                            if (Strings.println("While eating breakfast, you hear the radio, \"It's such a beautiful day in Caerleon with sunny skies in the morning and cloudy skies in the afternoon and nigh---....\""))
                                                break;
                                            if (Strings.println("The radio cuts out and all you hear is static."))
                                                break;
                                            if (Strings.println("\"Not again!\" your mom exclaims.")) break;
                                            if (Strings.println("You finish eating breakfast...")) break;
                                            int goOutsideInput = Strings.dialogue("Why don't you go outside and meet up with a few of your friends?", new String[]{"Go outside", "Go to your bedroom"});
                                            if (goOutsideInput == 2) {
                                                Strings.println("You go back to your bedroom and take out a book to read. You don't really have anything better to do, after all.");
                                                Strings.println("You read for about an hour, when your mom calls to you...");
                                                Strings.println("\"Go outside and play with your friends! You've been inside for too long now.\"");
                                                Strings.println("Reluctantly, you do what you're told.");
                                            } else if (goOutsideInput == 0) {
                                                break;
                                            }
                                            if (Strings.println("You go outside and meet up with a few of your friends."))
                                                break;
                                            if (Strings.println("Your neighbor suggests that the group go into an unexplored part of the forest."))
                                                break;
                                            int goToUnexplored = Strings.dialogue("Do you want to go with your friends to explore the new part of the forest?", new String[]{"Yes, go exploring with them", "No, refuse to go"});
                                            if (goToUnexplored == 2) {
                                                Strings.println("\"Come on, you promised to go with us yesterday!\" Your friends all exclaim.");
                                                Strings.println("After a little bit of persuasion, you finally give in and go with them.");
                                            } else if (goToUnexplored == 0) {
                                                break;
                                            }
                                            if (Strings.println("You start to head off into the depths of the dark oak forest with your group of friends."))
                                                break;
                                            stats.setProgress(1);
                                            Utility.checkpoint(stats, username);
                                        }
                                        if (stats.getProgress() == 1) {
                                            stats = Utility.generateStats(username);
                                            if (Strings.println("You walk with the group for a while.")) break;
                                            if (Strings.println("The sun gets higher and higher in the sky.")) break;
                                            if (Strings.println("You're still familiar with where you are. You've explored so much of this place, that you still recognize everything around you even this far out."))
                                                break;
                                            if (Strings.println("You feel the temperature getting colder and colder."))
                                                break;
                                            if (Strings.println("You sense something's wrong...")) break;
                                            if (Strings.println("You hear rustling around you.")) break;
                                            if (Strings.println("The entire group turns to where you heard the rustling."))
                                                break;
                                            if (Strings.println("Suddenly, a bear jumps out at you!")) break;
                                            long start = System.currentTimeMillis();
                                            if (Strings.println("QUICK! Press [ENTER] to dodge it!")) break;
                                            long finish = System.currentTimeMillis();
                                            long timeElapsed = finish - start;
                                            if (timeElapsed <= 300) {
                                                if (Strings.println("You doge the bear and hit the ground hard!"))
                                                    break;
                                                if (stats.minusHP(random.nextInt(3) + 7)) continue;
                                            } else if (timeElapsed <= 600) {
                                                if (Strings.println("The bear slightly hits you but you manage to dodge it!"))
                                                    break;
                                                if (stats.minusHP(random.nextInt(6) + 10)) continue;
                                            } else if (timeElapsed <= 1000) {
                                                if (Strings.println("The bear hits you but it could've been worse!"))
                                                    break;
                                                if (stats.minusHP(random.nextInt(8) + 14)) continue;
                                            } else if (timeElapsed <= 1500) {
                                                if (Strings.println("The bear hits you fairly hard!")) break;
                                                if (stats.minusHP(random.nextInt(12) + 20)) continue;
                                            } else if (timeElapsed <= 2500) {
                                                if (Strings.println("The bear hits you head on, but you manage to just escape!"))
                                                    break;
                                                if (stats.minusHP(random.nextInt(20) + 35)) continue;
                                            } else {
                                                if (Strings.println("The bear takes you to the ground and mauls you to death..."))
                                                    break;
                                                if (stats.minusHP(100)) continue;
                                            }
                                            if (Strings.println("The group disperses, and you run for your life!"))
                                                break;
                                            if (Strings.println("You run, run, and run...")) break;
                                            if (Strings.println("WATCH OUT! There's a branch in front of you, but you can't stop running, or the bear will catch up!"))
                                                break;
                                            long start2 = System.currentTimeMillis();
                                            if (Strings.println("QUICK! Press [ENTER] to duck under it!")) break;
                                            long finish2 = System.currentTimeMillis();
                                            long timeElapsed2 = finish2 - start2;
                                            if (timeElapsed2 <= 250) {
                                                if (Strings.println("You cleanly duck underneath the branch unscathed."))
                                                    break;
                                            } else if (timeElapsed2 <= 500) {
                                                if (Strings.println("The branch slightly scrapes your head, but you're mostly fine."))
                                                    break;
                                                if (stats.minusHP((double) random.nextInt(25) / 10)) continue;
                                            } else if (timeElapsed2 <= 750) {
                                                if (Strings.println("The branch hits your forehead hard, and you have a headache. This also slows you down."))
                                                    break;
                                                if (stats.minusHP(random.nextInt(7) + 7)) continue;
                                            } else if (timeElapsed2 <= 1000) {
                                                if (Strings.println("The branch hits your head and you get whiplash. This slows you down a lot."))
                                                    break;
                                                if (stats.minusHP(random.nextInt(10) + 20)) continue;
                                            } else {
                                                if (Strings.println("The branch hits your chest. This really hurts and also slows you down a lot."))
                                                    break;
                                                if (stats.minusHP(random.nextInt(23) + 25)) continue;
                                            }
                                            if (Strings.println("You continue running through the woods...")) break;
                                            if (Strings.println("Until you think you finally lost the bear.")) break;
                                            if (Strings.println("You're out of breath...")) break;
                                            if (Strings.println("You calm down a little bit. There is a cave up ahead."))
                                                break;
                                            int goIntoCave = Strings.dialogue("Do you enter the cave?", new String[]{"Go in", "Go past"});
                                            if (goIntoCave == 2) {
                                                if (Strings.println("You walk past the cave to find better shelter."))
                                                    break;
                                                if (Strings.println("You step on a loud branch on accident.")) break;
                                                if (Strings.println("This noise alerts the bear, and the bear sprints towards you."))
                                                    break;
                                                long start3 = System.currentTimeMillis();
                                                if (Strings.println("RUN BACK INTO THE CAVE, QUICK! Press [ENTER] to sprint!"))
                                                    break;
                                                long finish3 = System.currentTimeMillis();
                                                long timeElapsed3 = finish3 - start3;
                                                if (timeElapsed3 > 1000) {
                                                    if (Strings.println("The bear got you.")) break;
                                                    if (stats.minusHP(100)) continue;
                                                }
                                            } else if (goIntoCave == 0) {
                                                break;
                                            }
                                            if (Strings.println("You make it into the cave. It's dark, but you figure this is a safe place to rest."))
                                                break;
                                            int goFarther = Strings.dialogue("You still may be visible to the bear outside of the cave. Do you want to go farther in?", new String[]{"Go farther in", "Stay where you are"});
                                            if (goFarther == 2) {
                                                if (Strings.println("The bear runs up to the cave.")) break;
                                                long start3 = System.currentTimeMillis();
                                                if (Strings.println("QUICK! Press [ENTER] to hide!")) break;
                                                long finish3 = System.currentTimeMillis();
                                                long timeElapsed3 = finish3 - start3;
                                                if (timeElapsed3 > 900) {
                                                    if (Strings.println("The bear got you.")) break;
                                                    if (stats.minusHP(100)) continue;
                                                } else {
                                                    if (Strings.println("You hide deeper in the cave...")) break;
                                                }
                                            } else if (goFarther == 0) {
                                                break;
                                            }
                                            if (Strings.println("You walk deeper and deeper into the cave.")) break;
                                            if (Strings.println("You suddenly feel a force. You feel rejuvenated. All of your cuts and scratches heal up, almost like it's magic..."))
                                                break;
                                            stats.resetHP();
                                            stats.setProgress(2);
                                            Utility.checkpoint(stats, username);
                                        }
                                        if (stats.getProgress() == 2) {
                                            stats = Utility.generateStats(username);
                                            if (Strings.println("You continue deeper and deeper into the cave..."))
                                                break;
                                            if (Strings.println("You see light up ahead.")) break;
                                            if (Strings.println("You find a fountain with glowing liquid flowing down."))
                                                break;
                                            if (Strings.println("You are tempted to touch the liquid.")) break;
                                            if (Strings.println("It's almost like a force is pulling you in...")) break;
                                            if (Strings.println("All noise cuts out around you.")) break;
                                            if (Strings.println("You're getting pulled in by the temptation...")) break;
                                            if (Strings.println("You touch the liquid and you make a wish.")) break;
                                            if (Strings.println("You're wish is to protect your mother at all costs.")) break;
                                            if (Strings.println("Suddenly, you hear a noise!")) break;
                                            if (Strings.println("What could it have been?")) break;
                                            if (Strings.println("You look to your right, but you hear it from your left.")) break;
                                            if (Strings.println("You look to your left, but you hear it from your right!")) break;
                                            if (Strings.println("Then, whatever it is flies right in front of your face!")) break;
                                            if (Strings.println("You jump back in surprise.")) break;
                                            if (Strings.println("But as soon as you look back, it's already gone!")) break;
                                            if (Strings.println("You can kind of make out what it is, though.")) break;
                                            if (Strings.println("It looks like some sort of... pixie.")) break;
                                            System.out.println("It's probably going to try to fly by you again, so try to catch it. Press [ENTER] when it comes by.");
                                            AtomicBoolean here = new AtomicBoolean(false);
                                            Timer pixiePromptTimer = new Timer();
                                            TimerTask pixiePrompt = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        here.set(true);
                                                        System.out.println("\nHere it is! Press [ENTER] to catch it!");
                                                        Thread.sleep(1000);
                                                        here.set(false);
                                                    }
                                                    catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            pixiePromptTimer.schedule(pixiePrompt, (random.nextInt(5) + 5) * 1000);
                                            while(true) {
                                                Main.consoleInput.readLine();
                                                if(here.get()) {
                                                    if (Strings.println("You caught the pixie!")) break;
                                                    break;
                                                }
                                                else {
                                                    System.out.println("You missed! Try again. Make sure to time it right.");
                                                    pixiePromptTimer.cancel();
                                                    pixiePromptTimer.purge();
                                                    pixiePromptTimer = new Timer();
                                                    pixiePrompt = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                here.set(true);
                                                                System.out.println("\nHere it is! Press [ENTER] to catch it!");
                                                                Thread.sleep(1000);
                                                                here.set(false);
                                                            }
                                                            catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    };
                                                    pixiePromptTimer.schedule(pixiePrompt, (random.nextInt(6) + 4) * 1000);
                                                }
                                            }
                                            if (Strings.println("You see it struggling to get free in your hands.")) break;
                                            if (Strings.println("You try to calm it down...")) break;
                                            if (Strings.println("It finally speaks to you.")) break;
                                            if (Strings.println("[Pixie]: What are you doing down here?")) break;
                                            if (Strings.println("[Pixie]: How did you even get here?")) break;
                                            if (Strings.println("[You]: Erm... I...")) break;
                                            if (Strings.println("[Pixie]: It doesn't matter... What do you want from me?")) break;
                                            if (Strings.println("[You]: You came up to me!")) break;
                                            if (Strings.println("[Pixie]: Right... I guess I got myself into this situation.")) break;
                                            if (Strings.println("[You]: What even is this place?!")) break;
                                            if (Strings.println("[You]: I've never seen someplace like it before!")) break;
                                            if (Strings.println("[Pixie]: It's... complicated. You wouldn't understand...")) break;
                                            if (Strings.println("[You]: Come on, tell me!")) break;
                                            if (Strings.println("[Pixie]: Fine, later, though. We should probably get out of here as soon as possible.")) break;
                                            if (Strings.println("[You]: What do you mea---...")) break;
                                            if (Strings.println("You couldn't even finish your sentence, when the cave starts to rumble.")) break;
                                            if (Strings.println("The ceiling starts to collapse!")) break;
                                            if (Strings.println("[Pixie]: Quick! Follow me.")) break;
                                            if (Strings.println("In all the confusion, you let go of the pixie.")) break;
                                            if (Strings.println("You run toward the pixie as fast as you can!")) break;
                                            if (Strings.println("Press [ENTER] as many times as you can! The more you press it, the faster you'll run!")) break;
                                            int enterCounter = 0;
                                            AtomicBoolean continuePrompt = new AtomicBoolean(true);
                                            Timer afterTimer = new Timer();
                                            TimerTask after = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        continuePrompt.set(false);
                                                    }
                                                    catch(Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            afterTimer.schedule(after, 2500);
                                            while(true) {
                                                Main.consoleInput.readLine();
                                                System.out.println("...");
                                                enterCounter++;
                                                if(!continuePrompt.get()) {
                                                    break;
                                                }
                                            }
                                            if(enterCounter < 8) {
                                                if (Strings.println("You got crushed by the collapsing cave.")) break;
                                                if (stats.minusHP(100)) continue;
                                            }
                                            else if(enterCounter < 13) {
                                                if (Strings.println("You get seriously hurt, but you make it out alive.")) break;
                                                if (stats.minusHP(Utility.pickBetween(40, 65))) continue;
                                            }
                                            else if(enterCounter < 18) {
                                                if (Strings.println("The cave tumbles onto you a little bit, you make it out fine!")) break;
                                                if (stats.minusHP(Utility.pickBetween(25, 40))) continue;
                                            }
                                            else {
                                                if (Strings.println("You make it out of the cave barely hurt.")) break;
                                                if (stats.minusHP(Utility.pickBetween(3, 9))) continue;
                                            }
                                            Thread.sleep(2000);
                                            System.in.read(new byte[System.in.available()]);//read and ignore
                                            if (Strings.println("[Pixie]: You kinda suck at this...")) break;
                                            if (Strings.println("[Pixie]: Come on, lemme heal you...")) break;
                                            stats.resetHP();
                                            if (Strings.println("[You]: Thanks")) break;
                                            stats.setProgress(3);
                                            Utility.checkpoint(stats, username);
                                        }
                                        if(stats.getProgress() == 3) {
                                            stats = Utility.generateStats(username);
                                            if (Strings.println("[Pixie]: So, why did you go down there?")) break;
                                            if (Strings.println("[You] (Out of breath): Hold on, give me a break... First of all, can you tell me what just happened down there?")) break;
                                            if (Strings.println("[Pixie]: Well, basically the entire cave fell apart since you touched the mellifliud. The mellifluid was the weird glowing liquid thing. Since it was the only thing holding the cave together, and you disturbed it, it fell apart.")) break;
                                            if (Strings.println("[You]: Uhh... Ok then.")) break;
                                            if (Strings.println("[Pixie]: So can you tell me now?")) break;
                                            if (Strings.println("[You]: Well, I was chased by a bear, and the cave was the only place I could hide.")) break;
                                            if (Strings.println("[Pixie]: Hmm... Well now I have no home.")) break;
                                            if (Strings.println("[You]: Wait, you live down there?")) break;
                                            if (Strings.println("[Pixie]: Correction, I LIVED down there.")) break;
                                            if (Strings.println("[You]: Oh...")) break;
                                            if (Strings.println("[You]: Anyways, the sun's going to set soon. We should probably get going.")) break;
                                            if (Strings.println("[Pixie]: Get going? Go where?")) break;
                                            if (Strings.println("[You]: To find shelter somewhere! Did you just want to stay here out in the open at night?")) break;
                                            if (Strings.println("[Pixie]: I guess you're right.")) break;
                                            stats.setProgress(4);
                                            Utility.checkpoint(stats, username);
                                        }
                                        if(stats.getProgress() == 4) {
                                            stats = Utility.generateStats(username);
                                            if (Strings.println("You and the pixie start walking through the forest to find better shelter somewhere.")) break;
                                            if (Strings.println("[Pixie]: So, what's your name?")) break;
                                            if (Strings.println("[You]: Mine name is " + username + ". What yours?")) break;
                                            if (Strings.println("[Pixie]: Actually, I don't have a name.")) break;
                                            if (Strings.println("[You]: So what should I call you, then?")) break;
                                            if (Strings.println("[Pixie]: I don't kow. I guess you can give me a name...")) break;
                                            try {
                                                stats.other.clone();
                                            }
                                            catch(Exception e) {
                                                stats.other = new String[]{};
                                            }
                                            System.out.println("What do you want to name the pixie?");
                                            while(true) {
                                                System.out.print("Enter name: ");
                                                String name = Main.consoleInput.readLine();
                                                if(!name.isBlank()) {
                                                    System.out.println("\nAre you sure you want the name \"" + name + "\"?\n1) Yes\n2) No");
                                                    String confirmPixieName = Main.consoleInput.readLine();
                                                    if(confirmPixieName.equalsIgnoreCase("1")) {
                                                        stats.other = ArrayModification.append(stats.other, name);
                                                        break;
                                                    }
                                                    else if(confirmPixieName.equalsIgnoreCase("2")) {
                                                        System.out.println("Enter new pixie name.");
                                                    }
                                                    else if(confirmPixieName.equalsIgnoreCase("/exit")) {
                                                        System.exit(0);
                                                    }
                                                    else if(confirmPixieName.equalsIgnoreCase("/quit")) {
                                                        break gameLoop;
                                                    }
                                                    else {
                                                        invalid();
                                                    }
                                                }
                                                else {
                                                    System.out.println("That's an invalid name! Please try again.");
                                                }
                                            }
                                            if (Strings.println("[You]: I guess I can call you " + stats.other[0] + ".")) break;
                                            if (Strings.println("[" + stats.other[0] + "]: Alright, sounds good!")) break;
                                            if (Strings.println("You continue walking through the forest, the sun getting lower and lower in the sky...")) break;
                                            if (Strings.println("[" + stats.other[0] + "]: So what were you doing all the way out here?")) break;
                                            int dialogue1 = Strings.dialogue("Choose your reply:", new String[]{"I wanted to go exploring.", "My friends ditched me.", "I sneaked out."});
                                            if(dialogue1 == 0) {
                                                break;
                                            }
                                            else if(dialogue1 == 1) {
                                                if (Strings.println("[You]: I wanted to go exploring.")) break;
                                                if (Strings.println("[" + stats.other[0] + "]: Cool. I was just sleeping until I heard you come in the cave.")) break;
                                            }
                                            else if(dialogue1 == 2) {
                                                if (Strings.println("[You]: My friends ditched me.")) break;
                                                if (Strings.println("[" + stats.other[0] + "]: That sucks. I was just sleeping until I heard you come in the cave.")) break;
                                            }
                                            else if(dialogue1 == 3) {
                                                if (Strings.println("[You]: I sneaked out.")) break;
                                                if (Strings.println("[" + stats.other[0] + "]: Interesting. I was just sleeping until I heard you come in the cave.")) break;
                                            }
                                            if (Strings.println("You see another cave up ahead and think it would be a good place to spend the night.")) break;
                                            if (Strings.println("[You]: Look! Over there, another cave. We can sleep in there.")) break;
                                            if (Strings.println("[" + stats.other[0] + "]: Alright, sure.")) break;
                                            if (Strings.println("You both go into the cave and rest there for the night...")) break;
                                            stats.setProgress(5);
                                            Utility.checkpoint(stats, username);
                                        }
                                        if(stats.getProgress() == 5) {
                                            stats = Utility.generateStats(username);
                                            if (Strings.println("Morning comes...")) break;
                                            if (Strings.println("You both go out of the cave, and you start heading in the direction you think home is.")) break;
                                            if (Strings.println("ZOOOOOOOOOOOOOM!")) break;
                                            if (Strings.println("You hear something and look up.")) break;
                                            if (Strings.println("You see a plane flying above you, fairly low to the ground.")) break;
                                            if (Strings.println("You get a little worried, because you've never seen a plane like that.")) break;
                                            if (Strings.println("Thank you for playing. The rest of the game is not yet made. Come back soon!")) break;
                                            break;
                                        }
                                    }
                                }
                            } else if (mainMenuInput.equalsIgnoreCase("2")) {
                                exit();
                            } else if (mainMenuInput.equals("3")) {
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
                            } else if (mainMenuInput.equalsIgnoreCase("4")) {
                                System.out.println("Logging out...");
                                username = "";
                                break;
                            } else if (mainMenuInput.equals("5")) {
                                accountOptionsLoop:
                                while (true) {
                                    System.out.println("""
                                                                                
                                            Account Options
                                            ===============
                                            1) Change email
                                            2) Change username
                                            3) Change password
                                            4) Delete account
                                            5) Request my information""");
                                    String accountOptionsInput = Main.consoleInput.readLine();
                                    if (accountOptionsInput.equalsIgnoreCase("1")) {
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
                                                } else {
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
                                    } else if (accountOptionsInput.equalsIgnoreCase("2")) {
                                        System.out.print("Enter new username: ");
                                        String newUsername = Main.consoleInput.readLine();

                                        Statement stmt = Main.conn.createStatement();
                                        String query = "SELECT * FROM pbm.accounts WHERE username = \"" + newUsername + "\"";
                                        ResultSet rs = stmt.executeQuery(query);
                                        if (rs.next()) {
                                            System.out.println("Username is taken! Please try again.");
                                        } else {
                                            System.out.println("Updating username...");
                                            String query3 = "UPDATE pbm.accounts SET username = \"" + newUsername + "\" WHERE username = \"" + username + "\"";
                                            PreparedStatement stmt3 = Main.conn.prepareStatement(query3);
                                            stmt3.executeUpdate();
                                            username = newUsername;
                                            System.out.println("Username has been updated!");
                                        }
                                    } else if (accountOptionsInput.equalsIgnoreCase("3")) {
                                        String newPassword = PasswordField.readPassword("New password: ");
                                        String confirm = PasswordField.readPassword("Confirm password: ");
                                        if (newPassword.equals(confirm)) {
                                            System.out.println("Updating password...");
                                            String query = "UPDATE pbm.accounts SET password = \"" + newPassword + "\" WHERE username = \"" + username + "\"";
                                            PreparedStatement stmt = Main.conn.prepareStatement(query);
                                            stmt.executeUpdate();
                                            System.out.println("Password has been updated!");
                                        } else {
                                            System.out.println("Passwords do not match! Please try again.");
                                        }
                                    } else if (accountOptionsInput.equalsIgnoreCase("4")) {
                                        System.out.print("We're sad to see you go... Why are you deleting your account?\nReason: ");
                                        String reason = Main.consoleInput.readLine();
                                        System.out.println("\nIf you delete your account, everything will be lost!");
                                        String enteredPassword = PasswordField.readPassword("Enter you password to continue: ");
                                        if (enteredPassword.equalsIgnoreCase(getPassword(username))) {
                                            System.out.println("Deleting account...");

                                            String query = "DELETE FROM pbm.accounts WHERE username = \"" + username + "\"";
                                            PreparedStatement stmt = Main.conn.prepareStatement(query);
                                            stmt.executeUpdate();

                                            String query2 = "INSERT INTO pbm.feedback (account, feedback) VALUES (?, ?)";
                                            PreparedStatement stmt2 = Main.conn.prepareStatement(query2);
                                            stmt2.setString(1, username);
                                            stmt2.setString(2, reason);
                                            stmt2.executeUpdate();

                                            username = "";

                                            System.out.println("Account deleted!");
                                            break mainMenuLoop;
                                        } else {
                                            System.out.println("Incorrect password.");
                                        }
                                    }
                                    else if(accountOptionsInput.equalsIgnoreCase("5")) {
                                        System.out.println("Please wait while we gather your information... (This won't take long)");

                                        Statement stmt = Main.conn.createStatement();
                                        String query = "SELECT * FROM pbm.accounts WHERE username = \"" + username + "\"";
                                        ResultSet rs = stmt.executeQuery(query);
                                        rs.next();

                                        String from = "projectbloodmoon.services";
                                        String pass = "dpassgmail";
                                        String[] to = { rs.getString("email") }; // list of recipient email addresses
                                        String subject = "Project: Blood Moon, Information We Have About You";
                                        String body = "Here is all of the information we have about you:\n\nUsername: " + username + "\nEmail: " + rs.getString("email") + "\nCharacter gender: " + rs.getString("gender") + "\nCharacter race: " + rs.getString("race") + "\nSerialized game stats: " + rs.getString("stats") + "\n\nWhat's serialized information? On the title screen of Project: Blood Moon, type \"/serial\" and we'll send you a copy of deserialized information.\n\nIf you see anything out of place, please let us know by replying to this email.";
                                        sendFromGMail(from, pass, to, subject, body);

                                        Strings.println("We've sent your information to your email.\n(Press [ENTER] to continue)");
                                        break;
                                    }
                                    else if (accountOptionsInput.equalsIgnoreCase("/exit")) {
                                        exit();
                                    } else if (accountOptionsInput.equalsIgnoreCase("/quit")) {
                                        break;
                                    } else if (accountOptionsInput.equalsIgnoreCase("/stats")) {
                                        System.out.println("Cannot view stats because you are not in a game.");
                                    } else if (accountOptionsInput.equalsIgnoreCase("/inventory")) {
                                        System.out.println("Cannot view inventory because you are not in a game.");
                                    } else invalid();
                                }
                            }
                            else if(mainMenuInput.equalsIgnoreCase("/serial")) {
                                System.out.println("Please wait...");

                                Statement stmt = Main.conn.createStatement();
                                String query = "SELECT email FROM pbm.accounts WHERE username = \"" + username + "\"";
                                ResultSet rs = stmt.executeQuery(query);
                                rs.next();

                                String from = "projectbloodmoon.services";
                                String pass = "dpassgmail";
                                String[] to = { rs.getString("email") }; // list of recipient email addresses
                                String subject = "Project: Blood Moon, Deserialized Information We Have About You";
                                String body = "Serialized information is just a piece of information that has been turned into text form.\nSerialized information isn't readable, so here's a break down of that information.\n\nCharacter's HP: " + stats.getHP() + "\nCharacter's inventory: " + Arrays.toString(stats.getInventory()) + "\nProgress level: " + stats.getProgress() + "\nOther information: " + Arrays.toString(stats.other) + "\n\nIf you have any questions, please let us know by replying to this email.";
                                sendFromGMail(from, pass, to, subject, body);

                                Strings.println("We've sent deserialized information to your email.\n(Press [ENTER] to continue)");
                            }
                            else if (mainMenuInput.equalsIgnoreCase("/exit")) {
                                exit();
                            } else if (mainMenuInput.equalsIgnoreCase("/quit")) {
                            } else if (mainMenuInput.equalsIgnoreCase("/stats")) {
                                System.out.println("Cannot view stats because you are not in a game.");
                            } else if (mainMenuInput.equalsIgnoreCase("/inventory")) {
                                System.out.println("Cannot view inventory because you are not in a game.");
                            } else invalid();
                        }
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
            else if(loginSignupInput.equals("5")) {
                System.out.print("Forgot username\nEmail: ");
                String email = Main.consoleInput.readLine();
                System.out.println("Fetching info...");
                Statement stmt = Main.conn.createStatement();
                String query = "SELECT * FROM pbm.accounts WHERE email = \"" + email + "\"";
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    Statement stmt2 = Main.conn.createStatement();
                    String query2 = "SELECT username FROM pbm.accounts WHERE email = \"" + email + "\"";
                    ResultSet rs2 = stmt2.executeQuery(query2);
                    rs2.next();
                    System.out.println("The username connected to that account is: " + rs2.getString("username") + "\n(Press [ENTER] to continue)");
                    Main.consoleInput.readLine();
                }
                else {
                    System.out.println("That email is not found in our system.");
                }
            }
            else if(loginSignupInput.equals("6")) {
                System.out.print("Forgot email\nUsername: ");
                String username = Main.consoleInput.readLine();
                System.out.println("Fetching info...");
                Statement stmt = Main.conn.createStatement();
                String query = "SELECT * FROM pbm.accounts WHERE username = \"" + username + "\"";
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    Statement stmt2 = Main.conn.createStatement();
                    String query2 = "SELECT email FROM pbm.accounts WHERE username = \"" + username + "\"";
                    ResultSet rs2 = stmt2.executeQuery(query2);
                    rs2.next();
                    System.out.println("The email connected to that account is: " + rs2.getString("email") + "\n(Press [ENTER] to continue)");
                    Main.consoleInput.readLine();
                }
                else {
                    System.out.println("That username is not found in our system.");
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
            else if(loginSignupInput.equalsIgnoreCase("/adm")) {
                String password = PasswordField.readPassword("Enter password: ");
                if(password.equals(getPassword("admin"))) {
                    System.out.println("ADMIN ACCOUNT CANNOT BE ACTIVATED AT THIS TIME");
                    username = "admin";
                }
                else {
                    System.out.println("Incorrect password!");
                }
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

    public static String login() throws IOException, SQLException {
        System.out.print("Username: ");
        String username = Main.consoleInput.readLine();
        String password = PasswordField.readPassword("Password: ");
        System.out.println("Logging in...");

        Statement stmt = Main.conn.createStatement();
        String query = "SELECT * FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            if(rs.getString("password").equals(password)) {
                stats = Utility.generateStats(username);
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
                            String query3 = "INSERT INTO pbm.accounts (username, password, email, gender, race, stats) VALUES (?, ?, ?, ?, ?, ?)";
                            PreparedStatement stmt3 = Main.conn.prepareStatement(query3);
                            stmt3.setString(1, username);
                            stmt3.setString(2, password);
                            stmt3.setString(3, email);
                            stmt3.setString(6, Serialization.serialize(new Stats(100, new String[]{}, 0)).getData());
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

    public static void resetProgress() throws SQLException {
        stats.setProgress(0);
        stats.setHP(100);
        stats.setInventory(new String[]{});
        stats.other = new String[]{};
        Utility.saveStats(stats, username);
    }

    public static boolean checkIfEmailExists(String email) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT * FROM pbm.accounts WHERE email = \"" + email + "\"";
        return stmt.executeQuery(query).next();
    }

    public static int emailCode(String email) {
        int number = generateRandom6DigitCode();

        String from = "projectbloodmoon.services";
        String pass = "dpassgmail";
        String[] to = { email }; // list of recipient email addresses
        String subject = "Project: Blood Moon verification code";
        String body = "Your verification code is: " + number;

        sendFromGMail(from, pass, to, subject, body);

        return number;
    }
    public static int generateRandom6DigitCode() {
        Random random = new Random();
        String[] numbers = new String[]{"0", "0", "0", "0", "0", "0"};
        String stringResult = "";
        for(String number : numbers) {
            number = String.valueOf(random.nextInt(10));
            stringResult += (number);
        }
        return Integer.parseInt(stringResult);
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