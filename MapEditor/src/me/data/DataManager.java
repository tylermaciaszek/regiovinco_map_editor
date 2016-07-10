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

    
    public DataManager(MapEditorApp initApp) {
        app = initApp;
        data = FXCollections.observableArrayList();
        mapData = new ArrayList<>();
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
    
    @Override
    public void reset() {
        
    }
}
