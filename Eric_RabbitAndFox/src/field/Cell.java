package field;

import java.awt.Graphics;

public interface Cell{
	
	public abstract void draw(Graphics g, int x, int y, int size);
//	{
//		g.setColor(Color.GRAY);
//		g.drawRect(x, y, size, size);
//		g.fillRect(x, y, size, size);
//	}
}
