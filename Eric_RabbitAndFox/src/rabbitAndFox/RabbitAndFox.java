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
		// ע��,������߼�Ӧ����,����Ȳ�������Ҳ���Ǻ���,��ô���λ����Ӧ����NULL,û��CELL
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		view = new View(myfield);
		frame.setTitle("Rabbits and Fox");
		frame.add(view);
		JButton btnStep = new JButton("����");
		frame.add(btnStep, BorderLayout.NORTH);
		
		// ���������� button����Ӧ����		
		btnStep.addActionListener(new ActionListener() { // ���add��ʵ��һ�� ��ע�ᡱ
			@Override  // ActionListener ��һ���ӿ� ������һ��abstract�ĺ��� actionPerformed(ActionEvent arg)
			// ������֮���Կ����� NEW �ؼ���ȥ������������ӿڣ�������ʵ�����˽ӿڶ��󣬶�ֻ��һ�ּ򻯴���ļ���
			// ��ͨ�����������г��Դ����ӿ� ʵ�����Ǵ�����һ�������� �������ʵ����ActionListener�ӿ� Ȼ��������Ķ��󴴽�����
			// ע�� ������������� ��ʵ��һ���ڲ��� ������֮ǰ����� StepListener��һ������
			// �ڲ������ĺô��ǿ��Է����ʹ�� ���� �е����г�Ա�����ͳ�Ա���� 
			public void actionPerformed(ActionEvent e) {
				System.out.println("������");
				step();
				frame.repaint();
				// ����������Ϊ ��Ȼframe������view ��ô frame��repaint()
				// ��ȻҲ���������component view��repaint()
			}			
		});
//		btnStep.addActionListener(new StepListener(this));
		frame.pack();
		frame.setVisible(true);
	}
	
	public void start(int steps) { // �����ʵ����ѭ����ȥ��step()
		for(int i=0; i<steps; i++) 
		{
			step();
			view.repaint(); // ���ǻ���ÿһ�ֵ� repaint() ������ȥ����paint()
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
					if( animal.isAlive() ) // �����Ķ���Ͳ�Ҫ��Grew��
					{
						animal.grew();
						if( animal.isAlive() ) 
						{
							// MOVE
							Location loc = animal.move(myfield.getFreeNeighbors(row, col)); // ��Ҫ���������ƶ��ķ�λ,�����趨ֻ������Χ8���������ƶ����ƶ�,��������ת����Field����ȥ���� adjacent
								// ע��,����Ĵ���ֻ����ANIMAL���߼���MOVE�� ��û������physically��field��ȥ�ı䵱ǰANIMAL��λ��,Ҫ������һ��,��Ҫ��FIELD��ȥ�����ƶ�ANIMAL�ķ���
							if( loc!=null ) // �۲� Animal���е�Դ���� loc������null ��ô��������޷��ƶ� ��������Ҫ��һ�� if ��������
											// �Ϳ��ܵ��� null ����� getRow() getCol() ���� nullPointException �쳣
								myfield.moveAnimal(row, col, loc);
							// BREED
							Animal baby = animal.breed();
							if( baby!=null ) 
							{	// ���ȷʵ���µ�ANIMAL����,������Ҫ����Χ������һ���ط� �����ŵ�FIELD��
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
