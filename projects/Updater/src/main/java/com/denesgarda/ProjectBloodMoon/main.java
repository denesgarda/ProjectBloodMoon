package com.denesgarda.ProjectBloodMoon;

import java.io.File;

public class main {
    public static void main(String[] args) {
        System.out.println("Installing update...");

        File homeDirectory = new File(new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().length() - 2));
        for(String item : homeDirectory.list()) {
            File file = new File(item);
            if(!(file.getName().equals("Updater.jar") || file.getName().equals("update.bat") || file.getName().equals("update.command") || file.getName().equals("update.sh") || file.getName().equals("package.zip"))) {
                file.delete();
            }
        }
    }
}
