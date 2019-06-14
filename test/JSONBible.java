package test;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONBible{
	
  private JSONArray jsonarray = null;

  public JSONBible(JSONArray jsonarray){
    this.jsonarray = jsonarray;
  }

  private int verse = 0;

  // This takes in two parameters: book and chapter, optional third parameter: verse.
  // returns a jsonarray containing results that pertain. 
  public String get(String book, int chapter, int verse){
  	JSONArray jsonResults = new JSONArray();
  	String result = null;

  	for (int i = 0; i < this.jsonarray.length(); i++){
  		JSONObject obj = this.jsonarray.getJSONObject(i);

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
  	}
  	return jsonResults.toString();
  }

  public String toString(){
    return this.jsonarray.toString();
  }

}
