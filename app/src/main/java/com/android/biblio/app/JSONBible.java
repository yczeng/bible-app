
package com.android.biblio.app;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JSONBible{

    private JSONArray jsonarray;
    private JSONObject jsonobj;
    private JSONObject chapcounts;
    private Context context;

    public JSONBible(Context context, String kjv, String kjvDict, String kjvChapterCounts){
        this.context = context;
        this.jsonarray = JSONBible.readJSONArray(context, kjv);
        this.jsonobj = JSONBible.readJSONObject(context,kjvDict);
        this.chapcounts = JSONBible.readJSONObject(context, kjvChapterCounts);
    }

    private int verse = 0;
    // This takes in two parameters: book and chapter, optional third parameter: verse.
    // returns a jsonobj containing results that pertain.
    public String get(String book, int chapter) {
        // converts the book's json into a json object
        JSONObject obj = null;
        try {
            obj = new JSONObject(this.jsonobj.get(book).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray chapterArray;
        try {
            chapterArray = obj.getJSONArray("" + chapter);

        } catch (JSONException e) {
            e.printStackTrace();
            chapterArray = null;
        }

        String result = "";

        for (int i = 0; i < chapterArray.length(); i++) {
            String verse = null;
            try {
                verse = chapterArray.get(i).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result += "<sup><font color=\"#808080\"><small>" + (i+1) + "</sup></font></small>" + "  " + verse + "<br>";
        }

        return result;
    }

    public JSONArray search(String text){
        JSONArray jsonResults = new JSONArray();

        for (int i = 0; i < this.jsonarray.length(); i++){
            JSONObject obj = null;

            try {
                obj = this.jsonarray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (obj.getString("text").contains(text)){
                    jsonResults.put(obj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonResults;
    }

    public int getChapterCount(String book){
        int result = 0;

        try{
            result = chapcounts.getInt(book);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String toString(){
        return this.jsonobj.toString();
    }



    // Reads a json object from a file
    public static JSONObject readJSONObject(Context context, String filename) {
        byte[] buffer = null;

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
        JSONObject obj = null;
        try {
            // grabs json object from the buffer
            json = new String(buffer, "UTF-8");

            // saves the json string as a JSONObject
            obj = new JSONObject(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    // Reads a json array from a file
    public static JSONArray readJSONArray(Context context, String filename) {
        byte[] buffer = null;

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
