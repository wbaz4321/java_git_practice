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
	
	public void moveAnimal(int row, int col, Location loc) { // �������������ANIMAL�ƶ�֮��,��field��ı�ANIMAL��λ��	
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
				if( r>-1 && r<this.height && c>-1 && c<this.width && !(r==row && c==col) ) // ͬһ�����п������������г�Ա����������Ҫͨ������ķ���
				{
					templist.add(field[r][c]);
				}
			}
		}
		// ע�⣬�����������ֻ��ȥ����ָ�����괦CELL���ھӣ�����Ԫ���Զ���������������ÿ��CELL�������������ھӣ�����templist�������ǿյ�
		// ���ǣ�templistҲ����ֻ������nullֵ����Ϊ�������CELL������ANIMAL����Χ�ĸ����ﶼû����������
		return templist.toArray(new Cell[templist.size()]);
	}
	
	public Location[] getFreeNeighbors(int row, int col) 
	{
		// ��������� getNeighbors()���������ڣ�����Ҫ�ҵ�ָ������CELL��Χ�յĸ��ӣ���û�з��κ�ANIMAL�ĸ��ӣ�����ֻ�Ǽ�¼��Щ���Ӷ�Ӧ
		// ��ǰ���CELL��LOCATION
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
		// �������ר������Ϊ�³�����ANIMAL�ڸ�ĸ��Χ�� free neighbors ����һ��λ�ô��
		boolean ret = false;
		Location[] empty_locs = getFreeNeighbors(row, col);
		if(empty_locs.length>0) //˵����Χ�п�λ���Է���BABY
		{
			int index = (int)(Math.random()*empty_locs.length);
			field[empty_locs[index].getRow()][empty_locs[index].getCol()] = o;
			ret = true;
		}
		return ret;
	}
}
