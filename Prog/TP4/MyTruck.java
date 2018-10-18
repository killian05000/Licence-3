import javax.swing.*;
import java.awt.*;

public class MyTruck extends JPanel
{
  public MyTruck()
  {
    setPreferredSize(new Dimension(130, 110));
  }

  private void paintTruck(Painter painter)
  {
    painter.drawRectangle(10,10,70,70);
    painter.drawRectangle(80,45,40,35);  
    painter.drawCircle(40,80,10);
    painter.drawCircle(100,80,10);
  }

  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    //Painter painter = new PainterFromMarseille(g);
    Painter painter = new EnglishPainterAdapter(g);
    paintTruck(painter);
  }
}
