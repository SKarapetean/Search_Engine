package org.example.Search;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class Search {
    public static boolean search(String string, File file) {

        try(FileReader fr = new FileReader(file)) {
            Scanner scan = new Scanner(fr);
            while(scan.hasNext()) {
                if (string.equals(scan.next())) {
                    return true;
                }
            }
        } catch(IOException e) {
            System.err.println(e);
        }
        return false;
    }
}
