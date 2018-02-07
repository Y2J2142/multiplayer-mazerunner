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
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class FxClient extends Application {

    PrintWriter out;
    Text text = new Text();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {

            Socket socket = new Socket(InetAddress.getLocalHost(), 5566);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String fromServer = new String();
            StackPane anchor = new StackPane();
            Scene scene = new Scene(anchor, 100, 100);
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {

                    KeyCode keyCode = event.getCode();
                    text.setText(keyCode.toString());
                    if (keyCode.equals(KeyCode.DOWN))
                    {
                        out.println("d");
                        readMaze(41, in);  
                    }
                    else if (keyCode.equals(KeyCode.UP))
                    {
                        out.println("u");
                        readMaze(41, in);   
                    }
                    else if (keyCode.equals(KeyCode.LEFT))
                    {
                        out.println("l");
                        readMaze(41, in);           
                    }
                    else if (keyCode.equals(KeyCode.RIGHT))
                    {
                        out.println("r");
                        readMaze(41, in);
                    }
                }

            });
            anchor.getChildren().add(text);
            primaryStage.setScene(scene);
            primaryStage.show();

            for(int i = 0; i <=41; i++)
            {
                fromServer = in.readLine();
                System.out.println(fromServer);
            }
            System.out.println("dupa");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

void readMaze(int size, BufferedReader in)
{
    for(int i = 0; i <=size; i++)
    {
        try{

        String fromServer = new String();
        fromServer = in.readLine();
        System.out.println(fromServer);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}


}
