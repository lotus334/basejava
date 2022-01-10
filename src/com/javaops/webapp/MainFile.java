package com.javaops.webapp;

import java.io.File;

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

        printRecursivelyFiles(".", 0);
    }

    public static void printRecursivelyFiles(String startDir, int depthLineCounter) {
        File file = new File(startDir);
        if (file.isDirectory()) {
            printDepthLine(depthLineCounter);
            System.out.println(file.getName());
            String[] list = file.list();
            if (list != null) {
                depthLineCounter++;
                for (String name : list) {
                    printRecursivelyFiles(startDir + "/" + name, depthLineCounter);
                }
            }
        } else {
            printDepthLine(depthLineCounter);
            System.out.println(file.getName());
        }
    }

    public static void printDepthLine(int depthLineCounter) {
        for (int i = 0; i < depthLineCounter; i++) {
            System.out.print("-");
        }
    }
}