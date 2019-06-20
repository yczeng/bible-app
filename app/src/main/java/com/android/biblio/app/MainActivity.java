package com.android.biblio.app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private TextView textView;

    Dialog bookDialog;
    Button bookButton;
    Dialog chapterDialog;
    Button chapterButton;

    JSONArray verseList;
    JSONObject bibleMap;
    JSONBible kjv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creates the popup windows for book and chapter
        bookDialog = new Dialog(this);
        chapterDialog = new Dialog(this);

        textView = findViewById(R.id.text);

        bookButton = findViewById(R.id.bookButton);
        bookButton.setText(GlobalVariable.getInstance().getBook());

        chapterButton = findViewById(R.id.chapterButton);
        chapterButton.setText(String.valueOf(GlobalVariable.getInstance().getChapter()));

//        generateButtons();
    }

    public void generateButtons(){
        Button myButton = new Button(this);
        myButton.setText("Add Me");

        LinearLayout ll = (LinearLayout)findViewById(R.id.mainActivity);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
    }

    // Generates a pop up of all books
    public void bookPopUp(View view){
        Context context = this;
        bookDialog.setContentView(R.layout.book_pop_up);
        
        bookDialog.show();
    }

    // Updates global variable when click on new book
    public void changeBook(View v){
        String newBook = v.getTag().toString();
        GlobalVariable.getInstance().setBook(newBook);

        bookButton.setText(newBook);
        bookDialog.dismiss();
    }

    // Generates a pop up of all the chapters for given book
    public void chapterPopUp(View view){
        chapterDialog.setContentView(R.layout.chapter_pop_up);
        chapterDialog.show();
    }

    // Updates global variable when click on new chapter
    public void changeChapter(View v){
        int newChapter = Integer.parseInt(v.getTag().toString());
        GlobalVariable.getInstance().setChapter(newChapter);

        chapterButton.setText(v.getTag().toString());
        chapterDialog.dismiss();
    }

    // Moves on to the next page with json result of verse
    public void moveActivity(View view) {
        Intent intent = new Intent(this, ReaderActivity.class);

        kjv = new JSONBible(this, "kjv.json", "reformattedKjv2.json");

        String resultVerse = kjv.get(GlobalVariable.getInstance().getBook(), GlobalVariable.getInstance().getChapter());
        textView.setText(resultVerse);

//        Context context = getApplicationContext();
//        CharSequence text = resultVerse;
//        int duration = Toast.LENGTH_LONG;
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
        intent.putExtra("json", resultVerse);
        startActivity(intent);
        finish();
    }
}
