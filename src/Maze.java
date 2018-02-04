import java.util.ArrayList;
import java.util.Collections;

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
			{
				maze[i][j] = new Cell(i, j);
				maze[i][j].setR(i);
				maze[i][j].setC(j);
				if (i % 2 == 0 || j % 2 == 0)
					maze[i][j].setWall(true);
			}

		
	}
	
	public void makePath(int r, int c)
	{
		if(!this.maze[r][c].isVisited())
		{
			this.maze[r][c].setVisited(true);
			ArrayList<Cell> cellList= new ArrayList<>(9);
			
			if (r - 2 >= 0 && r < R)
				cellList.add(maze[r - 2][c]);
			if(r+2 < R && r >= 0)
				cellList.add(maze[r + 2][c]);
			if(c - 2 >= 0 && c < C)
				cellList.add(maze[r][c - 2]);
			if (c + 2 < C && c >= 0)
				cellList.add(maze[r][c + 2]);
			
			Collections.shuffle(cellList);
			
			for(Cell cell : cellList)
			{
				if(!cell.isVisited())
				{
					if (r + 2 == cell.getR())
					{
						maze[r + 1][c].setWall(false);
						makePath(cell.getR(), cell.getC());
					}
					if (r - 2 == cell.getR())
					{
						maze[r - 1][c].setWall(false);
						makePath(cell.getR(), cell.getC());
					}
					if (c + 2 == cell.getC())
					{
						maze[r][c + 1].setWall(false);
						makePath(cell.getR(), cell.getC());
					}
					if (c - 2 == cell.getC())
					{
						maze[r][c - 1].setWall(false);
						makePath(cell.getR(), cell.getC());
					}
					
				}
			}
			
			
			
			
			
			
		}
	}
	
	
	public String toString()
	{
		String string = new String();
		for(int i = 0; i < R; i++)
		{
			for(int j = 0; j < C; j++)
				string += this.maze[i][j].toString();
			string += '\n';
		}
		return string;
	}
	

}
