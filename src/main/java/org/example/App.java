package org.example;

import org.example.DBWorker.DB;
import org.example.Word.Word;
import org.example.DBWorker.*;
import java.io.File;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        File file1 = new File("/home/sergey/IdeaProjects/Search_Engine/src/main/java/org/example/TxtFiles/1.txt");
        File file2 = new File("/home/sergey/IdeaProjects/Search_Engine/src/main/java/org/example/TxtFiles/2.txt");
        File file4 = new File("/home/sergey/IdeaProjects/Search_Engine/src/main/java/PDFFiles/Karapetyan Sergey.pdf");
        File file3 = new File("/home/sergey/IdeaProjects/Search_Engine/src/main/java/org/example/TxtFiles/words_alpha.txt");
        DBSearchStrategy strategy = new DBSearchStrategy1();
        DBSearchStrategy2 searchStrategy;
        searchStrategy = new DBSearchStrategy2();
        DB db = DB.getInstance(strategy, file3);
        db.write(file4);
        Word w = new Word();
        System.out.println("search");
        Scanner scan = new Scanner(System.in);
        while (true) {
            w.setWord(scan.next());
            System.out.println(db.search(w.getWord()));
        }
    }
}
