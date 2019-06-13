package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestMain{

	public static final String kjvfile = "/home/yczeng/Documents/bibleapp2/app/src/main/assets/kjv.json";

	public static JSONArray verseList = null;
	public static JSONBible kjv = null;

	public static void main(String[] args) {
		verseList = readJSONArray(kjvfile);
		kjv = new JSONBible(verseList);
		System.out.println(kjv);
	}


	// Reads a json array from a file
	public static JSONArray readJSONArray(String filename) {
		JSONParser jsonParser = new JSONParser();
	    JSONArray jsonarray = null;

	    try (FileReader reader = new FileReader(kjvfile)) {
	        Object obj = jsonParser.parse(reader);
	        jsonarray = (JSONArray) obj;

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        System.exit(1);
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.exit(1);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        System.exit(1);
	    }

	    return jsonarray;
	}
}
