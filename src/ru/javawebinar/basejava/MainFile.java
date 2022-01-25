package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dir = new File("./src");
        printDirectoryDeeply(dir, 0);
    }

    private static void printDirectoryDeeply(File directory, int indent) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= indent; i++) {
                    sb.append('\t');
                }
                if (file.isDirectory()) {
                    System.out.println(sb + "Directory: " + file.getName());
                    indent++;
                    printDirectoryDeeply(file, indent);
                    indent--;
                } else if (file.isFile()) {
                    System.out.println(sb + "File: " + file.getName());
                }
            }
        }
    }
}
