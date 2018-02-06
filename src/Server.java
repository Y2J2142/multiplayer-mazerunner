import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	static Maze maze = new Maze(41,41);
	
	public static void main(String[] args)
	{
		maze.makePath(1,1);
	
			int id = 0;
			ServerSocket server;
			try {
				server = new ServerSocket(Integer.parseInt(args[0]));
			
			while(true)
			{
				Socket client = server.accept();
				ThreadHandler handler = new ThreadHandler(client, id++, maze);
				handler.start();
			}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
