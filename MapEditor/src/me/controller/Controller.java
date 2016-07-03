/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.controller;

import javafx.scene.control.TextInputDialog;
import me.PropertyType;
import properties_manager.PropertiesManager;

/**
 *
 * @author Tyler
 */
public class Controller {
    
    public void launchChangeNameWindow(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TextInputDialog dialog = new TextInputDialog("Current Map Name");
        dialog.setTitle(props.getProperty(PropertyType.CHANGE_MAP_NAME));
        dialog.setHeaderText(props.getProperty(PropertyType.CHANGE_MAP_NAME_HEADER));
        dialog.setContentText(props.getProperty(PropertyType.CHANGE_MAP_NAME_CONTEXT));
        dialog.showAndWait();
    }
}
