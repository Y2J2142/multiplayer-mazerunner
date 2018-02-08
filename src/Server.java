import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.Collections;
public class Server {
	
	static Maze maze = new Maze(41,41);
	static List<Player> playerList;
	
	public static void main(String[] args)
	{
		maze.makePath(1,1);
		maze.makeExits(5);
		playerList = Collections.synchronizedList(new ArrayList<>());	
			int id = 0;
			ServerSocket server;
			try {
				server = new ServerSocket(Integer.parseInt(args[0]));
			
			while(true)
			{
				Socket client = server.accept();
				ThreadHandler handler = new ThreadHandler(client, id++, maze, playerList);
				handler.start();
			}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
