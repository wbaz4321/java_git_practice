package rabbitAndFox;

import field.Cell;
import field.Field;
import field.View;
import field.Location;
import animal.Animal;
import animal.Fox;
import animal.Rabbit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

//class StepListener implements ActionListener
//{
//	private RabbitAndFox rb;
//	public StepListener(RabbitAndFox rb) 
//	{
//		this.rb = rb;
//	}
//	
//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		rb.step();
//		rb.repaint();
//	}	
//}

public class RabbitAndFox {

	private Field myfield;
	private View view;
	private JFrame frame;
	
	public void repaint() { frame.repaint(); }
	
	public RabbitAndFox(int field_size) {
		frame = new JFrame();
		myfield = new Field(field_size, field_size);
		for(int row=0; row<myfield.getHeight(); row++) 
		{
			for(int col=0; col<myfield.getWidth(); col++) 
			{
				if(Math.random()<0.05) 
				{
					myfield.placeAnimal(row, col, new Fox());
				}
				else if(Math.random()<0.15) 
				{
					myfield.placeAnimal(row, col, new Rabbit());
				}
			}
		}
		// 注意,这里的逻辑应该是,如果既不是兔子也不是狐狸,那么这个位置上应该是NULL,没有CELL
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		view = new View(myfield);
		frame.setTitle("Rabbits and Fox");
		frame.add(view);
		JButton btnStep = new JButton("单步");
		frame.add(btnStep, BorderLayout.NORTH);
		
		// 接下来设置 button的响应机制		
		btnStep.addActionListener(new ActionListener() { // 这个add其实是一种 “注册”
			@Override  // ActionListener 是一个接口 其中有一个abstract的函数 actionPerformed(ActionEvent arg)
			// 而这里之所以可以用 NEW 关键字去“创建”这个接口，并不是实例化了接口对象，而只是一种简化代码的技巧
			// 即通过用匿名类有尝试创立接口 实际上是创建了一个匿名类 让这个类实现了ActionListener接口 然后把这个类的对象创建出来
			// 注意 这里这个匿名类 其实是一个内部类 和我们之前定义的 StepListener是一个道理
			// 内部类最大的好处是可以方便的使用 大类 中的所有成员变量和成员函数 
			public void actionPerformed(ActionEvent e) {
				System.out.println("按下啦");
				step();
				frame.repaint();
				// 这里可以理解为 既然frame包含了view 那么 frame的repaint()
				// 自然也会调用其中component view的repaint()
			}			
		});
//		btnStep.addActionListener(new StepListener(this));
		frame.pack();
		frame.setVisible(true);
	}
	
	public void start(int steps) { // 这个其实就是循环的去做step()
		for(int i=0; i<steps; i++) 
		{
			step();
			view.repaint(); // 我们会在每一轮的 repaint() 方法中去调用paint()
			try 
			{
				Thread.sleep(200);
			}
			catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void step() {
		for(int row=0; row<myfield.getHeight(); row++) 
		{
			for(int col=0; col<myfield.getWidth(); col++) 
			{
				Cell c = myfield.getCell(row, col);
				if( c!= null) 
				{
					Animal animal = (Animal)c;
					if( animal.isAlive() ) // 死掉的动物就不要再Grew了
					{
						animal.grew();
						if( animal.isAlive() ) 
						{
							// MOVE
							Location loc = animal.move(myfield.getFreeNeighbors(row, col)); // 需要给定可能移动的方位,我们设定只能在周围8个格子里移动里移动,所以我们转而在Field类里去定义 adjacent
								// 注意,上面的代码只是让ANIMAL在逻辑上MOVE了 还没有真正physically在field里去改变当前ANIMAL的位置,要做到这一点,需要在FIELD类去定义移动ANIMAL的方法
							if( loc!=null ) // 观察 Animal类中的源代码 loc可能是null 那么这个动物无法移动 所以这里要加一个 if 否则我们
											// 就可能调用 null 对象的 getRow() getCol() 引发 nullPointException 异常
								myfield.moveAnimal(row, col, loc);
							// BREED
							Animal baby = animal.breed();
							if( baby!=null ) 
							{	// 如果确实有新的ANIMAL出生,我们需要在周围给它找一个地方 把它放到FIELD里
								myfield.placeNewAnimal(row, col, (Cell)baby);
								System.out.println("a new BABY is born: "+ baby.toString());
							}
							// FEED		
							Cell[] neighbor = myfield.getNeighbors(row, col);
							ArrayList<Animal> eatable_rb = new ArrayList<Animal>();
							for(Cell cell : neighbor) 
							{
								if(cell instanceof Rabbit) 
								{
									eatable_rb.add((Rabbit)cell);
								}
							}
							if( !eatable_rb.isEmpty() ) 
							{
								Animal eaten_rb = animal.feed(eatable_rb);
								if( eaten_rb !=null )
									myfield.removeAnimalByCell((Cell)eaten_rb);
							}
						}
						else 
						{
							myfield.removeAnimal(row, col);
						}
					}
					else 
					{
						myfield.removeAnimal(row, col);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		RabbitAndFox rb = new RabbitAndFox(50);
		rb.start(100);
	}

}
