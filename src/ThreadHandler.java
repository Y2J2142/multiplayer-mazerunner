import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.lang.model.util.ElementScanner6;


public class ThreadHandler extends Thread {
	Socket client;
	int id;
	Maze maze;
	Player player;
	List<Player> playerList;

	ThreadHandler(Socket client, int id, Maze maze, List<Player> playerList) {
		this.client = client;
		this.id = id;
		this.maze = maze;
		this.player = new Player(1, 1, id, maze);
		playerList.add(this.player);
		this.playerList = playerList;
		
	}

	public void run() {
		try {
			PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String fromClient = new String();
			writer.println(maze.toString());
			while (true) {

				fromClient = reader.readLine().trim();

				if (fromClient.equals("l")) {
					writer.print("\033[H\033[2J");
					player.moveLeft();
					sendMaze(writer);
				}
				if (fromClient.equals("r")) {
					writer.print("\033[H\033[2J");
					player.moveRight();
					sendMaze(writer);
				}
				if (fromClient.equals("u")) {
					writer.print("\033[H\033[2J");
					player.moveUp();
					sendMaze(writer);
				}
				if (fromClient.equals("d")) {
					writer.print("\033[H\033[2J");
					player.moveDown();
					sendMaze(writer);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void sendMaze(PrintWriter writer) {
		writer.println(this.playerMaze());
	}

	String playerMaze() {
		String string = new String();
		for (int i = 0; i < this.maze.getR(); i++) 
		{
			for (int j = 0; j < this.maze.getC(); j++) 
			{
				boolean drawMaze = true;
				for (Player p : playerList) 
				{
					
					if (i == p.getX() && j == p.getY() && p.id == this.player.id && drawMaze)
					{
						string += "X";
						drawMaze = false;
						
					}
					if(i == p.getX() && j == p.getY() && p.getID() != this.player.id && drawMaze)
					{
						string+= Integer.toString(p.id);
						drawMaze = false;
					}	
					
				}
				if(drawMaze)
					string += this.maze.maze[i][j].toString();
			}
			string += '\n';
		}
		return string;

	}

}
