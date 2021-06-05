package com.denesgarda.ProjectBloodMoon.utility;

import com.denesgarda.JarData.data.Serialized;
import com.denesgarda.JarData.data.statics.Serialization;
import com.denesgarda.ProjectBloodMoon.Main;
import com.denesgarda.ProjectBloodMoon.game.data.Stats;
import com.denesgarda.ProjectBloodMoon.game.data.Strings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

public class Utility {
    public static String decrypt(String text) {
        char[] characters = text.toCharArray();
        for(int i = 0; i < characters.length; i++) {
            characters[i] -= 3;
        }
        return String.valueOf(characters);
    }
    public static String encrypt(String text) {
        char[] characters = text.toCharArray();
        for(int i = 0; i < characters.length; i++) {
            characters[i] += 3;
        }
        return String.valueOf(characters);
    }

    public static void download(String url, String name) throws IOException {
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(name);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }

    public static Stats generateStats(String username) throws SQLException {
        Statement stmt2 = Main.conn.createStatement();
        String query2 = "SELECT stats FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs2 = stmt2.executeQuery(query2);

        rs2.next();

        return (Stats) new Serialized(rs2.getString("stats")).deSerialize();
    }
    public static void saveStats(Stats stats, String username) throws SQLException {
        String query = "UPDATE pbm.accounts SET stats = \"" + Serialization.serialize(stats).getData() + "\" WHERE username = \"" + username + "\"";
        PreparedStatement stmt = Main.conn.prepareStatement(query);
        stmt.executeUpdate();
    }

    public static void checkpoint(Stats stats, String username) throws SQLException {
        System.out.println("Checkpoint reached.\nSaving stats. Please wait...");
        saveStats(stats, username);
        System.out.println("Progress saved!\n");
    }
}
