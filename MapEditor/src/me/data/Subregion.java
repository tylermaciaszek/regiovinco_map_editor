/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.data;

import javafx.scene.shape.Polygon;

/**
 *
 * @author Tyler
 */
public class Subregion {
    Polygon polygon;
    String name;
    String leader;
    String capital;
    int redColor;
    int blueColor;
    int greenColor;

    
    public Subregion(Polygon polygonIn){
        polygon = polygonIn;
        redColor = 0;
        blueColor = 0;
        greenColor = 0;
        name = "";
        leader = "";
        capital = "";
    }
    
    public Subregion(String nameIn, String leaderIn, String capitalIn){
        name = nameIn;
        leader = leaderIn;
        capital = capitalIn;
        redColor = 0;
        blueColor = 0;
        greenColor = 0;
    }


    @Override
    public String toString() {
        return "Subregion{" + "name=" + name + ", leader=" + leader + ", capital=" + capital + '}';
    }

    public int getRedColor() {
        return redColor;
    }

    public void setRedColor(int redColor) {
        this.redColor = redColor;
    }

    public int getBlueColor() {
        return blueColor;
    }

    public void setBlueColor(int blueColor) {
        this.blueColor = blueColor;
    }

    public int getGreenColor() {
        return greenColor;
    }

    public void setGreenColor(int greenColor) {
        this.greenColor = greenColor;
    }

    public String getName() {
        return name;
    }

    public String getLeader() {
        return leader;
    }

    public String getCapital() {
        return capital;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
    
    
    
    
    
}
