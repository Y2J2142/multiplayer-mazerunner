import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;;
public class FxClient extends Application {
    
    @Override
	public void start(Stage primaryStage)
	{
        
		try {
			
			Socket socket = new Socket(InetAddress.getLocalHost(), 5566);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(System.in));
            String fromServer = new String();
            AnchorPane anchor = new AnchorPane();
            Scene scene = new Scene(anchor, 100, 100);
            scene.setOnKeyPressed(keyEvent -> {
                KeyCode keyCode = keyEvent.getCode();
                if(keyCode.equals(KeyCode.DOWN))
                    out.println("d");
                else if(keyCode.equals(KeyCode.UP))
                    out.println("u");
                else if(keyCode.equals(KeyCode.LEFT))
                    out.println("l");
                else if(keyCode.equals(KeyCode.RIGHT))
                    out.println("r");  
            }
             );
        primaryStage.setScene(scene);
        primaryStage.show();
            while(true)
            {

                while((fromServer = in.readLine()) != null)
			    {
				    System.out.println(fromServer);	
                }
                
            }
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
