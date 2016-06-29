/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import javafx.scene.layout.Pane;
import saf.components.AppWorkspaceComponent;
import me.MapViewerApp;

/**
 *
 * @author Tyler Maciaszek
 */
public class Workspace extends AppWorkspaceComponent {
    MapViewerApp app;
    
    public Workspace(MapViewerApp initApp) {
        app = initApp;
        workspace = new Pane();
    }
    
    public void layoutGUI(){
        
    }

    @Override
    public void reloadWorkspace() {
        
    }

    @Override
    public void initStyle() {
        
    }
}
