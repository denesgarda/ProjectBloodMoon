package com.denesgarda.ProjectBloodMoon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

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

        try {
            String zipFileName = "package.zip";
            String destDirectory = "";
            File destDirectoryFolder = new File(destDirectory);
            if (!destDirectoryFolder.exists()) {
                destDirectoryFolder.mkdir();
            }
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String filePath = /*destDirectory + File.separator + */zipEntry.getName();
                System.out.println("Unzipping " + filePath);
                if (!zipEntry.isDirectory()) {
                    FileOutputStream fos = new FileOutputStream(filePath);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        }
        catch(Exception e) {
            System.out.println("ERROR! Could not unzip package.");
            System.exit(0);
        }
    }
}
