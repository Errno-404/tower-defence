package agh.ics.oop.gui;

import agh.ics.oop.Constants;
import agh.ics.oop.Vector;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class CanvasElement {
    public int xIndex;
    public int yIndex;

    public Vector boxCentre;


    private final ImageView originalImage;
    private ImageView normalImg;
    private ImageView cursorImg;

    ColorAdjust cursorBrightener;

    private ImageView img; //image that is used to draw


    public CanvasElement(Image img, Image cursorImg,int posx, int posy){
        this.originalImage = new ImageView(img);
        this.normalImg = new ImageView(img);
        this.cursorImg = new ImageView(cursorImg);

        this.cursorBrightener = new ColorAdjust();
        this.cursorBrightener.setBrightness(0.5);
        this.cursorBrightener.setContrast(0.9);


        this.img = new ImageView(img);



        this.xIndex = posx;
        this.yIndex = posy;

        this.boxCentre = new Vector(this.xIndex* Constants.boxWidth + Constants.boxWidth/2, this.yIndex*Constants.boxHeight + Constants.boxWidth/2);

    }

    public void draw(GraphicsContext gc){
        gc.drawImage(this.img.getImage(), this.xIndex*Constants.boxWidth, this.yIndex*Constants.boxHeight);
    }

    public void highlight(){
        this.img = this.cursorImg;
        //System.out.println(this.cursorBrightener.getBrightness());
        //this.img.setEffect(this.cursorBrightener);
    }

    public void revert(){
        this.img = normalImg;
        //this.img.setEffect(null);
    }

    public void updateImage(ImageView iv){
        this.normalImg = iv;
        this.img = iv;
    }

    public void setOriginalView(){
        this.normalImg = this.originalImage;
        this.img = this.originalImage;
    }
}
