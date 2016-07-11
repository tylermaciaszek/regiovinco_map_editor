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
import saf.AppTemplate;
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
        Map map;
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// AND NOW LOAD ALL THE ITEMS
        JsonArray editInfoLoad = json.getJsonArray("Edit Info");
	for (int i = 0; i < editInfoLoad.size(); i++) {
	    JsonObject jsonItem = editInfoLoad.getJsonObject(i);
	    map = loadEditInfo(jsonItem);
	    dataManager.addMap(map);
	}
        
        JsonArray mapInfoLoad = json.getJsonArray("map");
	for (int i = 0; i < mapInfoLoad.size(); i++) {
	    JsonObject jsonItem = mapInfoLoad.getJsonObject(i);
            loadMapInfo(jsonItem, dataManager);
	}
        
        JsonArray imageInfoLoad = json.getJsonArray("Images");
        for (int i = 0; i < imageInfoLoad.size(); i++) {
	    JsonObject jsonItem = imageInfoLoad.getJsonObject(i);
            loadImageInfo(jsonItem, dataManager);
	}
    }
    
     public Map loadEditInfo(JsonObject jsonItem) {
	// GET THE DATA
	String backgroundColor = jsonItem.getString("backgroundColor");
        //Double borderThickness = Double.parseDouble(jsonItem.getString("borderThickness"));
        String borderColor = jsonItem.getString("borderColor");
        

        
	// THEN USE THE DATA TO BUILD AN ITEM
        Map newMap = new Map();
        newMap.setBackgroundColor(backgroundColor);
        //newMap.setBorderThickness(Double.parseDouble(borderThickness));
        newMap.setBorderColor(borderColor);
        
	// ALL DONE, RETURN IT
	return newMap;
    }
     
     public void loadMapInfo(JsonObject jsonItem, DataManager dataManager) {
         System.out.print(dataManager);
         dataManager.setMapName(jsonItem.getString("mapName"));
         dataManager.setMapParentDirectory(jsonItem.getString("mapParentDirectory"));
         dataManager.setRawMapData(jsonItem.getString("mapRawData"));
         dataManager.setMapHeight(536);
         dataManager.setMapWidth(802); 
     }
     
     public void loadImageInfo(JsonObject jsonItem, DataManager dataManager) {
         Map map = dataManager.getMap().get(0);
         map.getImagePaths().add(jsonItem.getString("imagePath"));
         map.getImageLocationsX().add(jsonItem.getInt("X"));
         map.getImageLocationsY().add(jsonItem.getInt("Y"));
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
        JsonArrayBuilder editInfoArray = Json.createArrayBuilder();
        JsonArrayBuilder imageArray = Json.createArrayBuilder();
        JsonArrayBuilder mapArray = Json.createArrayBuilder();
        Map map = dataManager.getMap().get(0);
            JsonObject mapJson = Json.createObjectBuilder()
                    .add("mapName", dataManager.getMapName())
                    .add("mapParentDirectory", dataManager.getMapParentDirectory())
                    .add("mapRawData", dataManager.getRawMapData())
                    .add("mapHeight", dataManager.getMapHeight())
                    .add("mapWidth", dataManager.getMapWidth()).build();
            mapArray.add(mapJson);
        
            
	    JsonObject itemJson = Json.createObjectBuilder()
		    .add("backgroundColor", map.getBackgroundColor())
		    .add("borderThickness", map.getBorderThickness())
                    .add("borderColor", map.getBorderColor()).build();
                                editInfoArray.add(itemJson);

            
          
            for (int i = 0; i < map.getImagePaths().size(); i++) {
                JsonObject imageJson = Json.createObjectBuilder()
                        .add("imagePath", map.getImagePaths().get(i))
                        .add("X", map.getImageLocationsX().get(i))
                        .add("Y", map.getImageLocationsY().get(i)).build();
                imageArray.add(imageJson);
                           
        }	

        JsonArray editInfo = editInfoArray.build();   
        JsonArray imageInfo = imageArray.build();
        JsonArray mapInfo = mapArray.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add("map", mapInfo)
                .add("Edit Info", editInfo)
                .add("Images", imageInfo)
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
     public void loadCoords(AppDataComponent data, String filePath) throws IOException {
        DataManager dataManager = (DataManager)data;
        dataManager.reset();
        
        //Load JSON File
        JsonObject json = loadJSONFile(filePath);
        JsonArray jsonSubArray = null;
        ArrayList<Object> subregionCordsX = new ArrayList<>();
        ArrayList<Object> subregionCordsY = new ArrayList<>();
        JsonArray coordinateArray = null;

        // AND NOW LOAD ALL THE ITEMS
        JsonArray jsonItemArray = json.getJsonArray("SUBREGIONS");
        for (int i = 0; i < jsonItemArray.size(); i++) {
            json = jsonItemArray.getJsonObject(i);
            jsonSubArray = json.getJsonArray("SUBREGION_POLYGONS");
            if (jsonSubArray.size() > 0) {
                for (int j = 0; j < jsonSubArray.size(); j++) {
                    coordinateArray = jsonSubArray.getJsonArray(j);
                    ArrayList doubleCordsX = new ArrayList();
                    ArrayList doubleCordsY = new ArrayList();
                    for (int k = 0; k < coordinateArray.size(); k++) {
                        JsonObject coordinate = coordinateArray.getJsonObject(k);
                        double xCord = getDataAsDouble(coordinate, "X");
                        doubleCordsX.add(xCord);
                        double yCord = getDataAsDouble(coordinate, "Y");
                        doubleCordsY.add(yCord);
                        if (k == coordinateArray.size() - 1) {
                            dataManager.addXCords(doubleCordsX);
                            dataManager.addYCords(doubleCordsY);
                        }
                    }
                }
            }
        }
    } 

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        

    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {

    }


}
