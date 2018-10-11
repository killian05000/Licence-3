import javax.swing.*;
import java.awt.*;
import java.util.*;

public class App
{

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(()->run());
  }
  public static void run()
  {
    JFrame window = new JFrame("demo");
    MyTruck truck = new MyTruck();
    window.getContentPane().add(truck);
    window.pack();
    window.setVisible(true);
  }
}
