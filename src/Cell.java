
public class Cell {
	boolean wall;
	boolean visited;
	int r;
	int c;
	
	public Cell(int r, int c)
	{
		wall = false;
		visited = false;
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
	public boolean isVisited()
	{
		return visited;
	}
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}
	
	public int getR()
	{
		return r;
	}
	
	public int getC()
	{
		return c;
	}
	public void setR(int r)
	{
		this.r = r;
	}
	
	public void setC(int c)
	{
		this.c = c;
	}
	
	public String toString()
	{
		String string = new String();
		if(this.isWall())
			string += "#";
		else
			string += " ";
		return string;
	}

}
