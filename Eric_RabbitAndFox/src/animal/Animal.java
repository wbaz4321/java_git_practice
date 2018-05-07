package animal;

import java.util.ArrayList;

import field.Location;

public abstract class Animal {
	private int age;
	private int ageLimit;
	private boolean isAlive = true;
	private int breedAge;
	
	public int getAge() { return this.age; }
	public double agePercentage() { return (double)age/ageLimit; }
	public boolean isAlive() { return this.isAlive; }
	
	public Animal(int ageLimit, int breedAge) {
		this.ageLimit = ageLimit;
		this.breedAge = breedAge;
	}
	
	public void die() { isAlive = false; }
	public void grew() {
		age++;
		if( age>=ageLimit ) 
		{
			die();
		}
	}
	public boolean isBreedable() {
		return age >= breedAge;
	}
	
	// FIELD�ǲ�֪��ANIMAL��,��������ȴҪ��FIELD��ȥ�ƶ�ANIMAL,�ƶ�ÿ��ֻ�����ھ�CELL����ѡһ��
	// ����������Ҫһ���н������ָ���ƶ����λ��,��˶�����LOCATION
	public Location move(Location[] alternatives) {
		Location ret = null;
		if( alternatives.length>0 && Math.random()<0.05 ) 
		{
			ret = alternatives[(int)(Math.random()*alternatives.length)];
//			System.out.println("this animal is moved!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return ret;
	}
	
	public abstract Animal breed();
	
	public Animal feed(ArrayList<Animal> neighbors) 
	{
		return null;
	}
	
	public void longerLife( int amount ) {
		age += amount;
	}
}
