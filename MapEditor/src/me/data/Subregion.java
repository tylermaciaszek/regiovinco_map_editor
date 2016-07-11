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

    
    public Subregion(Polygon polygonIn){
        polygon = polygonIn;
    }
    
    public Subregion(String nameIn, String leaderIn, String capitalIn){
        name = nameIn;
        leader = leaderIn;
        capital = capitalIn;
    }

    @Override
    public String toString() {
        return "Subregion{" + "name=" + name + ", leader=" + leader + ", capital=" + capital + '}';
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
    
    
    
}
