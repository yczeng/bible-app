package com.android.biblio.app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class ReaderActivity extends AppCompatActivity {
    Button bookButton;
    Button chapterButton;
    JSONBible kjv;
    ViewPager biblePager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        // set the bookbutton name to be book title
        bookButton = findViewById(R.id.reader_bookButton);
        bookButton.setText(GlobalVariable.getInstance().getBook());

        // set chapter button to be current chapter
        chapterButton = findViewById(R.id.reader_chapterButton);
        chapterButton.setText(String.valueOf(GlobalVariable.getInstance().getChapter()));

        // grab references to global variables
        kjv = GlobalVariable.getInstance().getKjv();
        String book = GlobalVariable.getInstance().getBook();
        final int chapter = GlobalVariable.getInstance().getChapter();

        // create the array of strings containing the chapters' texts
        // for this book
        int chapterNum = kjv.getChapterCount(book);
        List<String> arr = new ArrayList<String>();
        for (int i = 1; i <= chapterNum; i++){
            arr.add(kjv.get(book, i));
        }
        String[] arrList = new String[arr.size()];
        arrList = arr.toArray(arrList);

        // create the viewpager and corresponding adapter
        // that will scroll through the chapter fragments
        biblePager = findViewById(R.id.biblepager);
        biblePager.setAdapter(new ReaderPagerAdapter(getSupportFragmentManager(), arrList, chapterButton));
        biblePager.setCurrentItem(chapter-1);
        biblePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                GlobalVariable.getInstance().setChapter(position);
                chapterButton.setText("" + (position + 1));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final BottomNavigationView botnavbar = findViewById(R.id.botnavbar);
        botnavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.botnavbar_bookmark:
                        generic_popup(R.layout.bookmark_popup);
                        return true;
                    case R.id.botnavbar_compare:
                        generic_popup(R.layout.compare_popup);
                        return true;
                    case R.id.botnavbar_notes:
                        generic_popup(R.layout.notes_popup);
                        return true;
                    case R.id.botnavbar_readaloud:
                        generic_popup(R.layout.readaloud_popup);
                        return true;
                    case R.id.botnavbar_textoptions:
                        generic_popup(R.layout.textopts_popup);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    // Generates a pop up of all books
    public void bookPopUp(View view){
        // make a popup builder
        AlertDialog.Builder bookpopupDialog = new AlertDialog.Builder(this);

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
        GridView grid_oldtest = dialogView.findViewById(R.id.buttongrid_oldtest);
        grid_oldtest.setAdapter(new ButtonGridAdapter(this, getSupportFragmentManager(),
                bookgridPane, bookButton, chapterButton, old_test, true, true, biblePager));

        GridView grid_newtest = dialogView.findViewById(R.id.buttongrid_newtest);
        grid_newtest.setAdapter(new ButtonGridAdapter(this, getSupportFragmentManager(),
                bookgridPane, bookButton, chapterButton, new_test, true, true, biblePager));

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
        grid_chapters.setAdapter(new ButtonGridAdapter(this, null, chaptergridPane,
                chapterButton, null, chapnums, false, true, biblePager));

        // display the dialog
        chaptergridPane.show();
    }

    private void generic_popup(int layout) {
        // make a popup builder
        AlertDialog.Builder popupDialogBuilder = new AlertDialog.Builder(this);

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(layout, null);
        popupDialogBuilder.setView(dialogView);
        AlertDialog popupDialog = popupDialogBuilder.create();

        // display the dialog
        try{
            popupDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams wmlp = popupDialog.getWindow().getAttributes();
            BottomNavigationView botnavbar = findViewById(R.id.botnavbar);
            wmlp.y = botnavbar.getTop() - botnavbar.getBottom()/2 - botnavbar.getHeight();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        popupDialog.show();
    }
}
