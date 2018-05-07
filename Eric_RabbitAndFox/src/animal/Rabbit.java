package animal;

import java.awt.Graphics;
import java.awt.Color;
import field.Cell;

public class Rabbit extends Animal implements Cell{
	public Rabbit() 
	{
		super(10, 2);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int size) {
		int alpha = (int)((1-this.agePercentage())*255);
		g.setColor(new Color(255, 0, 0, alpha));
		g.fillRect(x, y, size, size);
	}

	@Override
	public Animal breed() {
		Animal baby = null;
		if( this.isBreedable() && Math.random()<0.12 )
			baby = new Rabbit();
		return baby;
	}

	@Override
	public String toString() 
	{
		String ret = "A rabbit, age: " + this.getAge();
		return ret;
	}	
}
