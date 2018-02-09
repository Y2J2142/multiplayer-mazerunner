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

            Socket socket = new Socket(InetAddress.getLocalHost(), 5566);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            StackPane stack = new StackPane();
            canvas = new Canvas(1000, 1000);
            canvas.setFocusTraversable(true);
            out.println("l");
            readMaze(41, in, canvas, socket);
            String wait =  new String();
            System.out.println("Waiting for server to start");
            while(!(wait = in.readLine()).equals("start"));
            stack.getChildren().add(canvas);

            Scene scene = new Scene(stack, 1000, 1000);
            canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {

                    KeyCode keyCode = event.getCode();
                    if (keyCode.equals(KeyCode.DOWN)) {
                        out.println("d");
                        readMaze(41, in, canvas, socket);
                    } else if (keyCode.equals(KeyCode.UP)) {
                        out.println("u");
                        readMaze(41, in, canvas, socket);
                    } else if (keyCode.equals(KeyCode.LEFT)) {
                        out.println("l");
                        readMaze(41, in, canvas, socket);
                    } else if (keyCode.equals(KeyCode.RIGHT)) {
                        out.println("r");
                        readMaze(41, in, canvas, socket);
                    }
                }

            });
            stack.getChildren().add(text);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            readMaze(41, in, canvas, socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readMaze(int size, BufferedReader in, Canvas canvas, Socket socket) {
        for (int i = 0; i <= size; i++) {
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
                        gc.fillRect(j * 20, i * 20, 20, 20);
                    } else if (fromServer.charAt(j) == '#') {
                        gc.setFill(Color.BLACK);
                        gc.fillRect(j * 20, i * 20, 20, 20);
                    } else if (fromServer.charAt(j) == 'X') {
                        gc.setFill(Color.RED);
                        gc.fillRect(j * 20, i * 20, 20, 20);
                    } else if(fromServer.charAt(j) == '@'){
                        gc.setFill(Color.GOLDENROD);
                        gc.fillRect(j * 20, i * 20, 20, 20);

                    } else {
                        gc.setFill(Color.WHITE);
                        gc.fillRect(j * 20, i * 20, 20, 20);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
