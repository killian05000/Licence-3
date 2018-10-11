import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class EnglishPainterAdapter extends JPanel implements Painter
{

	public EnglishPainter englishPainter;
	private Graphics g;

	public EnglishPainterAdapter(Graphics g)
	{
		  englishPainter = new EnglishPainter(g);
	}

	@Override
	public void drawRectangle(int x, int y, int w, int h)
	{
		Point p1 = new Point(x,y);
		Point p2 = new Point(x+w, y+h);
		englishPainter.drawRectangle(p1, p2);
	}

	@Override
	public void drawCircle(int xCenter, int yCenter, int radius)
	{
		Point center = new Point(xCenter, yCenter);
		englishPainter.drawCircle(center, radius);
	}

}
