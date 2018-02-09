import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;
public class Maze {
	
	int R;
	int C;
	Cell[][] maze;
	List<Cell> exits;
	
	Maze(int r, int c)
	{
		R = r;
		C = c;
		exits = new ArrayList<>();
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
	public int getExitSize(){return this.exits.size();}
	public int getR(){return R;}
	public int getC(){return C;}
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
	
	public void makeExits(int numberOfExits)
	{
		Random rng = new Random();

		for(int i = 0; i < numberOfExits; i++)
		{
			int row = rng.nextInt(this.R-2)+1;
			int col = rng.nextInt(this.C-2)+1;
			this.maze[row][col].setExit(true);
			this.maze[row][col].setWall(false);
			exits.add(this.maze[row][col]);
		}
	}

	public void removeExit()
	{
		Random rng = new Random();
		int index = rng.nextInt(exits.size());
		exits.get(index).setExit(false);
		exits.get(index).setWall(false);
		exits.remove(index);
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
