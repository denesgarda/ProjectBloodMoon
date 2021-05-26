package com.denesgarda.ProjectBloodMoon.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
}
