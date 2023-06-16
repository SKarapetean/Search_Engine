package org.example.Word;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.io.File;
import java.util.Scanner;

public class Word {
   // private static final String[] pluralSuffixes = {"s", "es"};
    //private static final String[] singularSuffixes = {"", "s"};
    private String word;
    public Word(){};

    public Word(String word){
        setWord(word);
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = this.cleaner(word).toLowerCase();
       // this.word = this.convertToSingular(this.word);
        this.word = this.normalize(this.word, new File("/home/sergey/IdeaProjects/Search_Engine/src/main/java/org/example/TxtFiles/words_alpha.txt"));
    }

    public String toString() {
        return word;
    }

    public String cleaner(String string) {
        return string.replaceAll("[^a-zA-Z]", "");
    }

//    public  String convertToSingular(String plural) {
//        for (int i = 0; i < pluralSuffixes.length; i++) {
//            String pluralSuffix = pluralSuffixes[i];
//            String singularSuffix = singularSuffixes[i];
//
//            if (plural.endsWith(pluralSuffix)) {
//                return plural.substring(0, plural.length() - pluralSuffix.length()) + singularSuffix;
//            }
//        }
//
//        return plural;
//    }

    private String normalize(String str, File file) {
        try(FileReader fr = new FileReader(file)) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNext()) {
                String s = scan.next();
                int ls = s.length();
                int lstr = str.length();
                int abs = lstr - ls;
                if (Math.abs(abs) <= 1 ) {
                    char[] ch1 = s.toCharArray();
                    char[] ch2 = str.toCharArray();
                    int i = ls - 1, j = lstr - 1, xorCount = 0;
                    while (i >= 0 && j >= 0 && xorCount < 2) {
                        if ((ch1[i] ^ ch2[j]) != 0) {
                            ++xorCount;
                            if (abs > 0) {
                                --j;
                            } else {
                                --i;
                            }
                        } else {
                            --j;
                            --i;
                        }
                    }
                    if (xorCount != 2) {
                        return str;
                    }
                }
            }
        }catch (IOException e) {
            System.err.println(e);

        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        if (this.word == null) {
            return 0;
        }

        int result = 17;
        result = 31 * result + this.word.hashCode();

        return result;
    }
}
