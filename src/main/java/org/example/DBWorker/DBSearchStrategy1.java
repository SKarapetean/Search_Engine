package org.example.DBWorker;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DBSearchStrategy1 extends DBSearchStrategy {
    public DBSearchStrategy1() {}

    String search(String str) {
        try(FileReader fr = new FileReader(DB.getDb())) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNext()) {
                String[] arr = scan.nextLine().split(" : ");
                if (arr[0].equals(str)) {
                    return arr[1];
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }
}

