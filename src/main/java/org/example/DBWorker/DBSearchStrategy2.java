package org.example.DBWorker;

import org.example.Word.Word;

public class DBSearchStrategy2 extends DBSearchStrategy{
    String search(String word) {
        Word w = new Word(word);
        if (DB.getDbRepresentation().containsKey(w)) {
            return DB.getDbRepresentation().get(w).toString();
        }
        return null;
    }
}
