package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class TestMain{

	public static final String kjvfile = "/home/yczeng/Documents/bibleapp2/app/src/main/assets/kjv.json";

	public static JSONArray verseList = null;
	public static JSONBible kjv = null;

	public static void main(String[] args) {
		verseList = readJSONArray(kjvfile);
		kjv = new JSONBible(verseList);
		// System.out.println(kjv);

		// String book, int chapter, int verse
		System.out.println(kjv.get("Gen", 3, 10));
	}


	// Reads a json array from a file
	public static JSONArray readJSONArray(String filename) {
		byte[] buffer = null;

		// grabs the file locally
		try{
			InputStream is = new FileInputStream(filename);
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
