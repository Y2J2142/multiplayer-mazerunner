import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class FxClient extends Application {

    PrintWriter out;
    Text text = new Text();
    Canvas canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            Config config = new Config("config.xml");
            int size = config.getBlockSize();
            int rows = config.getRows();
            int cols = config.getCols();
            Socket socket = new Socket(config.getIP(), config.getPort());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            StackPane stack = new StackPane();
            canvas = new Canvas(cols*size,rows*size);
            canvas.setFocusTraversable(true);
            out.println("l");
            readMaze(rows, size, in, canvas, socket);
            String wait =  new String();
            System.out.println("Waiting for server to start");
            while(!(wait = in.readLine()).equals("start"));
            stack.getChildren().add(canvas);

            Scene scene = new Scene(stack,cols*size,rows*size);
            canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {

                    KeyCode keyCode = event.getCode();
                    if (keyCode.equals(KeyCode.DOWN)) {
                        out.println("d");
                        readMaze(rows, size, in, canvas, socket);
                    } else if (keyCode.equals(KeyCode.UP)) {
                        out.println("u");
                        readMaze(rows, size, in, canvas, socket);
                    } else if (keyCode.equals(KeyCode.LEFT)) {
                        out.println("l");
                        readMaze(rows, size, in, canvas, socket);
                    } else if (keyCode.equals(KeyCode.RIGHT)) {
                        out.println("r");
                        readMaze(rows, size, in, canvas, socket);
                    }
                }

            });
            stack.getChildren().add(text);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            readMaze(rows, size,  in, canvas, socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readMaze(int rows,int size, BufferedReader in, Canvas canvas, Socket socket) {
        for (int i = 0; i <= rows; i++) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            try 
            {
                String fromServer = new String();
                fromServer = in.readLine();
                if(fromServer.equals("end"))
                {
                    
                    
                    canvas.setOnKeyPressed(null);
                    gc.clearRect(0,0,canvas.getHeight(),canvas.getWidth());
                    String position = new String();
                    position = in.readLine();
                    gc.fillText("You placed : " + position, canvas.getHeight()/2, canvas.getWidth()/2);
                    socket.close();
                    return;
                }
                for (int j = 0; j < fromServer.length(); j++) {
                    if (fromServer.charAt(j) == '0') {
                        gc.setFill(Color.BLUE);
                        gc.fillRect(j * size, i * size, size, size);
                    } else if (fromServer.charAt(j) == '#') {
                        gc.setFill(Color.BLACK);
                        gc.fillRect(j * size, i * size, size, size);
                    } else if (fromServer.charAt(j) == 'X') {
                        gc.setFill(Color.RED);
                        gc.fillRect(j * size, i * size, size, size);
                    } else if(fromServer.charAt(j) == '@'){
                        gc.setFill(Color.GOLDENROD);
                        gc.fillRect(j * size, i * size, size, size);

                    } else {
                        gc.setFill(Color.WHITE);
                        gc.fillRect(j * size, i * size, size, size);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
