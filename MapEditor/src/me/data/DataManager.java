package me.data;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import saf.components.AppDataComponent;
import me.MapEditorApp;

/**
 *
 * @author Tyler Maciaszek
 */
public class DataManager implements AppDataComponent {
   MapEditorApp app;
   ObservableList data; 
   Map map;
   ArrayList<Map> mapData;
   ArrayList<ArrayList<Double>> subregionCordsX;
   ArrayList<ArrayList<Double>> subregionCordsY;
   ArrayList<Subregion> subregionList;
   ArrayList<ImageView> imageList;
   double mapWidth;
   double mapHeight;
   String mapName;
   String mapParentDirectory;
   String rawMapData;
   String workDir;
   String expDir;
   boolean hasCapitals;
   boolean hasLeaders;
   boolean hasFlags;
   double maxX = 0;
   double smallestX = 10000000;
   double maxY = 0;
   double smallestY = 10000000;
   

    
    public DataManager(MapEditorApp initApp) {
        app = initApp;
        data = FXCollections.observableArrayList();
        map = new Map();
        mapData = new ArrayList<>();
        this.subregionCordsX = new ArrayList<>();
        this.subregionCordsY = new ArrayList<>();
        imageList = new ArrayList<>();
        mapWidth = 802;
        mapHeight = 536;
        mapName = "";
        mapParentDirectory = "";
        rawMapData = "";
        workDir = "";
        expDir = "";
        subregionList = new ArrayList();
        hasCapitals = true;
        hasLeaders = true;
        hasFlags = true;
    }

    public String getExpDir() {
        return expDir;
    }

    public void setExpDir(String expDir) {
        this.expDir = expDir;
    }

    
    
    public String getWorkDir() {
        return workDir;
    }

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    
    
    public ArrayList<ImageView> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<ImageView> imageList) {
        this.imageList = imageList;
    }
    
    

    public double getMaxX() {
        return maxX;
    }

    public double getSmallestX() {
        return smallestX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getSmallestY() {
        return smallestY;
    }
    
    
    
    public void setMap(Map mapIn){
        map = mapIn;
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
    
    public Map getMap(){
        return map;
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
                if(screenCord > maxX){
                    maxX = screenCord;
                }
                if(screenCord < smallestX){
                    smallestX = screenCord;
                }
            }
        }
        for (int i = 0; i < subregionCordsY.size(); i++) {
            for (int k = 0; k < subregionCordsY.get(i).size(); k++) {
                double temp = subregionCordsY.get(i).get(k); 
                double screenCord =(((temp * -1) + 90) * (mapHeight /180));
                subregionCordsY.get(i).set(k, screenCord);
                if(screenCord > maxY){
                    maxY = screenCord;
                }
                if(screenCord < smallestY){
                    smallestY = screenCord;
                }
            }
        }
        System.out.println(maxX + " " + maxY);

    }
 
    @Override
    public void reset() {
        subregionList.clear();
        hasCapitals = true;
        hasLeaders = true;
        hasFlags = true;
      
    }

    
}
