
public class Cell {
	boolean wall;
	int r;
	int c;
	
	public Cell(int r, int c)
	{
		wall = false;
		this.r = r;
		this.c = c;
	}
	
	public boolean isWall()
	{
		return this.wall;
	}
	
	public void setWall(boolean wall)
	{
		this.wall = wall;
	}
	
	public String toString()
	{
		String string = new String();
		if(this.isWall())
			string += ((char) 219);
		else
			string += " ";
		return string;
	}

}
