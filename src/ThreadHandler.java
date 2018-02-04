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
		writer.println(maze.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
}
}
