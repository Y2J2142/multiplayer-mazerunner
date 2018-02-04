import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args)
	{
		try {
			
			Socket socket = new Socket(InetAddress.getLocalHost(), Integer.parseInt(args[0]));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			while(true)
			{
				String fromServer = in.readLine();
				System.out.println(fromServer);
				
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
