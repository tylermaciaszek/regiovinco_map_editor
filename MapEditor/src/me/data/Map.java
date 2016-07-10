/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.data;

import java.util.ArrayList;

/**
 *
 * @author Tyler
 */
public class Map {
    ArrayList<Subregion> subregionsList;
    ArrayList<String> imagePaths;
    ArrayList<Integer> imageLocationsX;
    ArrayList<Integer> imageLocationsY;
    String backgroundColor;
    String borderColor;
    Double borderThickness;

    public Map() {
        subregionsList = new ArrayList<>();
        imagePaths = new ArrayList<>();
        imageLocationsX = new ArrayList<>();
        imageLocationsY = new ArrayList<>();
        backgroundColor = "";
        borderColor = "";
        borderThickness = 1.0;
    }

    public ArrayList<Subregion> getSubregionsList() {
        return subregionsList;
    }

    public void setSubregionsList(ArrayList<Subregion> subregionsList) {
        this.subregionsList = subregionsList;
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public ArrayList<Integer> getImageLocationsX() {
        return imageLocationsX;
    }

    public void setImageLocationsX(ArrayList<Integer> imageLocationsX) {
        this.imageLocationsX = imageLocationsX;
    }

    public ArrayList<Integer> getImageLocationsY() {
        return imageLocationsY;
    }

    public void setImageLocationsY(ArrayList<Integer> imageLocationsY) {
        this.imageLocationsY = imageLocationsY;
    }

    

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public Double getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(Double borderThickness) {
        this.borderThickness = borderThickness;
    }
    
    
    
}
