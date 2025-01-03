package test;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONBible{
  
  private JSONObject jsonobj = null;
  private JSONArray jsonarray = null;

  public JSONBible(JSONObject jsonobj, JSONArray jsonarray){
    this.jsonobj = jsonobj;
    this.jsonarray = jsonarray;
  }

  // This takes in two parameters: book and chapter, optional third parameter: verse.
  // returns a jsonobj containing results that pertain. 
  public String get(String book, int chapter){

      // converts the book's json into a json object
      JSONObject obj = new JSONObject(this.jsonobj.get(book).toString());

      return obj.getJSONArray("" + chapter).toString();
  }

  public String search(String text){
    JSONArray jsonResults = new JSONArray();
    
    for (int i = 0; i < this.jsonarray.length(); i++){
      JSONObject obj = this.jsonarray.getJSONObject(i);

      int search_position = obj.getString("text").indexOf(text);
      if (search_position != -1){
        obj.put("search_index", search_position);
        obj.put("query_length", text.length());
        jsonResults.put(obj);

      }
    }
    
    return jsonResults.toString();
  }

  public int getChapterNumber(JSONObject chapterNum, String book){
    return chapterNum.getInt(book);
  }

  public String toString(){
    return this.jsonobj.toString();
  }

}