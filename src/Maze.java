public class Maze {
	
	int R;
	int C;
	Cell[][] maze;
	
	Maze(int r, int c)
	{
		R = r;
		C = c;
		maze = new Cell[R][C];
		for(int i = 0; i < R; i++)
			for(int j = 0; j < C; j++)
				maze[i][j] = new Cell(i, j);
		
	}
	
	
	public static void main(String[] args)
	{
		Maze maze = new Maze(10,10);
		System.out.println("heh|");
	}

}
