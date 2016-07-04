package me.data;

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
   ObservableList testData; 

    
    public DataManager(MapEditorApp initApp) {
        app = initApp;
        testData = FXCollections.observableArrayList();
    }
    
    public void addItem(Object item){
        testData.add(item);
    }
    
    public ObservableList getData(){
        return testData;   
    }
    
    @Override
    public void reset() {
        
    }
}
