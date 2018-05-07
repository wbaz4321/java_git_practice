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
	
	// FIELD是不知道ANIMAL的,但是我们却要在FIELD中去移动ANIMAL,移动每次只能在邻居CELL中挑选一个
	// 所以我们需要一个中介的类来指明移动后的位置,因此定义了LOCATION
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
