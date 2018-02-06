
public class Player {
	int x;
	int y;
	int id;
	Maze maze;
	
	Player(int x, int y, int id, Maze maze)
	{
		this.y = x;
		this.x = y;
		this.id = id;
		this.maze = maze;
	}
	
	
	public void moveLeft()
	{
		if(!maze.maze[x-1][y].isWall())
			x = x - 1;
	}
	
	public void moveRight()
	{
		if(!maze.maze[x+1][y].isWall())
			x = x + 1;
	}
	
	public void moveUp()
	{
		if(!maze.maze[x][y-1].isWall())
			y = y - 1;
	}
	
	public void moveDown()
	{
		if(!maze.maze[x][y+1].isWall())
			y = y + 1;
	}
}
