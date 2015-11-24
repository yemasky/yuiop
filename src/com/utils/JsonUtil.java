package com.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JsonUtil {
	private static JSONObject objJson = null;
	public static void main(String[] args) {
		String STR_JSON = "{\"name\":\"Michael\",\"address\":{\"city\":\"Suzou\",\"street\":\" Changjiang Road \",\"postcode\":100025},\"blog\":\"http://www.ij2ee.com\"}";
		String xml = json2XML(STR_JSON);
        System.out.println("xml = "+xml);
        String json = xml2JSON(xml);
        System.out.println("json="+json);

	}
	
	public static String xml2JSON(String xml){
        return new XMLSerializer().read(xml).toString();
    }
     
    public static String json2XML(String json){
        JSONObject jobj = JSONObject.fromObject(json);
        String xml =  new XMLSerializer().write(jobj);
        return xml;
    }
    
    public static JSONObject jsonParse(String jsonString, String key) {
    	JSONObject jsonObj = JSONObject.fromObject(jsonString);
    	objJson = jsonObj = (JSONObject) jsonObj.get(key);
        return jsonObj;
    }
    
    public static JSONObject jsonGet(String key) {
    	JSONObject jsonObj = null;
    	if(objJson != null) {
    		objJson = jsonObj = (JSONObject) objJson.get(key);
    	}
        return jsonObj;
    }
    
    public static JSONObject jsonParse(JSONObject jsonObj, String key) {
        return (JSONObject) jsonObj.get(key);
    }
    
    public static JSONArray jsonParse(String jsonString, int key) {
    	JSONArray jsonObj = JSONArray.fromObject(jsonString);
        return (JSONArray) jsonObj.get(key);
    }
    
    
}
