package com.example.pongfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class Pong extends Application {
    @Override
    public void start(Stage stage) throws IOException {

         int WIDTH = 800;
         int HEIGHT = 600;
         int  PLAYER_WIDTH = 100;//son los rectángulos
         int  PLAYER_HEIGHT = 15;
         double  BALL_R = 15;
         int ballYSpeed = 1;
         int ballXSpeed = 1;
         double playerOneYPos = HEIGHT / 2;
         double playerTwoYPos = HEIGHT / 2;

         int scoreP1 = 0;
         int scoreP2 = 0;
         int playerOneXPos = 0;


        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Group root = new Group(canvas);
        GraphicsContext gp = canvas.getGraphicsContext2D();
        gp.setFill(Color.BLACK);
        gp.fillRect(0, 0, WIDTH, HEIGHT);
        stage.setScene(new Scene(root));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}