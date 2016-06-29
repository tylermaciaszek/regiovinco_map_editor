package me.data;

import saf.components.AppDataComponent;
import me.MapViewerApp;

/**
 *
 * @author Tyler Maciaszek
 */
public class DataManager implements AppDataComponent {
    MapViewerApp app;
    
    public DataManager(MapViewerApp initApp) {
        app = initApp;
    }
    
    @Override
    public void reset() {
        
    }
}
