/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.data;

import javafx.scene.image.ImageView;

/**
 *
 * @author Tyler
 */
public class ImageData {
    ImageView image;
    double x;
    double y;
    String imagePath;

    public ImageData(ImageView iamge, double x, double y, String imagePath) {
        this.image = iamge;
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;
    }

    public ImageData() {
        image = new ImageView();
        x = 0;
        y = 0;
        imagePath = "";
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
    
}
