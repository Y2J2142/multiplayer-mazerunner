import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
public class Server {
	
	static Maze maze = new Maze(41,41);
	static List<Player> playerList;
	static AtomicInteger position = new AtomicInteger();
	public static void main(String[] args)
	{
		position.set(0);
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
				ThreadHandler handler = new ThreadHandler(client, id++, maze, playerList, position);
				handler.start();
			}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
