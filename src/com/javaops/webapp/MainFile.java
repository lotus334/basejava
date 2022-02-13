package com.javaops.webapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Path> list = null;
        try {
            list = Files.list(directory).filter(el -> el.getFileName().toString().equals("MainFile.java")).collect(Collectors.toList());
//            list = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(list);

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
}
