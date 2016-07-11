package me.data;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import saf.components.AppDataComponent;
import me.MapEditorApp;

/**
 *
 * @author Tyler Maciaszek
 */
public class DataManager implements AppDataComponent {
   MapEditorApp app;
   ObservableList data; 
   ArrayList<Map> mapData;
   ArrayList<ArrayList<Double>> subregionCordsX;
   ArrayList<ArrayList<Double>> subregionCordsY;
   ArrayList<Subregion> subregionList;
   double mapWidth;
   double mapHeight;
   String mapName;
   String mapParentDirectory;
   String rawMapData;
   boolean hasCapitals;
   boolean hasLeaders;
   boolean hasFlags;

    
    public DataManager(MapEditorApp initApp) {
        app = initApp;
        data = FXCollections.observableArrayList();
        mapData = new ArrayList<>();
        this.subregionCordsX = new ArrayList<>();
        this.subregionCordsY = new ArrayList<>();
        mapWidth = 0.0;
        mapHeight = 0.0;
        mapName = "";
        mapParentDirectory = "";
        rawMapData = "";
        subregionList = new ArrayList();
        hasCapitals = true;
        hasLeaders = true;
        hasFlags = true;
    }

    public boolean isHasCapitals() {
        return hasCapitals;
    }

    public void setHasCapitals(boolean hasCapitals) {
        this.hasCapitals = hasCapitals;
    }

    public boolean isHasLeaders() {
        return hasLeaders;
    }

    public void setHasLeaders(boolean hasLeaders) {
        this.hasLeaders = hasLeaders;
    }

    public boolean isHasFlags() {
        return hasFlags;
    }

    public void setHasFlags(boolean hasFlags) {
        this.hasFlags = hasFlags;
    }
    
    

    public ArrayList<Subregion> getSubregionList() {
        return subregionList;
    }

    public void setSubregionList(ArrayList<Subregion> subregionList) {
        this.subregionList = subregionList;
    }
    
    public String getRawMapData() {
        return rawMapData;
    }

    public void setRawMapData(String rawMapData) {
        this.rawMapData = rawMapData;
    }
    
    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapParentDirectory() {
        return mapParentDirectory;
    }

    public void setMapParentDirectory(String mapParentDirectory) {
        this.mapParentDirectory = mapParentDirectory;
    }
    
    public double getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(double mapWidth) {
        this.mapWidth = mapWidth;
    }

    public double getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(double mapHeight) {
        this.mapHeight = mapHeight;
    }
    
    
    public void addItem(Object item){
        data.add(item);
    }
    
    public void addMap(Map item){
        mapData.add(item);
    }
    
    public ArrayList<Map> getMap(){
        return mapData;
    }
    public ObservableList getData(){
        return data;  
    }
    
    public void addXCords(ArrayList data){
        subregionCordsX.add(data);
    }
    
    public void addYCords(ArrayList data){
        subregionCordsY.add(data);
    }

    public ArrayList<ArrayList<Double>> getSubregionCordsX() {
        return subregionCordsX;
    }

    public ArrayList<ArrayList<Double>> getSubregionCordsY() {
        return subregionCordsY;
    }
    
    public void convertToScreen() {
        for (int i = 0; i < subregionCordsX.size(); i++) {
            for (int k = 0; k < subregionCordsX.get(i).size(); k++) {
                double temp = subregionCordsX.get(i).get(k);
                double screenCord = ((temp + 180) * (mapWidth / 360));
                subregionCordsX.get(i).set(k, screenCord);
            }
        }
        for (int i = 0; i < subregionCordsY.size(); i++) {
            for (int k = 0; k < subregionCordsY.get(i).size(); k++) {
                double temp = subregionCordsY.get(i).get(k); 
                double screenCord =(((temp * -1) + 90) * (mapHeight /180));
                subregionCordsY.get(i).set(k, screenCord);
            }
        }

    }
 
    @Override
    public void reset() {
        subregionList.clear();
      
    }
}
