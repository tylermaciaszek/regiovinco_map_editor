/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.PropertyType;
import properties_manager.PropertiesManager;

/**
 *
 * @author Tyler
 */
public class SubRegionDialog {
     Stage newSubStage;

    public SubRegionDialog() {
        newSubStage = new Stage();
        newSubStage.setScene(layoutGUI());
    }
    
    private Scene layoutGUI(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Scene newSubScene;
        VBox subRegionHolder = new VBox();
        HBox subRegionHolderTop = new HBox();
        Button prevButton = new Button();
        Image prevButtonImage = new Image(props.getProperty(PropertyType.PREV_ICON));
        prevButton.setGraphic(new ImageView(prevButtonImage));
        Button nextButton = new Button();
        Image nextButtonImage = new Image(props.getProperty(PropertyType.NEXT_ICON));
        nextButton.setGraphic(new ImageView(nextButtonImage));
        Label subRegionName = new Label("SAMPLE SUBREGION");
        subRegionHolderTop.getChildren().addAll(prevButton, subRegionName, nextButton);
        ImageView subregionImage = new ImageView(new Image(props.getProperty(PropertyType.SAMPLE_SUB)));
        ImageView subregionFlagImage = new ImageView(new Image(props.getProperty(PropertyType.SAMPLE_SUB_FLAG)));
        ImageView subregionLeaderImage = new ImageView(new Image(props.getProperty(PropertyType.SAMPLE_SUB_LEADER)));
        HBox subregionImages = new HBox();
        subregionImages.getChildren().addAll(subregionFlagImage, subregionLeaderImage);
        subregionImages.setSpacing(40);
        subregionImages.setAlignment(Pos.CENTER);
        GridPane infoHolder = new GridPane();
        Label nameLabel = new Label("Name: ");
        Label capitalLabel = new Label("Capital: ");
        Label leaderLabel = new Label("Leader: ");
        TextField nameField = new TextField();
        TextField capitalField = new TextField();
        TextField leaderField = new TextField(); 
        infoHolder.add(nameLabel, 0, 0);
        infoHolder.add(capitalLabel, 0, 1);
        infoHolder.add(leaderLabel, 0, 2);
        infoHolder.add(nameField, 1, 0);
        infoHolder.add(capitalField, 1, 1);
        infoHolder.add(leaderField, 1, 2);
        infoHolder.setAlignment(Pos.CENTER);
        Button okButton = new Button("OK");
        subRegionHolderTop.setSpacing(45);
        subRegionHolderTop.setAlignment(Pos.CENTER);
        subRegionHolder.getChildren().addAll(subRegionHolderTop, subregionImage, subregionImages, infoHolder, okButton);
        subRegionHolder.setAlignment(Pos.TOP_CENTER);
        subRegionHolder.setSpacing(20);
        
        
        
        
        newSubScene = new Scene(subRegionHolder, 280,550);
        return newSubScene;
    }
    
    public void showDialog(){
        newSubStage.show();
        
    }
}
