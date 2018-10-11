import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class EnglishPainter extends JPanel
{
	private Graphics g;

	public EnglishPainter(Graphics g)
	{
		this.g = g;
	}
	
	public void drawRectangle(Point p1, Point p2)
	{
		int x1 = (int)p1.getX(); int _x2 = (int)p2.getX();
		int y1 = (int)p1.getY(); int _y2 = (int)p2.getY();
		
		int x2 = _x2-x1;
		int y2 = _y2-y1;
		
		g.drawRect(x1, y1, x2, y2);
	}

	public void drawCircle(Point pc, int radius)
	{
		int xCenter = (int)pc.getX();
		int yCenter = (int)pc.getY();
		g.drawOval(xCenter-radius, yCenter-radius, 2*radius, 2*radius);
	}
}
