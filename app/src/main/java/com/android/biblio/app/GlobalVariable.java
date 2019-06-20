package com.android.biblio.app;

public class GlobalVariable {
    private String book = "Gen";
    private int chapter = 1;
    private JSONBible kjv;

    private static GlobalVariable mInstance = null;

    private GlobalVariable() {
        // The empty class
    }

    static synchronized GlobalVariable getInstance() {
        if (null == mInstance) {
            mInstance = new GlobalVariable();
        }
        return mInstance;
    }

    JSONBible getKjv(){return kjv;}
    void setKjv(JSONBible newKjv){kjv = newKjv;}


    String getBook(){return book;}
    void setBook(String newBook){book = newBook;}

    int getChapter(){return chapter;}
    void setChapter(int newChapter){chapter = newChapter;}
}
