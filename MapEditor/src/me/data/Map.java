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
    ArrayList<Subregion> loadSub;
    ArrayList<String> imagePaths;
    ArrayList<Double> imageLocationsX;
    ArrayList<Double> imageLocationsY;
    ArrayList<Integer> colorList;
    String backgroundColor;
    String borderColor;
    double borderThickness;
    double scrollLocationX;
    double scrollLocationY;
    double zoomLevel;
    double initialZoom;

    public Map() {
        colorList = new ArrayList<>();
        loadSub = new ArrayList<>();
        subregionsList = new ArrayList<>();
        imagePaths = new ArrayList<>();
        imageLocationsX = new ArrayList<>();
        imageLocationsY = new ArrayList<>();
        backgroundColor = "";
        borderColor = "000000";
        borderThickness = 1.0;
        scrollLocationX = 0.0;
        scrollLocationY = 0.0;
        zoomLevel = 1.0;
        initialZoom = 1.0;
    }

    public ArrayList<Subregion> getLoadSub() {
        return loadSub;
    }

    public void setLoadSub(ArrayList<Subregion> loadSub) {
        this.loadSub = loadSub;
    }

    public double getInitialZoom() {
        return initialZoom;
    }

    public void setInitialZoom(double initialZoom) {
        this.initialZoom = initialZoom;
    }
    
    

    public ArrayList<Integer> getColorList() {
        return colorList;
    }

    public void setColorList(ArrayList<Integer> colorList) {
        this.colorList = colorList;
    }

    
    public double getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    
    public double getScrollLocationX() {
        return scrollLocationX;
    }

    public void setScrollLocationX(double scrollLocationX) {
        this.scrollLocationX = scrollLocationX;
    }

    public double getScrollLocationY() {
        return scrollLocationY;
    }

    public void setScrollLocationY(double scrollLocationY) {
        this.scrollLocationY = scrollLocationY;
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

    public ArrayList<Double> getImageLocationsX() {
        return imageLocationsX;
    }

    public void setImageLocationsX(ArrayList<Double> imageLocationsX) {
        this.imageLocationsX = imageLocationsX;
    }

    public ArrayList<Double> getImageLocationsY() {
        return imageLocationsY;
    }

    public void setImageLocationsY(ArrayList<Double> imageLocationsY) {
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

    public double getBorderThickness() {
        return (int) borderThickness;
    }
    


    public void setBorderThickness(double borderThickness) {
        this.borderThickness = borderThickness;
    }
    
    
    
}
