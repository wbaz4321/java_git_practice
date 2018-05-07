package field;

// View是一个容器 我们要在这个容器里面画出网格 它本身是要被加到更大的容器 Frame里面去的
// Frame容器由我们在业务逻辑类 RabbitAndFox里直接定义
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;

import field.Field;

public class View extends JPanel { // JPanel类中有paint()方法
	private Field thefield;
	private static final long serialVersionUID = -2417015700213488315L; 
	private static final int GRID_SIZE = 16;
	
	public View(Field field) {
		thefield = field;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); // 常规操作，调用super.paint(g)
		g.setColor(Color.GRAY);
		int n_row = thefield.getHeight();
		int n_col = thefield.getWidth(); // 返回整个FIELD一共有多少列
		// 画行之间的分割线
		for(int row=0; row<n_row; row++) 
		{
			g.drawLine(0, GRID_SIZE*row, GRID_SIZE*n_col, GRID_SIZE*row);
		}
		// 画行之间的分割线
		for(int col=0; col<n_col; col++) 
		{
			g.drawLine(col*GRID_SIZE, 0, col*GRID_SIZE, GRID_SIZE*n_row);
		}
		// 画出每个CELL
		for(int row=0; row<thefield.getHeight(); row++) 
		{
			for(int col=0; col<thefield.getWidth(); col++) 
			{
				Cell cell = thefield.getCell(row, col);
				if( cell!=null )
					cell.draw(g, GRID_SIZE*col, GRID_SIZE*row, GRID_SIZE);
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(thefield.getWidth()*GRID_SIZE+1, thefield.getHeight()*GRID_SIZE+1);
	}
}
