package com.android.biblio.app;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONBible{

    private JSONObject jsonobj = null;
    private JSONArray jsonarray = null;

    public JSONBible(JSONObject jsonobj, JSONArray jsonarray){
        this.jsonobj = jsonobj;
        this.jsonarray = jsonarray;
    }

    private int verse = 0;
    // This takes in two parameters: book and chapter, optional third parameter: verse.
    // returns a jsonobj containing results that pertain.
    public String get(String book, int chapter) {
        JSONArray jsonResults = new JSONArray();

        for (int i = 0; i < this.jsonarray.length(); i++){
            JSONObject obj = null;

            try {
                obj = this.jsonarray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (obj.getString("book_id").equals(book)){
                    if (obj.getInt("chapter") == chapter){

                        // if the verse is 0, then no verse parse was given
                        // else only return the verse
                        if (verse == 0){
                            jsonResults.put(obj);
                        } else {
                            if (obj.getInt("verse") == verse){
                                jsonResults.put(obj);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return jsonResults.toString();
    }

    public String search(String text){
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

        return jsonResults.toString();
    }

    public int getChapterNumber(JSONObject chapterNum, String book){
        int result = 0;

        try{
            result = chapterNum.getInt(book);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String toString(){
        return this.jsonobj.toString();
    }

}
