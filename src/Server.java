import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
	
	static Maze maze = new Maze(41,41);
	static List<Player> playerList;
	static AtomicInteger position = new AtomicInteger();
	static AtomicBoolean wait = new AtomicBoolean();
	public static void main(String[] args)
	{

		position.set(0);
		maze.makePath(1,1);
		maze.makeExits(5);
		wait.set(true);
		
		Timer timer = new Timer();
		TimerTask startGame = new TimerTask(){
		
			@Override
			public void run() {
				System.out.println("Setting play to true");
				wait.set(false);
			}
		};
		TimerTask removeExit = new TimerTask(){
		
			@Override
			public void run() {
				if(maze.getExitSize()>0)
				{
					maze.removeExit();
					System.out.println("Exit removed");
				}
				else
				{
					System.out.println("Server reset");
					maze.makePath(1,1);
					maze.makeExits(5);
					wait.set(true);
					position.set(0);
					playerList.clear();
				}
				
			}
		};
		
		timer.schedule(startGame,	 20000, 120000);
		timer.schedule(removeExit,	 20000, 20000);
		playerList = Collections.synchronizedList(new ArrayList<>());	
			int id = 0;
			ServerSocket server;
			try {
				server = new ServerSocket(Integer.parseInt(args[0]));
			
			while(true)
			{
				Socket client = server.accept();
				ThreadHandler handler = new ThreadHandler(client, id++, maze, playerList, position, wait);
				handler.start();
				System.out.println("Client accepted, ID : " + id);
				
			}
			
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
