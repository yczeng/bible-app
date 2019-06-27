package com.android.biblio.app;

public class GlobalVariable {


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

    ////////////////////////////////////////
    //// Current Bible & Book & Chapter ////
    ////////////////////////////////////////

    private String book = "Gen";
    private int chapter = 1;
    private JSONBible kjv;

    JSONBible getKjv(){return kjv;}
    void setKjv(JSONBible newKjv){kjv = newKjv;}


    String getBook(){return book;}
    void setBook(String newBook){book = newBook;}

    int getChapter(){return chapter;}
    void setChapter(int newChapter){
        chapter = newChapter;
        scrollPercent = 0;
    }


    /////////////////////////
    //// Pop-up Settings ////
    /////////////////////////

    private int textScaleSliderProgress = 50;
    private int textThemeRadioButton = 0;
    private String textThemeHighlight = "#555555";
    private int textFontFamButton = 0;
    private String textFontFamily = "fonts/CarroisGothicSC-Regular.ttf";
    private double scrollPercent = 0;

    public double getScrollPercent() {
        return scrollPercent;
    }

    public void setScrollPercent(double scrollPercent) {
        this.scrollPercent = scrollPercent;
    }

    public String getTextThemeHighlight() {
        return textThemeHighlight;
    }

    public void setTextThemeHighlight(String textThemeHighlight) {
        this.textThemeHighlight = textThemeHighlight;
    }

    public String getTextFontFamily() {
        return textFontFamily;
    }

    public void setTextFontFamily(String textFontFamily) {
        this.textFontFamily = textFontFamily;
    }

    public int getTextScaleSliderProgress() {
        return textScaleSliderProgress;
    }

    public void setTextScaleSliderProgress(int textScaleSliderProgress) {
        this.textScaleSliderProgress = textScaleSliderProgress;
    }

    public int getTextThemeRadioButton() {
        return textThemeRadioButton;
    }

    public void setTextThemeRadioButton(int textThemeRadioButton) {
        this.textThemeRadioButton = textThemeRadioButton;
    }

    public int getTextFontFamButton() {
        return textFontFamButton;
    }

    public void setTextFontFamButton(int textFontFamButton) {
        this.textFontFamButton = textFontFamButton;
    }
}
