package me.data;

import saf.components.AppDataComponent;
import me.MapEditorApp;

/**
 *
 * @author Tyler Maciaszek
 */
public class DataManager implements AppDataComponent {
    MapEditorApp app;
    
    public DataManager(MapEditorApp initApp) {
        app = initApp;
    }
    
    @Override
    public void reset() {
        
    }
}
