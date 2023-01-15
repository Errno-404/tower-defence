package agh.ics.oop.gui;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CanvasElement {

    private final double rectSize = (600/60);
    public double xIndex;
    public double yIndex;

    private ImageView normalImg;
    private ImageView cursorImg;

    ColorAdjust cursorBrightener;

    private ImageView img;


    public CanvasElement(Image img, Image cursorImg,double posx, double posy){
        this.normalImg = new ImageView(img);
        this.cursorImg = new ImageView(cursorImg);

        this.cursorBrightener = new ColorAdjust();
        this.cursorBrightener.setBrightness(0.5);
        this.cursorBrightener.setContrast(0.9);


        this.img = new ImageView(img);
        this.img.setCache(true);
        this.img.setFitWidth(rectSize);
        this.img.setFitHeight(rectSize);



        this.xIndex = posx;
        this.yIndex = posy;

        //this.img.hoverProperty();
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("1234");
            }
        };

        this.img.setOnMouseClicked(handler);

    }

    public void draw(GraphicsContext gc){
        gc.drawImage(this.img.getImage(), this.xIndex*rectSize, this.yIndex*rectSize);
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
}
