package fr.l3info.tp7;


import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class DrawerContext {

    Drawer drawer;

    public DrawerContext(Drawer drawer) {
        this.drawer = drawer;
    }

    void mousePressed(MouseEvent event){
        double x = event.getX();
        double y = event.getY();

        drawer.getGraphicsContext2D().strokeRect(x,y,2*x,2*y);

    }

    void mouseReleased(MouseEvent event){}

    void mouseMoved(MouseEvent event){}

    void keyPressed(KeyEvent event) {
        switch (event.getText()) {
            case "c":
                drawer.repaint();
        }
    }
}
