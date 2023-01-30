package com.example.pongfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class Pong extends Application {

    //Variables
    int width = 800;
    int height  = 600;
    int rectWidth = 15;
    int rectHeight = 100;
    double ballR = 15;
    int ballXSpeed = 1;
    int ballYSpeed = 1;
    double rectIzqYPos = height / 2;
    double rectDerYPos = height / 2;
    double ballXPos = width / 2;
    double ballYPos = width / 2;
    int scoreRectIzq = 0;
    int scoreRectDer = 0;
    boolean gameStart;
    double rectIzqXPos = 0;
    double rectDerXPos = width - rectWidth;


    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("PONG");
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        //mouse control
        canvas.setOnMouseMoved(e -> rectIzqYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStart = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();

    }

    private void run(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));

        if (gameStart) {
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;

            if (ballXPos < (width - width/4)) {
                rectDerYPos = ballYPos - (rectHeight / 2);
            } else {
                rectDerYPos = ballYPos > (rectDerYPos + rectHeight / 2) ? rectDerYPos += 1 : rectDerYPos - 1;
            }

            gc.fillOval(ballXPos, ballYPos, ballR, ballR);

        } else {
            //Este es el texto que le aparecerá al principio para comenzar la partida, deberá hacer click para empezar a jugar
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("COMENZAR PARTIDA", width/2, height/2);

            //Volvemos a poner la bola en el centro de la pantalla
            ballXPos = width / 2;
            ballYPos = height / 2;

            //Utilizamos un número aleatorio entre 0 y 1 para que la velocidad de la bola sea igual a 1 si el número aleatorio es 0
            //En cambio, si el número es igual a 1 la velocidad será -1
            ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;

        }

        //Invertimos la velocidad de la bola si la posición de la bola en el eje y es mayor a la altura de la ventana o menor a 0 (por debajo de la ventana)
        if (ballYPos > height || ballYPos < 0) {
            ballYSpeed *= -1;
        }

        //Si la posición de la bola en el eje x es menor a la posición del rectángulo en el eje x menos su anchura, el jugador
        //que maneja el rectángulo derecho obtiene un punto
        if (ballXPos < (rectIzqXPos - rectWidth)) {
            scoreRectDer++;
            gameStart = false;
        }

        //Si la posición de la bola en el eje x es mayor a la posición del rectángulo en el eje x más su anchura, el jugador
        //que maneja el rectángulo izquierdo obtiene un punto
        if (ballXPos > (rectDerXPos + rectWidth)) {
            scoreRectIzq++;
            gameStart = false;
        }

        //Incrementamos la velocidad de la bola cada vez que reciba un impacto de un rectángulo
        /*
        *
        *   PONER CÓDIGO NECESARIO PARA HACERLO
        *
        * */

        //Mostramos en pantalla los puntos de ambos jugadores
        gc.fillText(String.valueOf(scoreRectIzq), width - ((3/4)*width), height - (height*0.2));
        gc.fillText(String.valueOf(scoreRectDer), width - (width/4), height - (height*0.2));


    }


    public static void main(String[] args) {
        launch();
    }
}