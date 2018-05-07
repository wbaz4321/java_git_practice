package field;

import field.Cell;
import java.util.ArrayList;

public class Field {
	
	private int width;
	private int height;
	private Cell[][] field;
	private static final Location[] adjacent = {new Location(-1,-1), new Location(-1,0), new Location(-1,1),
									 new Location(0,-1), new Location(0,0), new Location(0,1),
									 new Location(1,-1), new Location(1,0), new Location(1,1)};
	
	public Field(int width, int height) {
		this.width = width;
		this.height = height;
		field = new Cell[width][height];
	}	
	
	public int getWidth(){ return this.width; }
	public int getHeight() {return this.height; }
	
	public Cell getCell(int row, int col) {
		Cell ret = null;
		ret = field[row][col];
		return ret;
	}
	
	public void placeAnimal(int row, int col, Cell o) {
		field[row][col] = o;
//		Cell ret = field[row][col];
//		field[row][col] = o;
//		return ret;
	}
	
	public void removeAnimal(int row, int col) {
		field[row][col] = null;
	}
	
	public void removeAnimalByCell(Cell c) {
		for(int row=0; row<height; row++) 
		{
			for(int col=0; col<width; col++) 
			{
				if(field[row][col] == c) 
				{
					field[row][col] = null;
					break;
				}
			}
		}
	}
	
	public void moveAnimal(int row, int col, Location loc) { // 这个方法用来在ANIMAL移动之后,在field里改变ANIMAL的位置	
		field[loc.getRow()][loc.getCol()] = field[row][col];
		removeAnimal(row, col);
	}
	
	public Cell[] getNeighbors(int row, int col) {
		ArrayList<Cell> templist = new ArrayList<Cell>();
		for(int i=-1; i<2; i++) 
		{
			for(int j=-1; j<2; j++) 
			{
				int r=row+i;
				int c=col+j;
				if( r>-1 && r<this.height && c>-1 && c<this.width && !(r==row && c==col) ) // 同一个类中可以随便调用所有成员变量，不需要通过额外的方法
				{
					templist.add(field[r][c]);
				}
			}
		}
		// 注意，这个方法我们只是去返回指定坐标处CELL的邻居，按照元胞自动机的排列特征，每个CELL都有至少三个邻居，所以templist不可能是空的
		// 但是，templist也可能只是容纳null值，因为可能这个CELL（或者ANIMAL）周围的格子里都没有其他动物
		return templist.toArray(new Cell[templist.size()]);
	}
	
	public Location[] getFreeNeighbors(int row, int col) 
	{
		// 这个方法和 getNeighbors()的区别在于，我们要找到指定坐标CELL周围空的格子，即没有放任何ANIMAL的格子，我们只是记录这些格子对应
		// 当前这个CELL的LOCATION
		ArrayList<Location> list = new ArrayList<Location>();
		for(Location loc : adjacent) 
		{
			int r = row+loc.getRow();
			int c = col+loc.getCol();
			if( r>-1 && r<height && c>-1 && c<width && field[r][c]==null ) // field[r][c]==null means this location is empty
			{
				list.add(new Location(r, c));
			}
		}	
		return list.toArray(new Location[list.size()]);
	}
	
	public boolean placeNewAnimal(int row, int col, Cell o) {
		// 这个方法专门用来为新出生的ANIMAL在父母周围的 free neighbors 中找一个位置存放
		boolean ret = false;
		Location[] empty_locs = getFreeNeighbors(row, col);
		if(empty_locs.length>0) //说明周围有空位可以放新BABY
		{
			int index = (int)(Math.random()*empty_locs.length);
			field[empty_locs[index].getRow()][empty_locs[index].getCol()] = o;
			ret = true;
		}
		return ret;
	}
}
