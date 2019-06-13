package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONBible{
  private JSONArray jsonarray = null;

  public JSONBible(JSONArray jsonarray){
    this.jsonarray = jsonarray;
  }

  public String toString(){
    return this.jsonarray.toString();
  }
}
