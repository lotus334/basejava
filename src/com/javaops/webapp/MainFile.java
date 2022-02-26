package com.javaops.webapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFile {
    public static void main(String[] args) {
//        String filePath = ".\\.gitignore";

//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }

//        File dir = new File("./");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }

//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        printRecursivelyFiles("src/com/javaops/webapp");

        Path directory = Paths.get("/home/dmitriyvass/basejava/src/com/javaops/webapp");

        printRecursivelyFilesAndDirs(directory, 0);
    }

    public static void printRecursivelyFiles(String startDir) {
        File file = new File(startDir);
        if (file.isDirectory()) {
            String[] list = file.list();
            if (list != null) {
                for (String name : list) {
                    printRecursivelyFiles(startDir + "/" + name);
                }
            }
        } else {
            System.out.println(file.getName());
        }
    }

    public static void printRecursivelyFilesAndDirs(Path startDir, int offset) {
        if (Files.isDirectory(startDir)) {
            printFileWithOffset(startDir.toFile(), offset);
            try {
                offset++;
                int finalDepth = offset;
                Files.list(startDir).forEach(el -> printRecursivelyFilesAndDirs(el, finalDepth));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            printFileWithOffset(startDir.toFile(), offset);
        }
    }

    private static void printFileWithOffset(File file, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("      ");
        }
        System.out.println(file.getName());
    }
}
