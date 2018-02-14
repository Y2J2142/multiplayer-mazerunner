import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;


public class ThreadHandler extends Thread {
	Socket client;
	int id;
	Maze maze;
	Player player;
	List<Player> playerList;
	AtomicInteger position;
	AtomicBoolean wait;
	AtomicInteger points;

	ThreadHandler(Socket client, int id, Maze maze, List<Player> playerList, AtomicInteger position, AtomicBoolean wait, AtomicInteger points) {
		this.client = client;
		this.id = id;
		this.maze = maze;
		this.playerList = playerList;
		this.position = position;
		this.wait = wait;
		this.points = points;
	}

	public void run() {
		try {
			PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String fromClient = new String();
			boolean play = true;
			player.setName(reader.readLine());
			while(wait.get())
				{
					writer.println(Boolean.toString(wait.get()));
				}
			writer.println("start");
			while (play) {
				
				fromClient = reader.readLine().trim();

				if (fromClient.equals("l")) {
					if(player.moveLeft() || maze.getExitSize() == 0)
					{
						writer.println("end");
						writer.println(Integer.toString(position.incrementAndGet()));
						play = false;
						
					}
					else sendMaze(writer);
				}
				if (fromClient.equals("r")) {
					if(player.moveRight() || maze.getExitSize() == 0)
					{
						writer.println("end");
						writer.println(Integer.toString(position.incrementAndGet()));
						play = false;
						
					}
					else sendMaze(writer);
				}
				if (fromClient.equals("u")) {
					if(player.moveUp() || maze.getExitSize() == 0)
					{
						writer.println("end");
						writer.println(Integer.toString(position.incrementAndGet()));
						play = false;
					} 
					else sendMaze(writer);
				}
				if (fromClient.equals("d")) {
					if(player.moveDown() || maze.getExitSize() == 0)
					{
						writer.println("end");
						writer.println(Integer.toString(position.incrementAndGet()));
						play = false;
					}
					else sendMaze(writer);
				}
				if (fromClient.equals("blank")) {
					if(maze.getExitSize() == 0)
					{
						writer.println("end");
						writer.println(Integer.toString(position.incrementAndGet()));
						play = false;
					}
					else sendMaze(writer);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try { client.close();}
			catch(Exception e) {e.printStackTrace();}
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
				char tempPlayer = 'e';
				for (Player p : playerList) 
				{
					if(i == p.getX() && j == p.getY() && p.getID() != this.player.id)
					{
						tempPlayer = '0';
						drawMaze = false;
					}	
					if (i == p.getX() && j == p.getY() && p.id == this.player.id)
					{
						tempPlayer = 'X';
						drawMaze = false;
						break;
						
					}
					
				}
				if(!drawMaze && tempPlayer != 'e')
					string += tempPlayer;
				else if(drawMaze)
					string += this.maze.maze[i][j].toString();
			}
			string += '\n';
		}
		return string;

	}

}
