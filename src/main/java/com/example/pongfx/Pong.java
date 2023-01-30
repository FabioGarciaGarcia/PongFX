package com.example.pongfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        canvas.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                switch (e.getCode()) {
                    case W:
                        rectIzqYPos -= 20;
                        break;
                    case S:
                        rectIzqYPos += 20;
                        break;
                    case UP:
                        rectDerYPos -= 20;
                        break;
                    case DOWN:
                        rectDerYPos += 20;
                        break;
            }
        });


        canvas.setOnMouseClicked(e -> gameStart = true);
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
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

            gc.fillOval(ballXPos, ballYPos, ballR, ballR);

        } else {
            //Este es el texto que le aparecerá al principio para comenzar la partida, deberá hacer click para empezar a jugar
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("COMENZAR PARTIDA\nDALE CLICK", width/2, height/2);

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

        //Hacemos que la bola detecte el impacto con los rectángulos e incrementamos la velocidad de la bola cada vez que reciba un impacto de un rectángulo
        if (((ballXPos + ballR > rectDerXPos) && ballYPos >= rectDerYPos && ballYPos <= rectDerYPos + rectHeight) ||
                ((ballXPos < rectIzqXPos + rectWidth) && ballYPos >= rectIzqYPos && ballYPos <= rectIzqYPos + rectHeight)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }

        //Hacemos que no se desborden los rectángulos
        if (rectIzqYPos <= 0) {
            rectIzqYPos = 0;
        } else if (rectIzqYPos >= height - rectHeight) {
            rectIzqYPos = height - rectHeight;
        }
        if (rectDerYPos <= 0) {
            rectDerYPos = 0;
        } else if (rectDerYPos >= height - rectHeight) {
            rectDerYPos = height - rectHeight;
        }


        //Mostramos en pantalla los puntos de ambos jugadores
        gc.fillText(String.valueOf(scoreRectIzq), width*0.25, height*0.2);
        gc.fillText(String.valueOf(scoreRectDer), width*0.75, height*0.2);

        //Pintamos los rectángulos
        gc.fillRect(rectIzqXPos, rectIzqYPos, rectWidth, rectHeight);
        gc.fillRect(rectDerXPos, rectDerYPos, rectWidth, rectHeight);

    }


    public static void main(String[] args) {
        launch();
    }
}