/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import me.PropertyType;
import me.data.Subregion;
import properties_manager.PropertiesManager;

/**
 *
 * @author Tyler
 */
public class SubRegionDialog {
     Stage newSubStage;
     String regionName;
     Polygon regionPolygon;
    StackPane polyHolder;
    Subregion subregionToEdit;
    Button okButton;
    TextField nameField = new TextField();
    TextField capitalField = new TextField();
    TextField leaderField = new TextField();
    String leaderImage;
    String flagImage;
    ImageView subregionFlagImage;
    ImageView subregionLeaderImage;

    public String getLeaderImage() {
        return leaderImage;
    }

    public void setLeaderImage(String leaderImage) {
        this.leaderImage = leaderImage;
    }

    public String getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(String flagImage) {
        this.flagImage = flagImage;
    }
    
    public Subregion getSubregionToEdit() {
        return subregionToEdit;
    }

    public void setSubregionToEdit(Subregion subregionToEdit) {
        this.subregionToEdit = subregionToEdit;
    }

    public Polygon getRegionPolygon() {
        return regionPolygon;
    }

    public void setRegionPolygon(Polygon regionPolygon) {
        this.regionPolygon = regionPolygon;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public SubRegionDialog() {
        newSubStage = new Stage();
    }
    
    private Scene layoutGUI(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Scene newSubScene;
        nameField.setText(regionName);
        leaderField.setText(subregionToEdit.getLeader());
        capitalField.setText(subregionToEdit.getCapital());
        VBox subRegionHolder = new VBox();
        HBox subRegionHolderTop = new HBox();
        Button prevButton = new Button();
        Image prevButtonImage = new Image(props.getProperty(PropertyType.PREV_ICON));
        prevButton.setGraphic(new ImageView(prevButtonImage));
        Button nextButton = new Button();
        Image nextButtonImage = new Image(props.getProperty(PropertyType.NEXT_ICON));
        nextButton.setGraphic(new ImageView(nextButtonImage));
        Label subRegionName = new Label(regionName);
        subRegionHolderTop.getChildren().addAll(prevButton, subRegionName, nextButton);
        subRegionHolderTop.setPadding(new Insets(10, 5, 0, 5));
        polyHolder = new StackPane();
        polyHolder.setMinSize(60, 120);
        polyHolder.getChildren().add(regionPolygon);
        polyHolder.setScaleX(30);
        polyHolder.setScaleY(30);
        if (flagImage != null) {
            File file = new File(flagImage);
            System.out.print(file.toURI().toString());
            subregionFlagImage = new ImageView(new Image(file.toURI().toString()));
            subregionFlagImage.setFitHeight(100);
            subregionFlagImage.setFitWidth(100);
        } else {
            subregionFlagImage = new ImageView();
        }
        if (leaderImage != null) {
            File file = new File(leaderImage);
            System.out.print(file.toURI().toString());
            subregionLeaderImage = new ImageView(new Image(file.toURI().toString()));
            subregionLeaderImage.setFitHeight(100);
            subregionLeaderImage.setFitWidth(100);
           
        } else {
            subregionLeaderImage = new ImageView();
        }
        HBox subregionImages = new HBox();
        subregionImages.getChildren().addAll(subregionFlagImage, subregionLeaderImage);
        subregionImages.setSpacing(40);
        subregionImages.setAlignment(Pos.CENTER);
        GridPane infoHolder = new GridPane();
        Label nameLabel = new Label("Name: ");
        Label capitalLabel = new Label("Capital: ");
        Label leaderLabel = new Label("Leader: ");
        infoHolder.add(nameLabel, 0, 0);
        infoHolder.add(capitalLabel, 0, 1);
        infoHolder.add(leaderLabel, 0, 2);
        infoHolder.add(nameField, 1, 0);
        infoHolder.add(capitalField, 1, 1);
        infoHolder.add(leaderField, 1, 2);
        infoHolder.setAlignment(Pos.CENTER);
        infoHolder.setHgap(20);
        infoHolder.setVgap(5);
        okButton = new Button("OK");
        subRegionHolderTop.setSpacing(43);
        subRegionHolderTop.setAlignment(Pos.CENTER);
        subRegionHolder.getChildren().addAll(subRegionHolderTop, polyHolder, subregionImages, infoHolder, okButton);
        subRegionHolder.setAlignment(Pos.TOP_CENTER);
        subRegionHolder.setSpacing(20);
        
        
        
        
        newSubScene = new Scene(subRegionHolder, 290,565);
        return newSubScene;
    }
    
    public void initHandlers(){
        okButton.setOnAction(e ->{
            subregionToEdit.setName(nameField.getText());
            subregionToEdit.setCapital(capitalField.getText());
            subregionToEdit.setLeader(leaderField.getText());
            subregionToEdit.getPolygon().setFill(Color.rgb(subregionToEdit.getRedColor(), subregionToEdit.getGreenColor(), subregionToEdit.getBlueColor()));
            newSubStage.close();
        });
    }
    
    public void showDialog(){
        newSubStage.setScene(layoutGUI());
        initHandlers();
        newSubStage.showAndWait();
        
    }
}
