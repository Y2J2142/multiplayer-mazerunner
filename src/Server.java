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
	
	static Config config = new Config("config.xml");
	static Maze maze = new Maze(config.getRows(),config.getCols());
	static List<Player> playerList;
	static AtomicInteger position = new AtomicInteger();
	static AtomicBoolean wait = new AtomicBoolean();
	static AtomicInteger points = new AtomicInteger();
	static int delay = config.getDelay();
	public static void main(String[] args)
	{

		position.set(0);
		points.set(config.getNumberOfExits());
		maze.makePath(1,1);
		maze.makeExits(config.getNumberOfExits());
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
					points.decrementAndGet();
					if(maze.getExitSize() == 0)
					{
						System.out.println("Server reset");
						maze = new Maze(config.getRows(),config.getCols());
						maze.makePath(1,1);
						maze.makeExits(config.getNumberOfExits());
						wait.set(true);
						position.set(0);
						points.set(config.getNumberOfExits());
						for(Player p : playerList)
						{
							p.setMaze(maze);
							p.setStart();
						}
					}
				}
				
			}
		};
		
		timer.schedule(startGame,	 delay, (config.getNumberOfExits()) * delay);
		timer.schedule(removeExit,	 delay, delay);
		playerList = Collections.synchronizedList(new ArrayList<>());	
			int id = 0;
			ServerSocket server;
			try {
				server = new ServerSocket(config.getPort());
			
			while(true)
			{
				Socket client = server.accept();
				ThreadHandler handler = new ThreadHandler(client, id++, maze, playerList, position, wait, points);
				handler.start();
				System.out.println("Client accepted, ID : " + id);
				
			}
			
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
