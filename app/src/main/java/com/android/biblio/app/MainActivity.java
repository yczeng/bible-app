package com.android.biblio.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {
    Button bookButton;
    Button chapterButton;
    JSONBible kjv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kjv = new JSONBible(this, "kjv.json", "reformattedKjv3.json", "chapterCount.json", "bookName.json");
        GlobalVariable.getInstance().setKjv(kjv);

        bookButton = findViewById(R.id.bookButton);
        bookButton.setText(kjv.getBookFullName(GlobalVariable.getInstance().getBook()));

        chapterButton = findViewById(R.id.chapterButton);
        chapterButton.setText(String.valueOf(GlobalVariable.getInstance().getChapter()));
    }

    // Generates a pop up of all books
    public void bookPopUp(View view){
        // make a popup builder
        AlertDialog.Builder bookpopupDialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Settings);

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.book_pop_up, null);

        bookpopupDialog.setView(dialogView);

        AlertDialog bookgridPane = bookpopupDialog.create();

        // get the linear layout and add the button(s)
        String[] old_test = {"Gen", "Exod", "Lev", "Num", "Deut", "Josh",
                            "Judg", "Ruth", "1Sam", "2Sam", "1Kgs", "1Chr",
                            "2Chr", "Ezra", "Neh", "Esth", "Job", "Ps",
                            "Prov", "Eccl", "Song", "Isa", "Jer", "Lam",
                            "Ezek", "Dan", "Hos", "Joel", "Amos", "Obad",
                            "Jona", "Mic", "Nah", "Hab", "Zeph", "Hag",
                            "Zech", "Mal"};
        String[] new_test = {"Matt", "Mark", "Luke", "John",
                            "Acts", "Rom", "1Cor", "2Cor", "Gal", "Eph",
                            "Phil", "Col", "1Thess", "2Thess", "1Tim", "2Tim",
                            "Titus", "Phlm", "Heb", "Jas", "1Pet", "2Pet",
                            "1John", "2John", "3John", "Jude", "Rev"};
        ViewPager biblePager = null;

        // biblePager is not needed here - optional parameter that is null in this case.
        GridView grid_oldtest = dialogView.findViewById(R.id.buttongrid_oldtest);
        grid_oldtest.setAdapter(new ButtonGridAdapter(this, null, bookgridPane,
                bookButton, chapterButton, old_test, true, false, biblePager));
        GridView grid_newtest = dialogView.findViewById(R.id.buttongrid_newtest);
        grid_newtest.setAdapter(new ButtonGridAdapter(this, null, bookgridPane,
                bookButton, chapterButton, new_test, true, false, biblePager));


        // display the dialog
        bookgridPane.show();
    }

    // Generates a pop up of all the chapters for given book
    public void chapterPopUp(View view){
        // make a popup builder
        AlertDialog.Builder chapterpopupDialog = new AlertDialog.Builder(this);

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.chapter_pop_up, null);
        chapterpopupDialog.setView(dialogView);
        AlertDialog chaptergridPane = chapterpopupDialog.create();

        // create the button text dynamically depending on number of chapters in the selected book
        String book = GlobalVariable.getInstance().getBook();
        int numChapters = kjv.getChapterCount(book);
        String[] chapnums = new String[numChapters];
        for (int i = 0; i < numChapters; i++) {
            chapnums[i] = "" + (i+1);
        }

        // get the layout and add the button(s)
        GridView grid_chapters = dialogView.findViewById(R.id.buttongrid_chapters);

        // biblePager is not needed here - optional parameter that is null in this case.
        ViewPager biblePager = null;
        grid_chapters.setAdapter(new ButtonGridAdapter(this, null, chaptergridPane,
                chapterButton, null, chapnums, false, false, biblePager));

        // display the dialog
        chaptergridPane.show();
    }

    // Moves on to the next page with json result of verse
    public void moveActivity(View view) {
        Intent intent = new Intent(this, ReaderActivity.class);
        String resultVerse = kjv.get(GlobalVariable.getInstance().getBook(), GlobalVariable.getInstance().getChapter());

        intent.putExtra("json", resultVerse);
        startActivity(intent);
        finish();
    }
}
