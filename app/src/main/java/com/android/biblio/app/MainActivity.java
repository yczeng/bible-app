package com.android.biblio.app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    private TextView textView;

    Dialog bookDialog;
    Button bookButton;
    Dialog chapterDialog;
    Button chapterButton;

    JSONArray verseList;
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
    }

    // Generates a pop up of all books
    public void bookPopUp(View view){
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

        verseList = readJSONArray("kjv.json");
        kjv = new JSONBible(verseList);

        String resultVerse = kjv.get(GlobalVariable.getInstance().getBook(), GlobalVariable.getInstance().getChapter(), 1);
        textView.setText(resultVerse);
        intent.putExtra("json", resultVerse);
//        startActivity(intent);
//        finish();
    }

    // Reads a json array from a file
    public JSONArray readJSONArray(String filename) {
        byte[] buffer = null;
        Context context = this;

        // grabs the file locally
        try{
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String json = null;
        JSONArray array = null;
        try {
            // grabs json object from the buffer
            json = new String(buffer, "UTF-8");

            // saves the json string as a JSONObject
            array = new JSONArray(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }
}
