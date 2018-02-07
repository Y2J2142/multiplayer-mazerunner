import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadHandler extends Thread{
Socket client;
int id;
Maze maze;

ThreadHandler(Socket client, int id, Maze maze)
{
	this.client = client;
	this.id = id;
	this.maze = maze;
}

public void run()
{
	try {
		PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String fromClient = new String();
		writer.println(maze.toString());
		while(true)
		{
			
			
			fromClient = reader.readLine().trim();

			if(fromClient.equals("l"))
			{
				writer.print("\033[H\033[2J");
				player.moveLeft();
				sendMaze(writer);
			}
			if(fromClient.equals("r"))
			{
				writer.print("\033[H\033[2J");
				player.moveRight();
				sendMaze(writer);
			}
			if(fromClient.equals("u"))
			{
				writer.print("\033[H\033[2J");
				player.moveUp();
				sendMaze(writer);
			}
			if(fromClient.equals("d"))
			{
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

void sendMaze(PrintWriter writer)
{
	writer.println(this.maze.toString());
}


}
