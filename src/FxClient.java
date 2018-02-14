import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class FxClient extends Application {

    PrintWriter out;
    Text text = new Text();
    Canvas canvas;
    Timer timer = new Timer();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        TimerTask move = new TimerTask(){
        
            @Override
            public void run() {
                
                Platform.runLater(() -> canvas.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED,  
                KeyCode.SPACE.toString(), KeyCode.SPACE.toString(),  
                KeyCode.SPACE, false, false, false, false)) );
                    }
        };
        timer.schedule(move, 100, 100);
        try {
            Config config = new Config("config.xml");
            int size = config.getBlockSize();
            int rows = config.getRows();
            int cols = config.getCols();
            Socket socket = new Socket(config.getIP(), config.getPort());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            
            Scanner scn = new Scanner(System.in);
            out.println(scn.nextLine());
            
            
            StackPane stack = new StackPane();
            canvas = new Canvas(cols*size,rows*size);
            canvas.setFocusTraversable(true);
            String wait =  new String();
            System.out.println("Waiting for server to start");
            while(!(wait = in.readLine()).equals("start"));
            stack.getChildren().add(canvas);

            Scene scene = new Scene(stack,cols*size,rows*size);
            canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {

                    KeyCode keyCode = event.getCode();
                    if (keyCode.equals(KeyCode.DOWN)) 
                        out.println("d");
                    else if (keyCode.equals(KeyCode.UP))
                        out.println("u");
                    else if (keyCode.equals(KeyCode.LEFT))
                        out.println("l");
                    else if (keyCode.equals(KeyCode.RIGHT))
                        out.println("r");
                    else if (keyCode.equals(KeyCode.SPACE)) 
                        out.println("blank");
                    readMaze(rows, size, in, canvas, socket);
                }

            });
            stack.getChildren().add(text);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readMaze(int rows,int size, BufferedReader in, Canvas canvas, Socket socket) 
    {
        for (int i = 0; i <= rows; i++) 
        {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            try 
            {
                String fromServer = new String();
                fromServer = in.readLine();
                if(fromServer.equals("end"))
                {
                    readEndGame(in, canvas, socket);
                    return;
                }
                for (int j = 0; j < fromServer.length(); j++) 
                {
                    if (fromServer.charAt(j) == '0') 
                        gc.setFill(Color.BLUE);
                    else if (fromServer.charAt(j) == '#')
                        gc.setFill(Color.BLACK);
                    else if (fromServer.charAt(j) == 'X')
                        gc.setFill(Color.RED);
                    else if(fromServer.charAt(j) == '@')
                        gc.setFill(Color.GOLDENROD);
                    else 
                        gc.setFill(Color.WHITE);
                    gc.fillRect(j * size, i * size, size, size);
                }
            } catch (IOException e) {e.printStackTrace();}
        }

    }


    void readEndGame(BufferedReader in, Canvas canvas, Socket socket)
    {
        try{

            canvas.setOnKeyPressed(null);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            String scoreBoard = new String();
            int playerListSize = Integer.parseInt(in.readLine());
            for(int i = 0; i < playerListSize; i++)
                scoreBoard += in.readLine() +'\n';
            gc.clearRect(0,0,canvas.getHeight(),canvas.getWidth());
            gc.setFont(new Font(20));
            gc.fillText(scoreBoard, 20, canvas.getWidth()/2);
        }catch(IOException e){e.printStackTrace();}
    }

}
