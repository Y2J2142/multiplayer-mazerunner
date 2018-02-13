
public class Player {
	int x;
	int y;
	int id;
	Maze maze;
	String name;
	
	Player(int x, int y, int id, Maze maze)
	{
		this.y = x;
		this.x = y;
		this.id = id;
		this.maze = maze;
		this.name = new String();
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName(){return name;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getID(){return id;}
	public boolean moveLeft()
	{
		if(!maze.maze[x][y-1].isWall())
			y = y - 1;
		if(maze.maze[x][y].isExit())
			return true;
		return false;


	}
	
	public boolean moveRight()
	{
		if(!maze.maze[x][y+1].isWall())
			y = y + 1;
		if(maze.maze[x][y].isExit())
			return true;
		return false;
	}
	
	public boolean moveUp()
	{
		if(!maze.maze[x-1][y].isWall())
			x = x - 1;
		if(maze.maze[x][y].isExit())
			return true;
		return false;
	}
	
	public boolean moveDown()
	{
		if(!maze.maze[x+1][y].isWall())
			x = x + 1;
		if(maze.maze[x][y].isExit())
			return true;
		return false;
	}
}
