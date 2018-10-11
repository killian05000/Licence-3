import java.awt.Graphics;

import javax.swing.JPanel;

public class PainterFromMarseille extends JPanel implements Painter
{
  private Graphics g;

	public PainterFromMarseille(Graphics g)
	{
		  this.g = g;
	}

	@Override
	public void drawRectangle(int i, int j, int k, int l)
	{
		g.drawRect(i, j, k, l);
	}

	@Override
	public void drawCircle(int xCenter, int yCenter, int radius)
	{
		g.drawOval(xCenter-radius, yCenter-radius, 2*radius, 2*radius);
	}
}
