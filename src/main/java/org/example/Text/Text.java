package org.example.Text;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.Word.Word;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Text {
    private HashSet<Word> words;
    private String fileName;

    private String filePath;
    public Text() {
        this.words = new HashSet<>();
    }

    public boolean fileReader(File file) {
        if (isPdf(file)) {
            readFromPDF(file);
        }
        if (isTxt(file)) {
            readFromTxt(file);
        }
        this.filePath = file.getAbsolutePath();
        this.fileName = file.getName();

        return true;
    }

    private void readFromPDF(File file) {
        try {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper textStripper = new PDFTextStripper();
            String[] text = textStripper.getText(document).split(" ");
            for (String s : text) {
                Word w = new Word();
                w.setWord(s);
                if (w.getWord() != null) {
                    this.words.add(w);
                }
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromTxt(File file) {
        try(FileReader reader = new FileReader(file)) {

            Scanner scan = new Scanner(reader);
            while (scan.hasNext()) {
                Word w = new Word();
                w.setWord(scan.next());
                if (w.getWord() != null) {
                    words.add(w);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isPdf(File file) {
        String fileName  = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension.equals("pdf");
    }

    private boolean isTxt(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension.equals("txt");
    }

    public HashSet<Word> getTxt() {
        return new HashSet<>(words);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
}
