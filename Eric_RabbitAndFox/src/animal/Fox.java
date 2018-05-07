package animal;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import field.Cell;

public class Fox extends Animal implements Cell{

	public Fox() {
		super(20, 4);
	}

	@Override
	public void draw(Graphics g, int x, int y, int size) {
		int alpha = Math.max((int)((1-agePercentage())*255), 0);
		g.setColor(new Color(0, 0, 0, alpha));
		//g.drawRect(x, y, size, size);  一会来调试这个,目前看来只需要fill而不需要去画出矩形
		g.fillRect(x, y, size, size);
	}

	@Override
	public Animal breed() {
		Animal baby = null;
		if( this.isBreedable() && Math.random()<0.05 )
			baby = new Fox();
		return baby;
	}
	
	@Override
	public Animal feed(ArrayList<Animal> neighbors) 
	{
		Animal ret = null;
		if( Math.random()<0.2 ) 
		{
			int index = (int)(neighbors.size()*Math.random());
			ret = neighbors.get(index);
			longerLife(2);
		}
		return ret;
	}
	
	@Override
	public String toString() 
	{
		String ret = "A fox, age: " + this.getAge();
		return ret;
	}
}
