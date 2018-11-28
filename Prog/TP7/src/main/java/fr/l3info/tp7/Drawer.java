package fr.l3info.tp7;

import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;


public class Drawer extends Canvas {
    DrawerContext context = new DrawerContext(this);

    public Drawer(int width, int height) {
        super(width,height);
        setFocusTraversable(true);
        setOnMousePressed(event->context.mousePressed(event));
        setOnMouseReleased(event->context.mouseReleased(event));
        setOnMouseMoved(event->context.mouseMoved(event));
        setOnMouseDragged(event->context.mouseMoved(event));
        setOnKeyPressed(event -> context.keyPressed(event));
    }

    public void repaint(){
        this.getGraphicsContext2D().clearRect(0,0,this.getWidth(),this.getHeight());
    }


}
