package org.example.DBWorker;

import Collections.Lists.MyLinkedList;
import org.example.Word.Word;
import org.example.Text.Text;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;

public class DB{
    private static volatile DB instance = null;
    private static Object mutex = new Object();
    private File wordsAlphabet;

    private static final HashMap<Word, MyLinkedList<String>> dbRepresentation = new HashMap<>();
    private static File DB;
    private DBSearchStrategy searchStrategy;
    private DB(File wordsAlphabet) {
        this.wordsAlphabet = wordsAlphabet;
    }
    public static DB getInstance(DBSearchStrategy strategy, File wordsAlphabet) {
        DB result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    result = instance = new DB(wordsAlphabet);
                    result.DB = new File("/home/sergey/IdeaProjects/Search_Engine/src/main/java/org/example/DBWorker/db.txt");
                }
            }
            result.setSearchStrategy(strategy);
        }

        return result;
    }



    public static File getDb() {
        return DB;
    }

    public static HashMap<?,?> getDbRepresentation() {
        return (HashMap<?, ?>) dbRepresentation.clone();
    }

    public synchronized void setSearchStrategy(DBSearchStrategy strategy) {
        this.searchStrategy = strategy;
    }

    public synchronized String search(String str) {
        return this.searchStrategy.search(str);
    }

    public synchronized boolean write(String word) {
        Word w = new Word(word);
        if ( w != null) {
            try (RandomAccessFile fr = new RandomAccessFile(DB, "rwd")) {
                fr.seek(fr.length());
                fr.write(word.getBytes());
                fr.write('\n');
            } catch (IOException e) {
                System.err.println(e);
            }
            return true;
        }
        return false;
    }

    private void write(StringBuilder str) {
        try (RandomAccessFile fr = new RandomAccessFile(DB, "rwd")) {
            fr.seek(fr.length());
            fr.write(str.toString().getBytes());
            fr.write('\n');
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void write(File file) {
        Text t = new Text();
        t.fileReader(file);
        HashSet<Word> text = t.getTxt();
        String filePath = file.getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        for (Word w : text) {
            if (!dbRepresentation.containsKey(w)) {
                MyLinkedList<String> ml = new MyLinkedList<>();
                ml.add(filePath);
                dbRepresentation.put(w, ml);
                sb.append(w.getWord()).append(" : ").append(ml);
                this.write(sb);
                sb.delete(0, sb.length());
            }
        }

    }

    public synchronized void write(File file1, File file2) {
        Text t1 = new Text();
        Text t2 = new Text();
        t1.fileReader(file1);
        t2.fileReader(file2);
        HashSet<Word> tw1 = t1.getTxt();
        HashSet<Word> tw2 = t2.getTxt();
        HashSet<Word> and = new HashSet<>();
        MyLinkedList<String> ml = new MyLinkedList<>();
        ml.add(t1.getFilePath());
        ml.add(t2.getFilePath());
        for (Word w : tw1) {
            if (tw2.contains(w)) {
                and.add(w);
            }
        }

        tw1.removeAll(and);
        tw2.removeAll(and);
        StringBuilder str = new StringBuilder();
        for (Word w : and) {
            dbRepresentation.put(w, new MyLinkedList<>(ml));
            str.append(w.getWord()).append(" : ").append(ml);
            this.write(str);
            str.delete(0, str.length());
        }

        ml.removeLast();
        ml.set(0, file1.getAbsolutePath());
        for (Word w : tw1) {
            dbRepresentation.put(w, new MyLinkedList<>(ml));
            str.append(w.getWord()).append(" : ").append(ml);
            this.write(str);
            str.delete(0, str.length());
        }

        ml.set(0, file2.getAbsolutePath());
        for (Word w : tw2) {
            dbRepresentation.put(w, new MyLinkedList<>(ml));
            str.append(w.getWord()).append(" : ").append(ml);
            this.write(str);
            str.delete(0, str.length());
        }
    }

}
