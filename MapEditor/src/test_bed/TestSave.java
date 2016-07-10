/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import me.data.Map;


/**
 *
 * @author Tyler
 */
public class TestSave{

    
    public static void main(String[] args){
        generateAndorraMap();
        
        
    }
    
    public static void generateAndorraMap(){
        Map andorraMap = new Map();
        andorraMap.setBackgroundColor("DC6E00");
        andorraMap.setBorderColor("000000");
        andorraMap.setBorderThickness(1.0);
        andorraMap.getImageLocationsX().add(8);
        andorraMap.getImageLocationsY().add(9);
        andorraMap.getImageLocationsX().add(581);
        andorraMap.getImageLocationsY().add(390);
        
    }
}
