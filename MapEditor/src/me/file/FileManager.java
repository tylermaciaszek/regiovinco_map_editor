/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import me.data.DataManager;
import me.data.Map;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 *
 * @author Tyler Maciaszek
 */
public class FileManager implements AppFileComponent {

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	DataManager dataManager = (DataManager)data;
	dataManager.reset();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// AND NOW LOAD ALL THE ITEMS
	JsonArray jsonItemArray = json.getJsonArray("Items");
	for (int i = 0; i < jsonItemArray.size(); i++) {
	    JsonObject jsonItem = jsonItemArray.getJsonObject(i);
	    Map map = loadItem(jsonItem);
	    dataManager.addMap(map);
	}
        System.out.println(dataManager.getMap().get(0).getBackgroundColor());
    }
    
     public Map loadItem(JsonObject jsonItem) {
	// GET THE DATA
	String backgroundColor = jsonItem.getString("backgroundColor");

        
	// THEN USE THE DATA TO BUILD AN ITEM
        Map newMap = new Map();
        newMap.setBackgroundColor(backgroundColor);
        System.out.println(backgroundColor);
        
	// ALL DONE, RETURN IT
	return newMap;
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    public int getDataAsInt(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber)value;
        return number.bigIntegerValue().intValue();
    }
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        DataManager dataManager = (DataManager) data;
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ArrayList<Map> maps = dataManager.getMap();
        maps.stream().map((map) -> Json.createObjectBuilder()
                .add("backgroundColor", map.getBackgroundColor())).forEach((itemJson) -> {
                    arrayBuilder.add(itemJson);
        });
        JsonArray itemsArray = arrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add("Items", itemsArray)
		.build();
        
     // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	java.util.Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    

        
     

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {

    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {

    }


}
